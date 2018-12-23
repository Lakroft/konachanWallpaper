package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	@Autowired
	private KonachanWallPaperManager konachanWallPaperManager;
	@Autowired
	private ImgDownloader imgDownloader;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Scheduled(initialDelay=1000,fixedDelay = 60000) // Задержка запуска 1 сек. Выполняется через каждые 60 сек.
	public void getKonachan() throws IOException {
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
	}
}