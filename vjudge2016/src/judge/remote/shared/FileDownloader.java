package judge.remote.shared;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

public class FileDownloader {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static final long NOT_REDOWNLOAD_IN = 1000L * 60 * 60 * 1;//an hour
    private static  SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
    private final static Logger log = LoggerFactory.getLogger(FileDownloader.class);
    private static final int TRY_TIMES = 5;

    static {
        dateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }

    public static void downLoadFromUrl(String urlStr,String savePath,String fileName) throws Exception{
        log.info(urlStr);
        FileOutputStream fos = null;
        try {
            Connection connection = Jsoup.connect(urlStr)
                    .header("Referer",urlStr.substring(0,urlStr.indexOf("/", "https://".length())))
                    .userAgent(USER_AGENT)
                    .timeout(10000).ignoreContentType(true);

            File saveDir = new File(savePath);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
            File file = new File(saveDir+File.separator+fileName);
            if(file.exists()){
                Date lastModified = new Date(file.lastModified());
                log.info("already downloaded at " + lastModified.toString());
                if((new Date()).getTime() - lastModified.getTime() <  NOT_REDOWNLOAD_IN){
                    log.info("ignore");
                    return ;
                }
                connection.header("If-Modified-Since", dateFormat.format(lastModified));
            }
            int tryTimesLeft = TRY_TIMES;
            Response response = null;
            while(--tryTimesLeft >= 0){
                try{
                    response = connection.execute();
                } catch (HttpStatusException e){
                    if(e.getStatusCode() == HttpStatus.SC_NOT_MODIFIED){
                        log.info("Not Modified");
                        return ;
                    } else {
                        throw e;
                    }
                } catch (SocketTimeoutException e){
                    log.error(e.getMessage());
                    if(tryTimesLeft == 0){
                        if(file.exists()){
                            log.info("failed, use the local file");
                            return ;
                        } else {
                            throw e;
                        }
                    }
                }
            }
            fos = new FileOutputStream(file);
            fos.write(response.bodyAsBytes());
            log.info("download success : " + savePath + "/" + fileName);
        } finally {
            if(fos!=null){
                fos.close();
            }
        }
    }


    public static void  downLoadFromUrl(String urlStr,String savePath) throws Exception{
        String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
        downLoadFromUrl(urlStr,savePath,fileName);
    }

}
