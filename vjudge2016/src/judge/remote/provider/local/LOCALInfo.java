package judge.remote.provider.local;

import java.util.ResourceBundle;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;
import judge.tool.ApplicationConfigPopulator;
import judge.tool.ApplicationContainer;

import org.apache.http.HttpHost;

public class LOCALInfo {
	private static String domain =java.util.ResourceBundle.getBundle("config").getString("hustoj.domain");
	private static int port =Integer.parseInt(java.util.ResourceBundle.getBundle("config").getString("hustoj.port"));
	private static String path=java.util.ResourceBundle.getBundle("config").getString("hustoj.path");
	
    



	public static String getDomain() {
		return domain;
	}


	public static void setDomain(String domain) {
		LOCALInfo.domain = domain;
	}


	public static String getPath() {
		return path;
	}


	public static void setPath(String path) {
		LOCALInfo.path = path;
	}


	public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.LOCAL, //
            "LOCAL", //
            new HttpHost(domain,port) //
    );
     
    
    static {
        INFO.faviconUrl = "images/remote_oj/icon-icpc-small.gif";
        INFO._64IntIoFormat = "%lld & %llu";
        INFO.urlForIndexDisplay = "http://"+domain+path;
    }


	public static int getPort() {
		// TODO Auto-generated method stub
		return port;
	}
}
