package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ImgDownloader {
	public static String download(String url) throws IOException{
		
		HttpURLConnection connection = HttpConnector.getConnection(url); //
		String imagePath = "";
		
		if(connection.getResponseCode()==201 || connection.getResponseCode()==200) {
			
			InputStream in = connection.getInputStream();
			
			File imageFile = new File("test_image.jpg");
			
			OutputStream out = new BufferedOutputStream(new FileOutputStream(imageFile));
			for ( int i; (i = in.read()) != -1; ) {
			    out.write(i);
			}
			in.close();
			out.close();
			imagePath = imageFile.getAbsolutePath();
		} else {
			System.out.println("\nConnection responce code: " + connection.getResponseCode());
			System.out.println("Response message: " + connection.getResponseMessage());
		}
		
		return imagePath;
	}
}
