package judge.remote.shared.codeforces;

import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.httpclient.SimpleNameValueEntityFactory;
import judge.remote.account.RemoteAccount;
import judge.remote.loginer.RetentiveLoginer;
import judge.remote.shared.codeforces.CodeForcesTokenUtil.CodeForcesToken;

import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public abstract class CFStyleLoginer extends RetentiveLoginer {

    @Override
    protected void loginEnforce(RemoteAccount account, DedicatedHttpClient client) {
        if (client.get("/").getBody().contains("/logout\">")) {
            return;
        }

        CodeForcesToken token = CodeForcesTokenUtil.getTokens(client);
        HttpEntity entity = SimpleNameValueEntityFactory.create( //
                "csrf_token", token.csrf_token, //
                "_tta", token._tta, //
                "action", "enter", //
                "handleOrEmail", account.getAccountId(), //
                "password", account.getPassword(), //
                "remember", "on");
        client.post("/enter", entity, HttpStatusValidator.SC_MOVED_TEMPORARILY);
    }

}
