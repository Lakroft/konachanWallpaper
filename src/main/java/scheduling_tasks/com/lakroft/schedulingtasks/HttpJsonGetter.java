package scheduling_tasks.com.lakroft.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

@Service
public class HttpJsonGetter {

	@Autowired
	private HttpConnector connector;

	public String getJson(String url) throws IOException {
		String jsonAnswer = "connection error";

		HttpURLConnection connection = connector.getConnection(url); //
		
//		if(connection.getResponseCode()==201 || connection.getResponseCode()==200) {
			
			InputStream is = connection.getInputStream();
			
			byte[] buffer = new byte[8192]; 
			int bytesRead;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((bytesRead = is.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			byte[] data = baos.toByteArray();
			jsonAnswer = new String(data, "UTF-8");
//		} else {
//			System.out.println("\nConnection responce code: " + connection.getResponseCode());
//			System.out.println("Response message: " + connection.getResponseMessage());
//		}
		return jsonAnswer;
	}
}
