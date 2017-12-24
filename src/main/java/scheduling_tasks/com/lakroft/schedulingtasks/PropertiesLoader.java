package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesLoader {
	private static PropertiesLoader instance = null;
	public static PropertiesLoader instance() {
		if (instance == null) {
			instance = new PropertiesLoader();
		}
		return instance;
	}
	
	private Properties appProps;
	
	private PropertiesLoader() {
		appProps = new Properties();
		try {
			appProps.load(new FileInputStream("app.properties"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println(appProps.getProperty("name"));
	}
	
	public String getProperty(String name, String def) {
		try {
			return appProps.getProperty(name);
		} catch(Exception ignore) {
			return def;
		}
	}
	public String getProperty(String name) {
		return getProperty(name, "");
	}
	
	public List<String> getInlinePropList(String name) {
		String raw = getProperty(name);
		if(raw!=null && !raw.isEmpty()) {
			return Arrays.asList(raw.split(","));
		}
		return new ArrayList<String>();
	}
	public String[] getInlinePropList(String name, String[] def) {
		String raw = getProperty(name);
		if(raw!=null && !raw.isEmpty()) {
			return raw.split(",");
		}
		return def;
	}
}
