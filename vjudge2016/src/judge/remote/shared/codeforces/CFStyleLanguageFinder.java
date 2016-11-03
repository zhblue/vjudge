package judge.remote.shared.codeforces;

import java.util.HashMap;
import java.util.LinkedHashMap;

import judge.remote.language.LanguageFinder;
import judge.tool.Handler;

import org.springframework.stereotype.Component;

@Component
public abstract class CFStyleLanguageFinder implements LanguageFinder {

    @Override
    public boolean isDiverse() {
        return true;
    }

    @Override
    public void getLanguages(String remoteProblemId, Handler<LinkedHashMap<String, String>> handler) {
        // TODO Auto-generated method stub
    }

    @Override
    public LinkedHashMap<String, String> getDefaultLanguages() {
        LinkedHashMap<String, String> languageList = new LinkedHashMap<>();
        languageList.put("10", "GNU GCC 5.1.0");
        languageList.put("43", "GNU GCC C11 5.1.0");
        languageList.put("1", "GNU G++ 5.1.0");
        languageList.put("42", "GNU G++11 5.1.0");
        languageList.put("50", "GNU G++14 6.2.0");
        languageList.put("2", "Microsoft Visual C++ 2010");
        languageList.put("9", "C# Mono 3.12.1.0");
        languageList.put("29", "MS C# .NET 4.0.30319");
        languageList.put("28", "D DMD32 v2.069.2");
        languageList.put("32", "Go 1.5.2");
        languageList.put("12", "Haskell GHC 7.8.3");
        languageList.put("36", "Java 1.8.0_66");
        languageList.put("19", "OCaml 4.02.1");
        languageList.put("3", "Delphi 7");
        languageList.put("4", "Free Pascal 2.6.4");
        languageList.put("13", "Perl 5.20.1");
        languageList.put("6", "PHP 5.4.42");
        languageList.put("7", "Python 2.7.10");
        languageList.put("31", "Python 3.5.1");
        languageList.put("40", "PyPy 2.7.10 (2.6.1)");
        languageList.put("41", "PyPy 3.2.5 (2.4.0)");
        languageList.put("8", "Ruby 2.0.0p645");
        languageList.put("49", "Rust 1.10");
        languageList.put("20", "Scala 2.11.7");
        languageList.put("34", "JavaScript V8 4.8.0");
        languageList.put("14", "ActiveTcl 8.5");
        languageList.put("15", "Io-2008-01-07 (Win32)");
        languageList.put("17", "Pike 7.8");
        languageList.put("18", "Befunge");
        languageList.put("22", "OpenCobol 1.0");
        languageList.put("25", "Factor");
        languageList.put("26", "Secret_171");
        languageList.put("27", "Roco");
        languageList.put("33", "Ada GNAT 4");
        languageList.put("38", "Mysterious Language");
        languageList.put("39", "FALSE");
        languageList.put("44", "Picat 0.9");
        languageList.put("45", "GNU C++11 5 ZIP");
        languageList.put("46", "Java 8 ZIP");
        languageList.put("47", "J");
        languageList.put("48", "Kotlin 1.0.1");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
