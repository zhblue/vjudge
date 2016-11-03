package judge.remote.provider.codeforcesgym;

import org.springframework.stereotype.Component;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleSubmitter;

@Component
public class CodeForcesGymQuerier extends CFStyleSubmitter {
    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesGymInfo.INFO;
    }
    
}
