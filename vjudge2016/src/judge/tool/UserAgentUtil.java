package judge.tool;

/**
 * 用户代理解析工具，解析HTTP头UserAgent字段中的浏览器与操作系统信息
 * 
 * @author lsh 
 * 参考了https://github.com/mumuy/browser的数据
 */
public class UserAgentUtil {



    private static final int NAME = 0;
    private static final int REG_EXP_FOR_VERSION = 1;
    private static final int KEYWORD_START = 2;
    private static final int NT_VERSION = 0;
    private static final int WINDOWS_VERSION = 1;

    /**
     * 浏览器信息 <br>
     * 格式：浏览器名称， 浏览器版本号匹配正则， 浏览器标志字符串1 [，浏览器标志字符串2]
     */
    private static String[][] browserInfo = {
            { "iQiYi", "^.*IqiyiVersion\\/([\\d.]+).*$", "IqiyiApp" },
            { "Suning", "^.*SNEBUY-APP([\\d.]+).*$", "SNEBUY-APP" },
            { "Douban", "^.*com.douban.frodo\\/([\\d.]+).*$", "com.douban.frodo" },
            { "Weibo", "^.*weibo__([\\d.]+).*$", "Weibo" }, 
            { "Alipay", "^.*AliApp\\(AP\\/([\\d.]+).*$", "AliApp(AP" },
            { "Taobao", "^.*AliApp\\(TB\\/([\\d.]+).*$", "AliApp(TB" },
            { "Wechat", "^.*MicroMessenger\\/([\\d.]+).*$", "MicroMessenger" },
            { "Qiyu", "^.*Qiyu\\/([\\d.]+).*$", "Qiyu" }, 
            { "Quark", "^.*Quark\\/([\\d.]+).*$", "Quark" },
            { "XiaoMi", "^.*MiuiBrowser\\/([\\d.]+).*$", "MiuiBrowser" },
            { "TheWorld", "^.*TheWorld ([\\d.]+).*$", "TheWorld" },
            { "2345Explorer", "^.*2345Explorer\\/([\\d.]+).*$", "2345Explorer" },
            { "LBBROWSER", "", "LBBROWSER" },
            { "Sogou", "^.*SE ([\\d.X]+).*$", "MetaSr", "Sogou" },
            { "Maxthon", "^.*Maxthon\\/([\\d.]+).*$", "Maxthon" },
            { "Baidu", "^.*BIDUBrowser[\\s\\/]([\\d.]+).*$", "Baidu", "BIDUBrowser" },
            { "QQ", "^.*QQ\\/([\\d.]+).*$", "QQ/" }, 
            { "QQBrowser", "^.*QQBrowser\\/([\\d.]+).*$", "QQBrowser" },
            { "UC", "^.*UC?Browser\\/([\\d.]+).*$", "UC", " UBrowser" }, 
            { "360", "", "360EE", "360SE" },
            { "Kindle", "^.*Version\\/([\\d.]+).*$", "Kindle", "Silk/" },
            { "Yandex", "^.*YaBrowser\\/([\\d.]+).*$", "YaBrowser" },
            { "Vivaldi", "^.*Vivaldi\\/([\\d.]+).*$", "Vivaldi" },
            { "Opera", "^.*Opera\\/([\\d.]+).*$", "Opera", "OPR" },
            { "Chromium", "^.*Chromium\\/([\\d.]+).*$", "Chromium" },
            { "Firefox Focus", "^.*Focus\\/([\\d.]+).*$", "Focus" },
            { "Firefox", "^.*Firefox\\/([\\d.]+).*$", "Firefox", "FxiOS" },
            { "Edge", "^.*Edge\\/([\\d.]+).*$", "Edge" },
            { "IE", "^.*MSIE ([\\d.]+).*$", "MSIE", "Trident" },
            { "Chrome", "^.*Chrome\\/([\\d.]+).*$", "Chrome", "CriOS" },
            { "Safari", "^.*Version\\/([\\d.]+).*$", "Safari" } 
    };

    /**
     * 操作系统信息 <br>
     * 格式：操作系统名称， 操作系统版本号匹配正则， 操作系统标志字符串1 [，操作系统标志字符串2]
     */
    private static String[][] osInfo = { 
            { "WebOS", "^.*hpwOS\\/([\\d.]+).*$", "hpwOS" }, 
            { "Chrome OS", "", "CrOS" },
            { "iOS", "^.*OS ([\\d_]+) like.*$", "like Mac OS X" },
            { "Symbian", "", "Symbian" },
            { "MeeGo", "", "MeeGo" }, 
            { "BlackBerry", "", "BlackBerry", "RIM" },
            { "Windows Phone", "^.*Windows Phone( OS)? ([\\d.]+).*$", "IEMobile" },
            { "Debian", "^.*Debian\\/([\\d.]+).*$", "Debian" },
            { "FreeBSD", "", "FreeBSD" },
            { "Ubuntu", "", "Ubuntu" }, 
            { "Android", "^.*Android ([\\d.]+).*$", "Android", "Adr" },
            { "Mac OS", "^.*Mac OS X ([\\d_]+).*$", "Macintosh" },
            { "Linux", "", "Linux", "X11" },
            { "Windows", "^.*Windows NT ([\\d.]+).*$", "Windows" }
     };

    /**
     * Windows版本信息 <br>
     * 格式： NT版本号，Windows版本号
     */
    private static String[][] windowsVersionInfo = { 
            { "10.0", "10" }, 
            { "6.4", "10" }, 
            { "6.3", "8.1" },
            { "6.2", "8" }, 
            { "6.1", "7" }, 
            { "6.0", "Vista" }, 
            { "5.2", "XP" }, 
            { "5.1", "XP" }, 
            { "5.0", "2000" } 
    };

    /** 在用户代理字符串中寻找浏览器信息
     * @param ua 用户代理字符串
     * @return 浏览器名称及版本
     */
    public static String findBrowser(String ua) {
        String result =  getResult(ua, browserInfo);
        if(result.equals("IE")) {
            String version = ua.replaceAll("^.*rv:([\\d.]+).*$", " $1");
            if (!ua.equals(version)) {
                result += version;
            }
        }
        return result;
    }

    /** 在用户代理字符串中寻找操作系统信息
     * @param ua 用户代理字符串
     * @return 操作系统名称及版本
     */
    public static String findOS(String ua) {
        String result = getResult(ua, osInfo);
        if (result.startsWith("Windows")) {
            for (String[] version : windowsVersionInfo) {
                if (result.endsWith(version[NT_VERSION])) {
                    result = result.replace(version[NT_VERSION], version[WINDOWS_VERSION]);
                    break;
                }
            }
        }
        return result;
    }

    private static String getResult(String ua, String[][] info) {
        for (String[] record : info) {
            for (int i = KEYWORD_START; i < record.length; ++i) {
                if (ua.contains(record[i])) {
                    if (record[REG_EXP_FOR_VERSION].isEmpty()) {
                        return record[NAME];
                    }
                    String version = ua.replaceAll(record[REG_EXP_FOR_VERSION], "$1");
                    if (ua.equals(version)) {
                        return record[NAME];
                    } else {
                        return record[NAME] + " " + version;
                    }
                }
            }
        }
        return "Unknown";
    }
}
