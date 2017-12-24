package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.IOException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	@Scheduled(initialDelay=6000,fixedDelay = 6000) // Задержка запуска 6 сек. Выполняется через каждые 60 сек.
	public void getKonachan() throws IOException {
		//PropertiesLoader.instance();
		System.out.print("URL: ");
		
		//************* получаем URL картинки ********************
		String imageURL = KonachanWallPaperManager.instance().getImgURL();
		if (!imageURL.startsWith("http")) imageURL = "http:" + imageURL;
		
		System.out.print(imageURL);
		
		//************* сохраняем файл ********************
		String imagePath = ImgDownloader.download(imageURL);
		
		//************** устанавливаем файл как обоину **********
		WallPaperSetter.setWP(imagePath);
		
		//************** done ***********************************
		System.out.println(";");
	}
}