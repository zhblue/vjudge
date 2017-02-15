package judge.remote.provider.tyvj;

import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.crawler.SimpleCrawler;
import judge.tool.Tools;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

@Component
public class TyvjCrawler extends SimpleCrawler {

    @Override
    public RemoteOjInfo getOjInfo() {
        return TyvjInfo.INFO;
    }

    @Override
    protected String getProblemUrl(String problemId) {
        return getHost().toURI() + "/p/" + problemId;
    }

    @Override
    protected void preValidate(String problemId) {
        Validate.isTrue(problemId.matches("[1-9]\\d{3,}"));
    }

    @Override
    protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) {
        Validate.isTrue(!html.contains("<blockquote><p>没有找到题目</p></blockquote>"));
        info.title = Tools.regFind(html, "<title>P" + problemId + " (.+) - Tyvj Online Judge</title>").trim();
        info.timeLimit = Integer.parseInt(Tools.regFind(html, "时间: (\\d+)ms / 空间: (\\d+)KiB / Java类名"));
        info.memoryLimit = Integer.parseInt(Tools.regFind(html, "ms / 空间: (\\d+)KiB / Java类名"));
        String background =  Tools.regFind(html, "<h2>背景</h2>\\s*([\\d\\D]*?</div>)");
        if(background.length() > 0){
            background = "<h3>背景</h3>" + background + "<h3>描述</h3>";
        }
        info.description = background + Tools.regFind(html, "<h2>描述</h2>\\s*([\\d\\D]*?</div>)");
        info.input = Tools.regFind(html, "<h2>输入格式</h2>\\s*([\\d\\D]*?</div>)");
        info.output = Tools.regFind(html, "<h2>输出格式</h2>\\s*([\\d\\D]*?</div>)");
        info.sampleInput = Tools.regFind(html, "<h3>输入</h3>\\s*([\\d\\D]*?</blockquote>)");
        info.sampleOutput = Tools.regFind(html, "<h3>输出</h3>\\s*([\\d\\D]*?</blockquote>)");
        info.hint = Tools.regFind(html, "<h2>备注</h2>\\s*([\\d\\D]*?</div>)");
    }

}
