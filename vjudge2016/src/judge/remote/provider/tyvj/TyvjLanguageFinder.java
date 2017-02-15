package judge.remote.provider.tyvj;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.tool.Handler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class TyvjLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return TyvjInfo.INFO;
    }

    @Override
    public boolean isDiverse() {
        return false;
    }

    @Override
    public void getLanguages(String remoteProblemId, Handler<LinkedHashMap<String, String>> handler) {
        // TODO Auto-generated method stub
    }

    @Override
    public LinkedHashMap<String, String> getDefaultLanguages() {
        LinkedHashMap<String, String> languageList = new LinkedHashMap<String, String>();
        languageList.put("0", "C");
        languageList.put("1", "C++");
        languageList.put("2", "C++11");
        languageList.put("3", "Java");
        languageList.put("4", "Pascal");
        languageList.put("5", "Python 2.7");
        languageList.put("6", "Python 3.3");
        languageList.put("7", "Ruby");
        languageList.put("8", "C#");
        languageList.put("9", "VB.Net");
        languageList.put("10", "F#");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
