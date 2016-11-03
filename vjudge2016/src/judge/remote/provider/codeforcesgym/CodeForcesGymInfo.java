package judge.remote.provider.codeforcesgym;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class CodeForcesGymInfo {

    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.CFGym, //
            "CFGym", //
            new HttpHost("codeforces.com") //
    );
    
    static {
        INFO.faviconUrl = "images/remote_oj/CodeForces_favicon.png";
        INFO._64IntIoFormat = "%I64d & %I64u";
        INFO.urlForIndexDisplay = "http://codeforces.com/gyms";
    }
}
