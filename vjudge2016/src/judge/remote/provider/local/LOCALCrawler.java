package judge.remote.provider.local;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SyncCrawler;
import judge.tool.HtmlHandleUtil;
import judge.tool.Tools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpHost;
import org.springframework.stereotype.Component;

@Component
public class LOCALCrawler extends SyncCrawler {
    @Override
    public RemoteOjInfo getOjInfo() {
        return LOCALInfo.INFO;
    }

    private static final HttpHost HOSTS[] = new HttpHost[] {
        new HttpHost(LOCALInfo.getDomain(),LOCALInfo.getPort())
    };

    @Override
    public RawProblemInfo crawl(String problemId) throws Exception {
        preValidate(problemId);
        
        String problemUrl = null;
        String html = null;
        for (HttpHost host : HOSTS) {
            try {
                DedicatedHttpClient client = dedicatedHttpClientFactory.build(host);
                //client.get(LOCALInfo.PATH+"/setlang.php?lang=en").getBody();
                problemUrl = getProblemUrl(host, problemId);
                html = client.get(problemUrl, HttpStatusValidator.SC_OK).getBody();
                System.out.println("hhh=>"+html);
                html = HtmlHandleUtil.transformUrlToAbs(html, problemUrl);
                break;
            } catch (Throwable t) {
            	t.printStackTrace();
            }
        }
        Validate.notBlank(html);
        
        RawProblemInfo info = new RawProblemInfo();
        info.title = Tools.regFind(html, "<center><h2>([\\s\\S]*?)</h2>").replaceAll(problemId + ": ", "").trim();
        info.source = (Tools.regFind(html, "<h2>Source</h2>[\\s\\S]*?<div class=content><p>([\\s\\S]*?)</p></div><center>"));
        Matcher matcher = Pattern.compile("\\[(.*)\\](.*)").matcher(info.title);
        if (matcher.find()) {
            info.title = matcher.group(2);
            info.source = matcher.group(1);
        }
        info.timeLimit=1000;
        info.memoryLimit=256000;
        try{
        info.timeLimit = (1000 * Integer.parseInt(Tools.regFind(html, ": </span>(\\d+) Sec")));
        info.memoryLimit = (1024 * Integer.parseInt(Tools.regFind(html, ": </span>(\\d+) MB")));
        }catch(Exception e){
        	
        }
        info.description = (Tools.regFind(html, "<h2>Description</h2>([\\s\\S]*?)<h2>Input</h2>"));
        info.input = (Tools.regFind(html, "<h2>Input</h2>([\\s\\S]*?)<h2>Output</h2>"));
        info.output = (Tools.regFind(html, "<h2>Output</h2>([\\s\\S]*?)<h2>Sample Input</h2>"));
        info.sampleInput = (Tools.regFind(html, "<h2>Sample Input</h2>([\\s\\S]*?)<h2>Sample Output</h2>").replaceAll("<span", "<pre").replaceAll("</span>", "</pre>").replace("<br /> ", "<br />"));
        info.sampleOutput = (Tools.regFind(html, "<h2>Sample Output</h2>([\\s\\S]*?)<h2>HINT</h2>").replaceAll("<span", "<pre").replaceAll("</span>", "</pre>").replace("<br /> ", "<br />"));
        info.hint = (Tools.regFind(html, "<h2>HINT</h2>([\\s\\S]*?)<h2>Source</h2>"));
        info.url = problemUrl;
        if("".equals(info.description)){
        	int start=html.indexOf("题目描述");
        	int end=html.indexOf("来源");
        	info.description=html.substring(start,end);
        }
        Validate.isTrue(!StringUtils.isBlank(info.title));

        return info;
    }

    protected String getProblemUrl(HttpHost host, String problemId) {
        return host.toURI() + LOCALInfo.getPath()+"/problem.php?id=" + problemId;
    }
    
    protected void preValidate(String problemId) {
    	System.out.println(problemId);
        Validate.isTrue(problemId.matches("[1-9]\\d*"));
    }

}
