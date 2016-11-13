package judge.remote.provider.local;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class LOCALInfo {
	public static final String DOMAIN="hustoj.com";
    public static final String PATH="/oj/";
   
    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.LOCAL, //
            "LOCAL", //
            new HttpHost(DOMAIN) //
    );
     
    
    static {
        INFO.faviconUrl = "images/remote_oj/icon-icpc-small.gif";
        INFO._64IntIoFormat = "%lld & %llu";
        INFO.urlForIndexDisplay = "http://"+DOMAIN+PATH;
    }
}
