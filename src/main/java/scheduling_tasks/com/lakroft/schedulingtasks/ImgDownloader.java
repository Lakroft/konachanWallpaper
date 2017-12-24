package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

public class ImgDownloader {
	public static Boolean keepImgFiles = true;
	
	public static String download(String url) throws IOException{
		
		HttpURLConnection connection = HttpConnector.instance().getConnection(url); //
		String imagePath = "";
		
//		if(connection.getResponseCode()==201 || connection.getResponseCode()==200) {
			
			InputStream in = connection.getInputStream();
			//System.out.println("\n" + decodeUrl(url));
			File imageFile = new File(decodeUrl(url));
			
			OutputStream out = new BufferedOutputStream(new FileOutputStream(imageFile));
			for ( int i; (i = in.read()) != -1; ) {
			    out.write(i);
			}
			in.close();
			out.close();
			imagePath = imageFile.getAbsolutePath();
//		} else {
//			System.out.println("\nConnection responce code: " + connection.getResponseCode());
//			System.out.println("Response message: " + connection.getResponseMessage());
//		}
		
		return imagePath;
	}
	
	private static String decodeUrl(String url) {
		if (keepImgFiles) {
			try {
				String filename = URLDecoder.decode(url.substring(url.lastIndexOf("/") + 1), "UTF-8");
				if (!filename.endsWith(".jpg") && !filename.endsWith(".jpeg") && !filename.endsWith(".png")) {
					filename += ".jpg";
				}
				return filename;
			} catch (UnsupportedEncodingException e) {
				System.out.println("Error decoding URL:" + url);
			}
		}
		return "test_image.jpg";
	}
}
