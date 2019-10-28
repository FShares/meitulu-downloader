package club.geek66.downloader.test.meitulu;

import club.geek66.downloader.base.JournalAnalyser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 上午11:09
 * @copyright: Copyright 2019 by orange
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeiTuJournalAnalyserTest {

	@Autowired
	private JournalAnalyser analyser;

	@Test
	public void analysedJournalsByPaging() {


	}

	@Test
	public void analysedJournal() {

		analyser.analysedJournalPageUrls("https://www.meitulu.com/t/sugar-xiaotianxincc/");
		// analyser.analysedJournalPageUrls("https://www.meitulu.com/t/changtui/");


	}
}