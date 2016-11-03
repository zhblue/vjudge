package judge.remote.provider.codeforcesgym;

import org.springframework.stereotype.Component;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleLoginer;

@Component
public class CodeForcesGymLoginer extends CFStyleLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesGymInfo.INFO;
    }

}
