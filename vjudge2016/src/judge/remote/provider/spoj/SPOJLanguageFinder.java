package judge.remote.provider.spoj;

import judge.remote.RemoteOjInfo;
import judge.remote.language.LanguageFinder;
import judge.tool.Handler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class SPOJLanguageFinder implements LanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return SPOJInfo.INFO;
    }

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
        languageList.put("7", "Ada95 (gnat 6.3)");
        languageList.put("13", "Assembler 32 (nasm 2.12.01)");
        languageList.put("45", "Assembler 32 (gcc 6.3 )");
        languageList.put("42", "Assembler 64 (nasm 2.12.01)");
        languageList.put("105", "AWK (mawk 1.3.3)");
        languageList.put("104", "AWK (gawk 4.1.3)");
        languageList.put("28", "Bash (bash 4.4.5)");
        languageList.put("110", "BC (bc 1.06.95)");
        languageList.put("12", "Branf**k (bff 1.0.6)");
        languageList.put("81", "C (clang 4.0)");
        languageList.put("11", "C (gcc 6.3)");
        languageList.put("27", "C# (gmcs 4.6.2)");
        languageList.put("1", "C++ (gcc 6.3)");
        languageList.put("41", "C++ (g++ 4.3.2)");
        languageList.put("82", "C++14 (clang 4.0)");
        languageList.put("44", "C++14 (gcc 6.3)");
        languageList.put("34", "C99 (gcc 6.3)");
        languageList.put("14", "Clips (clips 6.24)");
        languageList.put("111", "Clojure (clojure 1.8.0)");
        languageList.put("118", "Cobol (opencobol 1.1.0)");
        languageList.put("91", "CoffeeScript (coffee 1.12.2)");
        languageList.put("31", "Common Lisp (sbcl 1.3.13)");
        languageList.put("32", "Common Lisp (clisp 2.49)");
        languageList.put("102", "D (dmd 2.072.2)");
        languageList.put("84", "D (ldc 1.1.0)");
        languageList.put("20", "D (gdc 6.3)");
        languageList.put("48", "Dart (dart 1.21)");
        languageList.put("96", "Elixir (elixir 1.3.3)");
        languageList.put("36", "Erlang (erl 19)");
        languageList.put("124", "F# (mono 4.0.0)");
        languageList.put("92", "Fantom (fantom 1.0.69)");
        languageList.put("107", "Forth (gforth 0.7.3)");
        languageList.put("5", "Fortran (gfortran 6.3)");
        languageList.put("114", "Go (go 1.7.4)");
        languageList.put("98", "Gosu (gosu 1.14.2)");
        languageList.put("121", "Groovy (groovy 2.4.7)");
        languageList.put("21", "Haskell (ghc 8.0.1)");
        languageList.put("16", "Icon (iconc 9.5.1)");
        languageList.put("9", "Intercal (ick 0.3)");
        languageList.put("24", "JAR (JavaSE 6)");
        languageList.put("10", "Java (HotSpot 8u112)");
        languageList.put("112", "JavaScript (SMonkey 24.2.0)");
        languageList.put("35", "JavaScript (rhino 1.7.7)");
        languageList.put("47", "Kotlin (kotlin 1.0.6)");
        languageList.put("26", "Lua (luac 5.3.3)");
        languageList.put("30", "Nemerle (ncc 1.2.0)");
        languageList.put("25", "Nice (nicec 0.9.13)");
        languageList.put("122", "Nim (nim 0.16.0)");
        languageList.put("56", "Node.js (node 7.4.0)");
        languageList.put("43", "Objective-C (gcc 6.3)");
        languageList.put("83", "Objective-C (clang 4.0)");
        languageList.put("8", "Ocaml (ocamlopt 4.01)");
        languageList.put("22", "Pascal (fpc 3.0.0)");
        languageList.put("2", "Pascal (gpc 20070904)");
        languageList.put("3", "Perl (perl 5.24.1)");
        languageList.put("54", "Perl (perl 6)");
        languageList.put("29", "PHP (php 7.1.0)");
        languageList.put("94", "Pico Lisp (pico 16.12.8)");
        languageList.put("19", "Pike (pike 8.0)");
        languageList.put("15", "Prolog (swi 7.2.3)");
        languageList.put("108", "Prolog (gnu prolog 1.4.5)");
        languageList.put("4", "Python (cpython 2.7.13)");
        languageList.put("99", "Python (PyPy 2.6.0)");
        languageList.put("116", "Python 3 (python  3.5)");
        languageList.put("126", "Python 3 nbc (python 3.4)");
        languageList.put("117", "R (R 3.3.2)");
        languageList.put("95", "Racket (racket 6.7)");
        languageList.put("17", "Ruby (ruby 2.3.3)");
        languageList.put("93", "Rust (rust 1.14.0)");
        languageList.put("39", "Scala (scala 2.12.1)");
        languageList.put("33", "Scheme (guile 2.0.13)");
        languageList.put("18", "Scheme (stalin 0.3)");
        languageList.put("97", "Scheme (chicken 4.11.0)");
        languageList.put("46", "Sed (sed 4)");
        languageList.put("23", "Smalltalk (gst 3.2.5)");
        languageList.put("40", "SQLite (sqlite 3.16.2)");
        languageList.put("85", "Swift (swift 3.0.2)");
        languageList.put("38", "TCL (tcl 8.6)");
        languageList.put("62", "Text (plain text)");
        languageList.put("115", "Unlambda (unlambda 0.1.4.2)");
        languageList.put("50", "VB.net (mono 4.6.2)");
        languageList.put("6", "Whitespace (wspace 0.3)");
        return languageList;
    }

    @Override
    public HashMap<String, String> getLanguagesAdapter() {
        // TODO Auto-generated method stub
        return null;
    }

}
