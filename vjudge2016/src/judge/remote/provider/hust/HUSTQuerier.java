package judge.remote.provider.hust;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.account.RemoteAccount;
import judge.remote.querier.AuthenticatedQuerier;
import judge.remote.status.RemoteStatusType;
import judge.remote.status.SubmissionRemoteStatus;
import judge.remote.status.SubstringNormalizer;
import judge.remote.submitter.SubmissionInfo;
import judge.tool.Tools;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class HUSTQuerier extends AuthenticatedQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return HUSTInfo.INFO;
    }

    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
        String html = client.get("/solution/source/" + info.remoteRunId, HttpStatusValidator.SC_OK).getBody();

        String regex = 
                "<span class=\"badge\">(.*?)</span>\\s*Result[\\s\\S]*?" +
                "<span class=\"badge\">(\\d+)ms</span>\\s*Time[\\s\\S]*?" +
                "<span class=\"badge\">(\\d+)kb</span>\\s*Memory";
        Matcher matcher = Pattern.compile(regex).matcher(html);
        Validate.isTrue(matcher.find());
        
        SubmissionRemoteStatus status = new SubmissionRemoteStatus();
        status.rawStatus = matcher.group(1).trim();
        status.executionTime = Integer.parseInt(matcher.group(2));
        status.executionMemory = Integer.parseInt(matcher.group(3));
        status.statusType = HUSTSubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
        if (status.statusType == RemoteStatusType.CE) {
            status.compilationErrorInfo = (Tools.regFind(html, "(<pre class=\"col-sm-12 linenums\">[\\s\\S]*?</pre>)"));
        }
        return status;
    }
    
}
class HUSTSubstringNormalizer extends SubstringNormalizer{
	public static final SubstringNormalizer DEFAULT = new HUSTSubstringNormalizer();
	  @SuppressWarnings("unused")
	private static LinkedHashMap<String, RemoteStatusType> baseStatusTypeMap = new LinkedHashMap<String, RemoteStatusType>(){{
	        put("等待评测", RemoteStatusType.QUEUEING);
	        put("等待重测", RemoteStatusType.QUEUEING);
	       
	        put("正在编译", RemoteStatusType.COMPILING);

	        put("正确答案", RemoteStatusType.AC);
	        put("表达错误", RemoteStatusType.PE);
	        put("格式错误", RemoteStatusType.PE);

	        put("编译错误", RemoteStatusType.CE);
	        put("Compilation Error", RemoteStatusType.CE);

	        put("错误答案", RemoteStatusType.WA);
	        put("时间超限", RemoteStatusType.TLE);
	        put("内存超限", RemoteStatusType.MLE);
	        put("输出超限", RemoteStatusType.OLE);
	        put("运行错误", RemoteStatusType.RE);
	        put("段错误", RemoteStatusType.RE);
	        put("Floating Point Error", RemoteStatusType.RE);
	        put("Crash", RemoteStatusType.RE);

	        put("正在运行", RemoteStatusType.JUDGING);
	        put("ing", RemoteStatusType.JUDGING);
	    }};
}
