package judge.remote.provider.csu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.querier.AuthenticatedQuerier;
import judge.remote.status.RemoteStatusType;
import judge.remote.status.SubmissionRemoteStatus;
import judge.remote.status.SubstringNormalizer;
import judge.remote.submitter.SubmissionInfo;
import judge.tool.Tools;

import org.apache.commons.lang3.Validate;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Component;

@Component
public class CSUQuerier extends AuthenticatedQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CSUInfo.INFO;
    }

    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        HttpGet get = new HttpGet("/csuoj/Status/status_ajax?solution_id=" + info.remoteRunId);
        get.setHeader("X-Requested-With", "XMLHttpRequest");
        String html = client.execute(get).getBody();
        Pattern pattern = Pattern.compile("\"memory\":(\\d+),\"time\":(\\d+),.*?\"result_show\":\"<.*?>(.+?)<.*?>\"");
        Matcher matcher = pattern.matcher(html);
        Validate.isTrue(matcher.find());
        SubmissionRemoteStatus status = new SubmissionRemoteStatus();
        status.rawStatus = matcher.group(3).replaceAll("<.*?>", "").trim();
        status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
        if (status.statusType == RemoteStatusType.AC) {
            status.executionMemory = Integer.parseInt(matcher.group(1).replaceAll("\\D", ""));
            status.executionTime = Integer.parseInt(matcher.group(2).replaceAll("\\D", ""));
        } else if (status.statusType == RemoteStatusType.CE) {
            get = new HttpGet("/csuoj/status/resdetail_ajax?solution_id=" + info.remoteRunId + "&cid=x");
            get.setHeader("X-Requested-With", "XMLHttpRequest");
            html = client.execute(get).getBody();
            status.compilationErrorInfo = "<pre>" + Tools.regFind(html, "\"msg\":\"(.*?)\",\"data\":").replace("\\n", "\n") + "</pre>";
        }
        
        return status;
    }

}
