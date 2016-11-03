package judge.remote.provider.codeforces;

import org.springframework.stereotype.Component;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleLoginer;

@Component
public class CodeForcesLoginer extends CFStyleLoginer {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }
}
