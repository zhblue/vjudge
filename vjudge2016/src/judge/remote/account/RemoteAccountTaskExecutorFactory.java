package judge.remote.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;

import judge.remote.RemoteOj;
import judge.remote.account.config.RemoteAccountOJConfig;
import judge.tool.ApplicationContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class RemoteAccountTaskExecutorFactory {
	private final static Logger log = LoggerFactory
			.getLogger(RemoteAccountTaskExecutorFactory.class);

	public File jsonConfig;

	public RemoteAccountTaskExecutorFactory(String jsonConfigPath) {

		String contextClassesPath = ApplicationContainer.serveletContext
				.getRealPath("/WEB-INF/classes");
		File json = new File(jsonConfigPath);
		if (!json.exists()) {
			json = new File(contextClassesPath + File.separator
					+ jsonConfigPath);
		}
		this.jsonConfig = json;
	}

	public RemoteAccountTaskExecutor create() throws JsonSyntaxException,
			JsonIOException, FileNotFoundException {
		Type type = new TypeToken<HashMap<RemoteOj, RemoteAccountOJConfig>>() {
		}.getType();
		HashMap<RemoteOj, RemoteAccountOJConfig> map = null;
		try {
			map = new Gson().fromJson(new InputStreamReader(
					new FileInputStream(jsonConfig), "UTF-8"), type);

			if (map.containsKey(null)) {
				log.error("Remote OJ account config contains unknown OJ name");
				System.exit(-1);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new RemoteAccountTaskExecutor(map);
	}

}
