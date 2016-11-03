package judge.remote.provider.codeforces;

import judge.remote.RemoteOjInfo;
import judge.remote.shared.codeforces.CFStyleSubmitter;

import org.springframework.stereotype.Component;


@Component
public class CodeForcesSubmitter extends CFStyleSubmitter {

    @Override
    public RemoteOjInfo getOjInfo() {
        return CodeForcesInfo.INFO;
    }

}
