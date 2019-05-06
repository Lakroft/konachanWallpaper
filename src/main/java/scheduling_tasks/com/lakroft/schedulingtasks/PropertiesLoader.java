package scheduling_tasks.com.lakroft.schedulingtasks;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;

@Service
public class PropertiesLoader {
	Logger logger = LogManager.getLogger(PropertiesLoader.class);

	private Properties appProps;
	
	private PropertiesLoader() {
		appProps = new Properties();
		try {
			appProps.load(new FileInputStream("app.properties"));
		} catch(Exception e) {
			logger.error("No Properties file", e);
		}
	}
	
	public String getProperty(String name, String def) {
		String value = appProps.getProperty(name);
		if (value != null) return value;
		else return def;
	}

	public String getProperty(String name) {
		return getProperty(name, null);
	}
	
	public List<String> getInlinePropList(String name) {
		String raw = getProperty(name);
		if(raw!=null && !raw.isEmpty()) {
			return Arrays.asList(raw.split(","));
		}
		return Collections.emptyList();
	}
	public String[] getInlinePropList(String name, String[] def) {
		String raw = getProperty(name);
		if(raw!=null && !raw.isEmpty()) {
			return raw.split(",");
		}
		return def;
	}
	public String[] getPropList(String name) {
		ArrayList<String> result = new ArrayList<>();
		
		String proxy = getProperty(name);
		if (proxy != null) result.add(proxy);
			
		for (int i = 0;;i++) {
			proxy = getProperty(name + "." + i);
			if (proxy == null) return result.toArray(new String[result.size()]);
			else result.add(proxy);
		}
	}
}
