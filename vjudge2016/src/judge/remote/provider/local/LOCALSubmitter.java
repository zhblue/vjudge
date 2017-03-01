package judge.remote.provider.local;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.submitter.CanonicalSubmitter;
import judge.remote.submitter.SubmissionInfo;

import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class LOCALSubmitter extends CanonicalSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return LOCALInfo.INFO;
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) {
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String html = client.get(LOCALInfo.getPath()+"/status.php?user_id=" + info.remoteAccountId + "&problem_id=" + info.remoteProblemId+"&rand="+Math.random()).getBody();
        Matcher matcher = Pattern.compile("<tr class='evenrow'><td>(\\d+)</td>").matcher(html);
       // System.out.println(html);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        HttpEntity entity = SimpleNameValueEntityFactory.create(
            "language", info.remotelanguage, //
            "id", info.remoteProblemId, //
            "source", info.sourceCode //
        );
        client.post(LOCALInfo.getPath()+"/submit.php", entity, HttpStatusValidator.SC_MOVED_TEMPORARILY);
        return null;
    }

}
