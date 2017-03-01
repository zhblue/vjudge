package judge.remote.provider.local;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpBodyValidator;
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
public class LOCALQuerier extends AuthenticatedQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return LOCALInfo.INFO;
    }
    String []result={"Pending","Pending Rejudging","Compiling","Running Judging","Accepted","Presentation Error","Wrong Answer","Time Limit Exceed","Memory Limit Exceed","Output Limit Exceed","Runtime Error","Compile Error","Compile OK"};
    @Override
    protected SubmissionRemoteStatus query(SubmissionInfo info, RemoteAccount remoteAccount, DedicatedHttpClient client) {
    	 String html = client.get(
    			 LOCALInfo.getPath()+"/status-ajax.php?solution_id=" + info.remoteRunId,
                 new HttpBodyValidator("<title>Error</title>", true)).getBody();
    	 System.out.println(html);
         String[] s=html.split(",");
         
         SubmissionRemoteStatus status = new SubmissionRemoteStatus();
         try{
         status.rawStatus =result[Integer.parseInt(s[0])];
         status.statusType = SubstringNormalizer.DEFAULT.getStatusType(status.rawStatus);
         if (status.statusType == RemoteStatusType.AC) {
             status.executionMemory = Integer.parseInt(s[1]);
             status.executionTime = Integer.parseInt(s[2].trim());
         } else if (status.statusType == RemoteStatusType.CE) {
             
             Validate.isTrue(result[Integer.parseInt(s[0])].contains("Compile Error"));
             status.compilationErrorInfo = client.get(
            		 LOCALInfo.getPath()+"/ceinfo.php?sid=" + info.remoteRunId,
                     new HttpBodyValidator("<title>Error</title>", true)).getBody().substring(3277);
         }
         }catch(Exception e){
        	 e.printStackTrace();
        	 System.out.println("["+s[2]+"]");
        	 System.out.println(html);
         }
         return status;
    }

}
