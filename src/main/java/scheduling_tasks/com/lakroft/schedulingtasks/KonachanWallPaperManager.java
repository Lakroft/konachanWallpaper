package scheduling_tasks.com.lakroft.schedulingtasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scheduling_tasks.com.lakroft.json.KonachanDatum;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.List;

@Service
public class KonachanWallPaperManager {
	// лист ссылок на обоины
	
	//http://konachan.com/post.json?tags=catgirl+tail&limit=10&page=0
	private static final String BASIC_URL_HEAD = "http://konachan.com/post.json?tags=";
	private String tags;
	private static final String BASIC_URL_TAIL = "&limit=10&page=";
	private int pageNum = 1;
	
	private String[] blackList; //DONE: Сделать загрузку черного списка из проперти файла
	
	private Boolean useSizeFilter = false;
	private Integer imgHeight = 1080;
	private Integer imgWidth = 1920;

	@Autowired
	private HttpJsonGetter httpJsonGetter;
	@Autowired
	private PropertiesLoader propertiesLoader;

	@PostConstruct
	private void initProperties() {
		this.tags = propertiesLoader.getProperty("tags", "catgirl+tail");
		this.blackList = propertiesLoader.getInlinePropList("blacklist",
			new String[] {
				"male"
				,"penis"
				,"pussy_juice"
				,"pussy"
			});
		this.useSizeFilter = Boolean.parseBoolean(propertiesLoader.getProperty("size_filter", "false"));
		this.imgHeight = Integer.parseInt(propertiesLoader.getProperty("min_image_height", "1080"));
		this.imgWidth = Integer.parseInt(propertiesLoader.getProperty("min_image_width", "1920"));
		//DONE: Сделать загрузку параметров фильтра по размерам и размеров.
	}
	
	private ArrayDeque<String> imgURLs = new ArrayDeque<>();
	
	public void setTags(String tags){
		if (tags == null) return;
		if (tags.isEmpty()) return;
		this.tags = tags;
	}
	
	String getImgURL() throws IOException { // Возвращает ссылку на очередную картинку.
		if (imgURLs.isEmpty()){
			fillQueue();
		}
		return imgURLs.poll();
	}
	
	private void fillQueue() throws IOException { // Заполняет очередь ссылок на картинки
		do {
			String url = BASIC_URL_HEAD + tags + BASIC_URL_TAIL + pageNum;
			String jsonAnswer = httpJsonGetter.getJson(url);
			
			Type itemsListType = new TypeToken<List<KonachanDatum>>() {}.getType();
			// https://habrahabr.ru/post/253266/
			Gson gson = new Gson();
			List<KonachanDatum> data = gson.fromJson(jsonAnswer, itemsListType);
			if (data == null || data.isEmpty()) { // Проверяем, что на этой странице еще есть данные
				pageNum = 0; // Данных нет, начинаем с первой страницы
				continue;
			} else { pageNum++; }
			
			for (KonachanDatum curr : data) {
				if (blackListPassed(curr.getTags()) && imgSizePassed(curr)) {
					imgURLs.add(curr.getJpegUrl());
				}
			}
			
		} while (imgURLs.isEmpty());
	}
	
	private Boolean blackListPassed(String tags) {
		for (String blackTag : blackList){
			if (tags.contains(blackTag)) {
				return false;
			}
		}
		return true;
	}
	
	private Boolean imgSizePassed(KonachanDatum datum) {
		return !(useSizeFilter && (datum.getHeight() < imgHeight || datum.getWidth() < imgWidth));
//		if(useSizeFilter && (datum.getHeight() < imgHeight || datum.getWidth() < imgWidth)) return false;
//		return true;
	}
}
