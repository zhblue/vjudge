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
               // System.out.println("hhh=>"+html);
                html = HtmlHandleUtil.transformUrlToAbs(html, problemUrl);
                break;
            } catch (Throwable t) {
            	t.printStackTrace();
            }
        }
        Validate.notBlank(html);
        
        RawProblemInfo info = new RawProblemInfo();
        info.title = Tools.regFind(html, "<center><h3>([\\s\\S]*?)</h3>").replaceAll(problemId + ": ", "").trim();
        info.source = (Tools.regFind(html, "<h4>来源</h4>[\\s\\S]*?<div class=content><p>([\\s\\S]*?)</p></div><center>"));
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
        info.description = (Tools.regFind(html, "<h4>Description</h4>([\\s\\S]*?)<h4>Input</h4>"));
        info.input = (Tools.regFind(html, "<h4>Input</h4>([\\s\\S]*?)<h4>Output</h4>"));
        info.output = (Tools.regFind(html, "<h4>Output</h4>([\\s\\S]*?)<h4>Sample Input</h4>"));
        info.sampleInput = (Tools.regFind(html, "<h4>Sample Input</h4>([\\s\\S]*?)<h4>Sample Output</h4>").replaceAll("<span", "<pre").replaceAll("</span>", "</pre>").replace("<br /> ", "<br />"));
        info.sampleOutput = (Tools.regFind(html, "<h4>Sample Output</h4>([\\s\\S]*?)<h4>HINT</h4>").replaceAll("<span", "<pre").replaceAll("</span>", "</pre>").replace("<br /> ", "<br />"));
        info.hint = (Tools.regFind(html, "<h4>HINT</h4>([\\s\\S]*?)<h4>Source</h4>"));
        info.url = problemUrl;
        if(html.contains("MarkForVirtualJudge")){
        	int start=html.indexOf("<!--StartMarkForVirtualJudge-->")+"<!--StartMarkForVirtualJudge-->".length();
        	int end=html.indexOf("<!--EndMarkForVirtualJudge-->")-1;
        	info.description=html.substring(start,end);
        }else{
        	int start=html.indexOf("题目描述")+"题目描述".length();
        	int end=html.lastIndexOf("来源")-1;
        	info.description=html.substring(start,end);
        }
        System.out.println("titile:"+info.title);
        //Validate.isTrue(!StringUtils.isBlank(info.title));

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
