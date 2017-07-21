package judge.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import judge.remote.shared.FileDownloader;

public class HtmlHandleUtil {
    private final static Logger log = LoggerFactory.getLogger(HtmlHandleUtil.class);

    private static final String[][] pairs = new String[][] {
        { "img", "src" },
        { "script", "src" },
        { "link", "href" },
        { "a", "href" }
    };

    public static String transformUrlToAbs(String html, String baseUri) {
        Document doc = Jsoup.parse(html, baseUri);
        for (String[] pair : pairs) {
            Elements links = doc.select(pair[0]);
            for (Element element : links) {
                element.attr(pair[1], element.absUrl(pair[1]));
            }
        }
        return doc.toString()
                .replaceAll(">\\s*<", "><")
                .replaceAll("(?<=\\w)\\s+<(?!/pre)", " <")
                .replaceAll("(?<!pre)>\\s+(?=\\w)", "> ");
    }

    public static String transformUrlToAbsBody(String html, String baseUri) {
        Document doc = Jsoup.parseBodyFragment(html, baseUri);
        for (String[] pair : pairs) {
            Elements links = doc.select(pair[0]);
            for (Element element : links) {
                element.attr(pair[1], element.absUrl(pair[1]));
            }
        }
        return doc.body().toString()
                .replaceAll(">\\s*<", "><")
                .replaceAll("(?<=\\w)\\s+<(?!/pre)", " <")
                .replaceAll("(?<!pre)>\\s+(?=\\w)", "> ");
    }

    public static List<String> getStyleSheet(String html) {
        List<String> result = new ArrayList<String>();
        Matcher matcher = Pattern.compile("(?i)<link[^<>]*text/css[^<>]*/>|<style[^<>]*>[\\s\\S]*?</style>").matcher(html);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    public static String transformImgUrlToLocal(String html){
        if(html == null){
            return null;
        }
        Document doc = Jsoup.parseBodyFragment(html);
        Elements links = doc.select("img");

        for (Element element : links) {
            try{
                String src = element.absUrl("src");
                if(!src.startsWith("http://") && !src.startsWith("https://")){
                    continue;
                }
                while(src.contains("acm.hdu.edu.cn/../")){
                    src = src.replace("acm.hdu.edu.cn/../", "acm.hdu.edu.cn/");
                }
                String folder = src.replace("http://", "").replace("https://", "")
                        .replace("www.", "").replace("acm.", "")
                        .replace(".com", "").replace(".cn", "").replace(".edu", "").replace(".org", "")
                        .replaceAll("[^\\w/]", "_");
                folder = folder.substring(0, folder.lastIndexOf("/"));
                String savePath =  HtmlHandleUtil.class.getResource("/").getPath().
                        replace("WEB-INF/classes/", "ojFiles/") + folder;

                int index = Math.max(src.lastIndexOf("/"),src.lastIndexOf("?")) + 1;
                String fileName = src.substring(index);
                FileDownloader.downLoadFromUrl(src,savePath,fileName);
                element.attr("src", "../ojFiles/" + folder + "/" + java.net.URLEncoder.encode(fileName,"UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return doc.body().html();
    }

    public static void main(String[] args) {
        String s = "<br>\n45";
        log.info(s.replaceAll("(?<!pre)>\\s* \\s*(?=\\w)", "> "));
    }

}
