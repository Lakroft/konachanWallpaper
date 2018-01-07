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
	
	public static ProxyRecord StringToProxyRec(String url) {
		String[] proxy = url.split(":");
		if (proxy.length != 2) return null;
		return new ProxyRecord(proxy[0], Integer.parseInt(proxy[1]));
	}
}
