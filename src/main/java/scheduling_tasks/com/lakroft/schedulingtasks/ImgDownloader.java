package scheduling_tasks.com.lakroft.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

@Service
public class ImgDownloader {
	private Boolean keepImgFiles;
	private String imgCatalog;

	@Autowired
	private HttpConnector httpConnector;
	@Autowired
	private PropertiesLoader propertiesLoader;

	@PostConstruct
	private void initProperties() {
		String property = propertiesLoader.getProperty("keep_img", "true");
		keepImgFiles = Boolean.parseBoolean(property);
		imgCatalog = propertiesLoader.getProperty("img_catalog", "");
	}
	
	public String download(String url) throws IOException{
		
		HttpURLConnection connection = httpConnector.getConnection(url);
		String imagePath;
			
		InputStream in = connection.getInputStream();
		File imageFile = new File(decodeUrl(url));
		if (!imgCatalog.isEmpty()) imageFile.getParentFile().mkdirs();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(imageFile));
		for ( int i; (i = in.read()) != -1; ) {
			out.write(i);
		}
		in.close();
		out.close();
		imagePath = imageFile.getAbsolutePath();
		
		return imagePath;
	}
	
	private String decodeUrl(String url) {
		if (keepImgFiles) {
			try {
				String filename = imgCatalog + URLDecoder.decode(url.substring(url.lastIndexOf("/") + 1), "UTF-8");
				if (!filename.endsWith(".jpg") && !filename.endsWith(".jpeg") && !filename.endsWith(".png")) {
					filename += ".jpg";
				}
				return filename;
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Error decoding URL:" + url);
			}
		}
		return "test_image.jpg";
	}
}
