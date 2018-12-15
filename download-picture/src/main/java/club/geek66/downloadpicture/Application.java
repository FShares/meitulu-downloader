package club.geek66.downloadpicture;

import java.util.List;

import club.geek66.downloadpicture.domain.Periodical;
import club.geek66.downloadpicture.service.PeriodicalService;
import club.geek66.downloadpicture.service.impl.PeriodicalServiceImpl;
import club.geek66.downloadpicture.util.PictureDownloader;

public class Application {

	static final PeriodicalService periodicalService = new PeriodicalServiceImpl();

	public static void main(String[] args) {
		
		Long startMillionSecond = System.currentTimeMillis();
		
		/*List<Periodical> list = periodicalService.listPeriodicalByIndexHtml();*/
		
		/*List<Periodical> list = periodicalService.listPeriodicalBySearch("%E5%A8%87%E8%89%B3%E5%B0%8F%E8%90%9D%E8%8E%89%E5%BE%90cake");*/
		
		List<Periodical> list = periodicalService.listPeriodicalByUrl("https://www.meitulu.com/t/arvil/");
		
		list.parallelStream().forEach(periodical -> {
			System.out.println(periodical.getUrl());
			PictureDownloader.downloadPicture(periodical);
		});
		System.out.println("花费了 " + (System.currentTimeMillis() - startMillionSecond) + "毫秒");
	}

}
