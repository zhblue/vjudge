package judge.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import judge.executor.ExecutorTaskType;
import judge.executor.Task;
import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.DedicatedHttpClientFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;

public class PhysicalAddressTool {

    static private Map<String, String> addressMap = new ConcurrentHashMap<String, String>();

    static public String getPhysicalAddress(final String ips) {
        List<String> locations = new ArrayList<String>();
        for (String ip : ips.split("[^\\w\\.:]+")) {
            locations.add(getPhysicalAddressOne(ip));
        }
        return StringUtils.join(locations, ", ");
    }
    
    static private String getPhysicalAddressOne(final String ip) {
        if (addressMap.containsKey(ip)) {
            return addressMap.get(ip);
        }
        if("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
            addressMap.put(ip, "localhost");
            return "localhost";
        }
        if(isPrivateIP(ip)) {
            addressMap.put(ip, "LAN");
            return "LAN";
        }
        addressMap.put(ip, "N/A");
        new Task<String>(ExecutorTaskType.GENERAL) {
            @Override
            public String call() throws Exception {
                HttpHost host = new HttpHost("www.ip138.com");
                DedicatedHttpClient client = SpringBean.getBean(DedicatedHttpClientFactory.class).build(host, "gb2312");
                String html = client.get("/ips138.asp?ip=" + ip).getBody();
                String physicalAddress = Tools.regFind(html, "<li>本站数据：(.+?)</li>");
                if (!StringUtils.isBlank(physicalAddress)) {
                    addressMap.put(ip, physicalAddress);
                }
                if (addressMap.size() > 5000) {
                    addressMap.clear();
                }
                return null;
            }
        }.submit();
        return addressMap.get(ip);
    }
    
    private static boolean isPrivateIP(String ip) {
        if(ip.contains(":")) {                                                  // IPv6
            ip = ip.toUpperCase();
            return ip.startsWith("FE") && 'C' <= ip.charAt(2);                  // FEC0::/10
        }
        if(ip.startsWith("10.")) {                                              // A类
            return true;
        }
        String[] s = ip.split("\\.");
        int num = (Integer.parseInt(s[0]) << 8) + Integer.parseInt(s[1]);
        return (172 << 8) + 16 <= num && num <= (172 << 8)  + 31                // B类
                || (192 << 8) + 168 == num;                                     // C类
    }

    static public int getIpMapSize() {
        return addressMap.size();
    }
    
}
