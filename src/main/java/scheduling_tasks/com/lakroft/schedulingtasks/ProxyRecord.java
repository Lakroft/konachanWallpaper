package scheduling_tasks.com.lakroft.schedulingtasks;

public class ProxyRecord {
	private String url;
	private int port;
	
	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}

	public ProxyRecord(String url, int port) {
		super();
		this.url = url;
		this.port = port;
	}
	
	//TODO: public static StringToProxyRec(String url)
}
