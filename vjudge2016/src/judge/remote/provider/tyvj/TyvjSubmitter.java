package judge.remote.provider.tyvj;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.SimpleHttpResponse;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.submitter.CanonicalSubmitter;
import judge.remote.submitter.SubmissionInfo;
import judge.tool.Tools;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Component
public class TyvjSubmitter extends CanonicalSubmitter {
    private final static Logger log = LoggerFactory.getLogger(TyvjSubmitter.class);

    @Override
    public RemoteOjInfo getOjInfo() {
        return TyvjInfo.INFO;
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    protected Integer getMaxRunId(SubmissionInfo info, DedicatedHttpClient client, boolean submitted) {
        if(info.remoteRunId != null){
            return Integer.parseInt(info.remoteRunId);
        }
        try{
            Map<String, String> []json = new Gson().fromJson(client.get(
                    "/Status/GetStatuses?page=0&problemid=" + info.remoteProblemId + "&username=" + info.remoteAccountId).getBody(),
                    new TypeToken<HashMap<String, String>[]>() {}.getType());
            return Integer.parseInt(json[0].get("ID"));
        } catch (Exception e) {
            ;
        }
        return  -1;
    }

    @Override
    protected String submitCode(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        SimpleHttpResponse response = client.get("/p/" + info.remoteProblemId);
        String token = Tools.regFind(response.getBody(),
                "id=\"frmSubmitCode\" method=\"post\"><input name=\"__RequestVerificationToken\" type=\"hidden\" value=\"(.+?)\" /> ");
        HttpEntity entity = SimpleNameValueEntityFactory.create(
                "__RequestVerificationToken", token, //
                "problem_id", info.remoteProblemId, //
                "code", info.sourceCode,
                "language_id", info.remotelanguage
        );
        String html = client.post("/Status/Create", entity).getBody();
        if("No Online Judger".equals(html)){
            log.info("No Online Judger");
            return null;
        }
        try{
            info.remoteRunId = new Integer(html).toString();
            return null;
        } catch (Exception e) {
            if(html.length() <= 24){
                return html;
            } else {
                throw e;
            }
        }
    }
}
