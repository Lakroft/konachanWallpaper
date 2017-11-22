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

public class HttpConnector {
	private static String proxyURL = "31.182.52.156";//"5.135.195.166";
	private static int proxyPort = 3129;
	private static Boolean useProxy = true;
	
	public static HttpURLConnection getConnection(String urlString) throws IOException {
		HttpURLConnection connection = null;
		URL url = new URL(urlString);
		// TODO Читать из проперти файла настройку использования прокси
		
		// TODO Сделать ProxyManager
		if(useProxy) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyURL, proxyPort));
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
		// TODO: Добавить обработку эксепшинов
		
		return connection;
	}
}