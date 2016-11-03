package judge.remote.provider.acdream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.querier.AuthenticatedQuerier;
import judge.remote.status.RemoteStatusType;
import judge.remote.status.SubmissionRemoteStatus;
import judge.remote.status.SubstringNormalizer;
import judge.remote.submitter.SubmissionInfo;
import org.apache.struts2.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ACdreamQuerier extends AuthenticatedQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return ACdreamInfo.INFO;
    }

    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) throws JSONException {

    	String html = client.get("/status?name=" + info.remoteAccountId + "&pid=" + info.remoteProblemId).getBody();
    	String pattern = "rid=\"" +  info.remoteRunId + "\"[^>]*>([^<]+).*?KB<\\/td>";
    	Matcher matcher = Pattern.compile(pattern).matcher(html);
        matcher.find();
    	
        SubmissionRemoteStatus status = new SubmissionRemoteStatus();
        status.rawStatus =  matcher.group(1);
        status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
        
        if (status.statusType == RemoteStatusType.AC) {
        	String pattern2 = "<td>(\\d+)\\s*MS<\\/td><td>(\\d+)\\s*KB<\\/td>";
        	Matcher matcher2  = Pattern.compile(pattern2).matcher(matcher.group(0));
        	matcher2.find();
            status.executionMemory = Integer.parseInt(matcher2.group(2));
            status.executionTime = Integer.parseInt(matcher2.group(1));
        } else if (status.statusType == RemoteStatusType.CE) {
        	Map<String, String> json = new Gson().fromJson(client.post("/status/CE", SimpleNameValueEntityFactory.create(
                    "rid", info.remoteRunId
            )).getBody() , new TypeToken<HashMap<String, String>>() {
            }.getType());
            status.compilationErrorInfo = "<pre>" +  json.get("msg").replace("\n", "<br>") +  "</pre>";
        }
        return status;
    }

}
