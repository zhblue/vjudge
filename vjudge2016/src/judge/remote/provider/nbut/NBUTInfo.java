package judge.remote.provider.nbut;

import judge.remote.RemoteOj;
import judge.remote.RemoteOjInfo;

import org.apache.http.HttpHost;

public class NBUTInfo {

    public static final RemoteOjInfo INFO = new RemoteOjInfo( //
            RemoteOj.NBUT, //
            "NBUT", //
            new HttpHost("ac.2333.moe", 443, "https") //
    );
    
    static {
        INFO.faviconUrl = "images/remote_oj/NBUT_icon.jpg";
    }
}
