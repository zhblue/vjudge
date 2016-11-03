package judge.remote.provider.codeforcesgym;

import org.springframework.stereotype.Component;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleLanguageFinder;

@Component
public class CodeForcesGymLanguageFinder extends CFStyleLanguageFinder  {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesGymInfo.INFO;
    }

}
