package club.geek66.downloader.meitulu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 橙子
 * @date: 2019/11/1
 * @time: 0:02
 * @copyright: Copyright 2019 by 橙子
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituluJournalCombinationServiceTest {

	@Autowired
	private MeituluJournalCombinationService service;


	public void ligui(){service.downloadJournalCombination("ligui");}

	public void iess(){ service.downloadJournalCombination("iess");}

	public void siwayouhuo(){service.downloadJournalCombination("siwayouhuo");}
	// download-combination: 下载套图集合


	@Test
	public void downloadJournalCombination() {

//		service.downloadJournalCombination("qipao"); // 旗袍
		service.downloadJournalCombination("1083"); // 丝袜美女
		service.downloadJournalCombination("1461"); //丽丝映像GIRLISS
//		service.downloadJournalCombination("1084"); // 白丝
//		service.downloadJournalCombination("1085"); // 灰丝
//		service.downloadJournalCombination("1086"); // 红丝
//		service.downloadJournalCombination("1089"); // 丝袜控
		// service.downloadJournalCombination("1165");
		// service.downloadJournalCombination("sabrina");
	}
}