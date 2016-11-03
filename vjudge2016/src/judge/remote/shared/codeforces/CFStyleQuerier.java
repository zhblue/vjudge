package judge.remote.shared.codeforces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.stereotype.Component;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.account.RemoteAccount;
import judge.remote.querier.AuthenticatedQuerier;
import judge.remote.shared.codeforces.CodeForcesTokenUtil.CodeForcesToken;
import judge.remote.status.RemoteStatusType;
import judge.remote.status.SubmissionRemoteStatus;
import judge.remote.status.SubstringNormalizer;
import judge.remote.submitter.SubmissionInfo;

@Component
public abstract class CFStyleQuerier extends AuthenticatedQuerier {
    
    @SuppressWarnings("serial")
    private static final Map<String, String> statusMap = new HashMap<String, String>() {{
        put("FAILED","Failed");
        put("OK","Accepted");
        put("PARTIAL","Partial");
        put("COMPILATION_ERROR","Compilation Error");
        put("RUNTIME_ERROR","Runtime Error");
        put("WRONG_ANSWER","Wrong Answer");
        put("PRESENTATION_ERROR","Presentation Error");
        put("TIME_LIMIT_EXCEEDED","Time Limit Exceed");
        put("MEMORY_LIMIT_EXCEEDED","Memory Limit Exceed");
        put("IDLENESS_LIMIT_EXCEEDED","Idleness Limit Exceeded");
        put("SECURITY_VIOLATED","Security Violated");
        put("CRASHED","Crash");
        put("INPUT_PREPARATION_CRASHED","Input Preparation Crashed");
        put("CHALLENGED","Challenged");
        put("SKIPPED","Skipped");
        put("TESTING","Running");
        put("REJECTED","Rejected");
    }};
    
    @SuppressWarnings("unchecked")
    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        String html = client.get("/api/user.status?handle=" + info.remoteAccountId + "&from=1&count=20").getBody();
        
        try {
            Map<String, Object> json = (Map<String, Object>) JSONUtil.deserialize(html);
            List<Map<String, Object>> results = (List<Map<String, Object>>)json.get("result");
            for (Map<String, Object> result : results) {
                int runId = ((Long) result.get("id")).intValue();
                if(!Integer.toString(runId).equals(info.remoteRunId)){
                    continue;
                }
               
                SubmissionRemoteStatus status = new SubmissionRemoteStatus();
                String verdict = (String)result.get("verdict");
                status.rawStatus = statusMap.get(verdict);
                status.executionTime = ((Long)result.get("timeConsumedMillis")).intValue();
                status.executionMemory = ((Long)result.get("memoryConsumedBytes")).intValue() / 1024;
                status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
                status.failCase  = ((Long)result.get("passedTestCount")).intValue() + 1;
                if (status.statusType == RemoteStatusType.CE) {
                    CodeForcesToken token = CodeForcesTokenUtil.getTokens(client);
                    HttpEntity entity = SimpleNameValueEntityFactory.create( //
                            "submissionId", info.remoteRunId, //
                            "csrf_token", token.csrf_token //
                    );
                    html = client.post("/data/judgeProtocol", entity).getBody();
                    status.compilationErrorInfo = "<pre>" + html.replaceAll("(\\\\r)?\\\\n", "\n").replaceAll("\\\\\\\\", "\\\\") + "</pre>";
                } else if(status.statusType != RemoteStatusType.AC && status.statusType != RemoteStatusType.JUDGING){
                    status.rawStatus += " on test " + status.failCase;
                }
                return status;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
