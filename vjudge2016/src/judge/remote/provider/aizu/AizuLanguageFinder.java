package judge.remote.provider.aizu;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public class AizuLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return AizuInfo.INFO;
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
        languageList.put("C", "C");
        languageList.put("C++", "C++");
        languageList.put("JAVA", "JAVA");
        languageList.put("C++11", "C++11");
        languageList.put("C++14", "C++14");
        languageList.put("C#", "C#");
        languageList.put("D", "D");
        languageList.put("Ruby", "Ruby");
        languageList.put("Python", "Python");
        languageList.put("Python3", "Python3");
        languageList.put("PHP", "PHP");
        languageList.put("JavaScript", "JavaScript");
        languageList.put("Scala", "Scala");
        languageList.put("Haskell", "Haskell");
        languageList.put("OCaml", "OCaml");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        return null;
    }

}
