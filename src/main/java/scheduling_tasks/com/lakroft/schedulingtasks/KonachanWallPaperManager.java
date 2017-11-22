package scheduling_tasks.com.lakroft.schedulingtasks;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import scheduling_tasks.com.lakroft.json.KonachanDatum;

public class KonachanWallPaperManager {
	// статичный лист ссылок на обоины
	
	//http://konachan.com/post.json?tags=catgirl+tail&limit=3&page=0
	private static final String basicURLHead = "http://konachan.com/post.json?tags=";
	private String tags = "catgirl+tail";
	private static final String basicURLTail = "&limit=10&page=";
	private static int pageNum = 0;
	
	private static String[] blackList = {"penis"}; //TODO: Сделать загрузку черного списка из проперти файла
	
	private static KonachanWallPaperManager instance = null;
	
	public static KonachanWallPaperManager instance () {
		if (instance == null) {
			instance = new KonachanWallPaperManager();
		}
		return instance;
	}
	
	private ArrayDeque<String> imgURLs = new ArrayDeque<String>();
	
	public void setTags(String tags){
		if (tags == null) return;
		if (tags.isEmpty()) return;
		this.tags = tags;
	}
	
	public String getImgURL() throws IOException { // Возвращает ссылку на очередную картинку.
		if (imgURLs.size() <= 0){
			fillQueue();
		}
		return imgURLs.poll();
	}
	
	public void fillQueue() throws IOException { // Заполняет очередь ссылок на картинки
		do {
			String url = basicURLHead + tags + basicURLTail + pageNum;
			String jsonAnser = HttpJsonGetter.GetJson(url);
			
			Type itemsListType = new TypeToken<List<KonachanDatum>>() {}.getType();
			// https://habrahabr.ru/post/253266/
			
			Gson gson = new Gson();
			List<KonachanDatum> data = gson.fromJson(jsonAnser, itemsListType);
			if (data == null || data.size() < 1) { // Проверяем, что на этой странице еще есть данные
				pageNum = 0; // Данных нет, начинаем с первой страницы
				continue;
			} else { pageNum++; }
			
			for (KonachanDatum curr : data) {
				if (blackListPassed(curr.getTags())) {
					imgURLs.add(curr.getJpegUrl());
				} else System.out.println("passed " + curr.getSampleUrl());
			}
			
		} while (imgURLs.size() < 1);
	}
	
	private Boolean blackListPassed(String tags) {
		for (String blackTag : blackList){
			if (tags.contains(blackTag)) {
				return false;
			}
		}
		return true;
	}
}
