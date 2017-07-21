package judge.remote.provider.csu;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class CSUCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CSUInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {
        return getHost().toURI() + "/csuoj/problemset/problem?pid=" + problemId;
    }
    
    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\d*"));
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {
        info.title = Tools.regFind(html, "<h1>\\s*<a.*?>\\s*\\d+:([\\s\\S]*?)\\s*</a></h1>").trim();
        info.timeLimit = (1000 * Integer.parseInt(Tools.regFind(html, "Time Limit:\\s*<span.*?>(\\d+) Sec")));
        info.memoryLimit = (1024 * Integer.parseInt(Tools.regFind(html, "Memory Limit:\\s*<span.*?>(\\d+) MB")));
        info.description = (Tools.regFind(html, "<h2.*?>Description</h2>([\\s\\S]*?)\\s*</div>\\s*<div name=\"Input"));
        info.input = (Tools.regFind(html, "<h2.*?>Input</h2>([\\s\\S]*?)\\s*</div>\\s*<div name=\"Output"));
        info.output = (Tools.regFind(html, "<h2.*?>Output</h2>([\\s\\S]*?)\\s*</div>\\s*<div name=\"Sample Input"));
        info.sampleInput = (Tools.regFind(html, "<h2.*?>Sample Input</h2>([\\s\\S]*?)\\s*</div>\\s*<div name=\"Sample Output").replaceAll("<span", "<pre").replaceAll("</span>", "</pre>"));
        info.sampleOutput = (Tools.regFind(html, "<h2.*?>Sample Output</h2>([\\s\\S]*?)\\s*</div>\\s*<div name=\"Hint").replaceAll("<span", "<pre").replaceAll("</span>", "</pre>"));
        info.hint = (Tools.regFind(html, "<h2.*?>Hint</h2>([\\s\\S]*?)\\s*</div>\\s*<div name=\"Source"));
        info.source = (Tools.regFind(html, "<h2.*?>Source</h2>([\\s\\S]*?)</div>"));
    }

}
