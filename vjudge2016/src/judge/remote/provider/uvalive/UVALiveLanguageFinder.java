package judge.remote.provider.uvalive;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class UVALiveLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return UVALiveInfo.INFO;
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
        languageList.put("1", "ANSI C 5.3.0 - GNU C Compiler");
        languageList.put("2", "JAVA 1.8.0 - OpenJDK Java");
        languageList.put("3", "C++ 5.3.0 - GNU C++");
        languageList.put("4", "PASCAL 3.0.0 - Free Pascal Compiler");
        languageList.put("5", "C++11 5.3.0 - GNU C++ Compiler");
        languageList.put("6", "PYTH3 3.5.1 - Python 3");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
