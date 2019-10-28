package club.geek66.downloader.impl.meitulu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午5:20
 * @copyright: Copyright 2019 by orange
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituluJournalDownloaderTest {

	@Autowired
	private MeituluJournalDownloader downloader;

	@Test
	public void downloadJournalByPaging() {

		downloader.downloadJournalByPaging("https://www.meitulu.com/t/xujiale-cake/");
		downloader.downloadJournalByPaging("https://www.meitulu.com/t/sugar-xiaotianxincc/");

	}
}