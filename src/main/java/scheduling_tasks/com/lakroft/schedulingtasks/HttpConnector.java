//**********************************************
// Список прокси серверов я беру здесь: https://hidemy.name/ru/proxy-list/
// Проверить их работоспособность можно здесь: https://hidemy.name/ru/proxy-checker/
//**********************************************

package scheduling_tasks.com.lakroft.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

@Service
public class HttpConnector {
	private ArrayList<ProxyRecord> proxyList; // = new ArrayList<>() {new ProxyRecord("31.182.52.156", 3129)}; //{new ProxyRecord("31.182.52.156", 3129)};
	private static Boolean useProxy = true;

	@Autowired
	private PropertiesLoader propertiesLoader;

	@PostConstruct
	private void initProxies() {
		this.proxyList = new ArrayList<>();
		// TODO Читать из проперти файла настройку использования прокси
		String[] proxies = propertiesLoader.getPropList("proxy");
		for (String proxy : proxies) {
			String[] list = proxy.split(",");
			for (String entry : list) {
				proxyList.add(ProxyRecord.StringToProxyRec(entry));
			}
		}
		if (proxyList.isEmpty()) proxyList.add(new ProxyRecord("31.182.52.156", 3129));
	}
	
	public HttpURLConnection getConnection(String urlString) throws IOException {
		HttpURLConnection connection;
		URL url = new URL(urlString);
		ProxyRecord proxyRec;
		for (int i = 0; i < proxyList.size(); i++) {
			proxyRec = proxyList.get(i);
			try {
				if(useProxy) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyRec.getUrl(), proxyRec.getPort()));
					connection = (HttpURLConnection) url.openConnection(proxy);
				} else {
					connection = (HttpURLConnection) url.openConnection();
				}
				
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-length",  "0");
				connection.setUseCaches(false);
				connection.setAllowUserInteraction(false);
				//connection.setConnectTimeout(2000);
				//connection.setReadTimeout(2000);
				connection.connect();
				if(connection.getResponseCode()==201 || connection.getResponseCode()==200) {
					if (i > 0) {
						// перемещать прокси на первую строчку
						proxyList.remove(i);
						proxyList.add(0, proxyRec);
					}
					return connection;
				}
			} catch(Exception ignored) {}
		}
		//TODO: Генерировать эксэпшн
		System.out.println("No Availabel Proxy");
//		throw new RuntimeException("No Availabel Proxy");
		return null;
	}
}