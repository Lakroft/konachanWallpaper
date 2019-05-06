package scheduling_tasks.com.lakroft.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KonachanManager {
	private Logger logger = LogManager.getLogger(KonachanManager.class);
	@Autowired
	private KonachanWallPaperManager konachanWallPaperManager;
	@Autowired
	private ImgDownloader imgDownloader;
	@Autowired
	private PropertiesLoader propertiesLoader;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	//@Scheduled(initialDelay=1000,fixedDelay = 60000) // Задержка запуска 1 сек. Выполняется через каждые 60 сек.
	public void getKonachan() {
		try {
			System.out.print(dateFormat.format(new Date()) + " URL: ");

			//************* получаем URL картинки ********************
			String imageURL = konachanWallPaperManager.getImgURL();
			if (!imageURL.startsWith("http")) imageURL = "http:" + imageURL;

			System.out.print(imageURL);

			//************* сохраняем файл ********************
			String imagePath = imgDownloader.download(imageURL);

			//************** устанавливаем файл как обоину **********
			WallPaperSetter.setWP(imagePath);

			//************** done ***********************************
			System.out.println(";");
		} catch (Exception e) {
			if (Boolean.parseBoolean(propertiesLoader.getProperty("show_stack", "false"))) {
				logger.error("\n" + e.getMessage(), e);
			} else {
				logger.error("\n" + e.getMessage());
			}
		}
	}
}