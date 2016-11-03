package judge.remote.provider.codeforces;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleLanguageFinder;

import org.springframework.stereotype.Component;

@Component
public class CodeForcesLanguageFinder extends CFStyleLanguageFinder {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }

}
