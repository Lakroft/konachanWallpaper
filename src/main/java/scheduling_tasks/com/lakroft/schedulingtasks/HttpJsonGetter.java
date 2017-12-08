package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpJsonGetter {
	
	public static String GetJson(String url) throws IOException {
		String jsonAnser = "connection error";
		
		HttpURLConnection connection = HttpConnector.getConnection(url); //
		
		if(connection.getResponseCode()==201 || connection.getResponseCode()==200) {
			
			InputStream is = connection.getInputStream();
			
			byte[] buffer = new byte[8192]; 
			int bytesRead;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((bytesRead = is.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			byte[] data = baos.toByteArray();
			jsonAnser = new String(data, "UTF-8");
		} else {
			System.out.println("\nConnection responce code: " + connection.getResponseCode());
			System.out.println("Response message: " + connection.getResponseMessage());
		}
		return jsonAnser;
	}
}
