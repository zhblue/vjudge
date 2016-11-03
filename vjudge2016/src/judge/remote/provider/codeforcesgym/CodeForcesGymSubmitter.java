package judge.remote.provider.codeforcesgym;

import org.springframework.stereotype.Component;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleQuerier;

@Component
public class CodeForcesGymSubmitter extends CFStyleQuerier {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesGymInfo.INFO;
    }
}
