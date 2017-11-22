package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.IOException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	@Scheduled(fixedRate = 60000) // Выполняется каждые 60 сек.
	public void getKonachan() throws IOException {
		System.out.print("URL: ");
		
		//************* получаем URL картинки ********************
		String imageURL = KonachanWallPaperManager.instance().getImgURL();
		
		System.out.print("http:" + imageURL);
		
		//************* сохраняем файл ********************
		String imagePath = ImgDownloader.download(imageURL);
		
		//************** устанавливаем файл как обоину **********
		WallPaperSetter.setWP(imagePath);
		
		//************** done ***********************************
		System.out.println(";");
	}
}