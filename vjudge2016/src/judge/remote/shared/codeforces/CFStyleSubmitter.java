package judge.remote.shared.codeforces;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.stereotype.Component;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.SimpleHttpResponse;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.account.RemoteAccount;
import judge.remote.shared.codeforces.CodeForcesTokenUtil.CodeForcesToken;
import judge.remote.submitter.CanonicalSubmitter;
import judge.remote.submitter.SubmissionInfo;


@Component
public abstract class CFStyleSubmitter extends CanonicalSubmitter {

    @Override
    protected boolean needLogin() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) throws IOException {
        String html = client.get("/api/user.status?handle=" + info.remoteAccountId + "&from=1&count=10").getBody();
        String contestNum = info.remoteProblemId.replaceAll("\\D.*", "");
        String problemNum = info.remoteProblemId.replaceAll("^\\d*", "");
        
        try {
            Map<String, Object> json = (Map<String, Object>) JSONUtil.deserialize(html);
            List<Map<String, Object>> results = (List<Map<String, Object>>)json.get("result");
            for (Map<String, Object> result : results) {
                int runId = ((Long) result.get("id")).intValue();
                Map<String, Object> problem = (Map<String, Object>) result.get("problem");
                if(contestNum.equals(((Long)problem.get("contestId")).toString()) &&
                        problemNum.equals((String)problem.get("index"))){
                    return runId;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) throws IOException, InterruptedException {
        CodeForcesToken token = CodeForcesTokenUtil.getTokens(client);
        
        HttpEntity entity = SimpleNameValueEntityFactory.create( //
            "csrf_token", token.csrf_token, //
            "_tta", token._tta, //
            "action", "submitSolutionFormSubmitted", //
            "submittedProblemCode", info.remoteProblemId, //
            "programTypeId", info.remotelanguage, //
            "source", info.sourceCode + getRandomBlankString(), //
            "sourceFile", "", //
            "sourceCodeConfirmed", "true", //
            "doNotShowWarningAgain", "on" //
        );
        
        SimpleHttpResponse response = client.post(
            "/problemset/submit?csrf_token=" + token.csrf_token,
            entity
        );
        
        if (response.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY) {
            if (response.getBody().contains("error for__programTypeId")) {
                return "Language Rejected";
            }
            if (response.getBody().contains("error for__source")) {
                return "Source Code Error";
            }
            throw new RuntimeException();
        }
        return null;
    }
    
    private String getRandomBlankString() {
        String string = "\n";
        int random = new Random().nextInt(Integer.MAX_VALUE);
        while (random > 0) {
            string += random % 2 == 0 ? ' ' : '\t';
            random /= 2;
        }
        return string;
    }
    
    @Override
    protected long getSubmitReceiptDelay() {
        return 30000;
    }

}
