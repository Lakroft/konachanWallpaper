//**********************************************
// Список прокси серверов я беру здесь: https://hidemy.name/ru/proxy-list/
// Проверить их работоспособность можно здесь: https://hidemy.name/ru/proxy-checker/
//**********************************************

package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

public class HttpConnector {
	private static String proxyURL = "31.182.52.156";//"5.135.195.166";
	private static int proxyPort = 3129;
	private ArrayList<ProxyRecord> proxyList; // = new ArrayList<>() {new ProxyRecord("31.182.52.156", 3129)}; //{new ProxyRecord("31.182.52.156", 3129)};
	private static Boolean useProxy = true;
	
	private static HttpConnector instance = null;
	public static HttpConnector instance() {
		if (instance == null) {
			instance = new HttpConnector();
		}
		return instance;
	}
	
	public HttpConnector() {
		this.proxyList = new ArrayList<>();
		// TODO Читать из проперти файла настройку использования прокси
		//TODO: Здесь загружать список прокси
		proxyList.add(new ProxyRecord("31.182.52.156", 3129));
	}
	
	public HttpURLConnection getConnection(String urlString) throws IOException {
		HttpURLConnection connection = null;
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
				if(connection != null && (connection.getResponseCode()==201 || connection.getResponseCode()==200)) {
					if (i > 0) {
						// перемещать прокси на первую строчку
						proxyList.remove(i);
						proxyList.add(0, proxyRec);
					}
					return connection;
				}
			} catch(Exception ignore) {}
		}
		//TODO: Генерировать эксэпшн
		System.out.println("No Availabel Proxy");
		return null;
	}
}