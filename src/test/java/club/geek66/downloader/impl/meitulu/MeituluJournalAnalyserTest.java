package club.geek66.downloader.impl.meitulu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午3:05
 * @copyright: Copyright 2019 by orange
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituluJournalAnalyserTest {

	@Autowired
	private MeituluJournalAnalyser journalAnalyser;

	@Test
	public void analysedJournalsByPaging() {
	}

	@Test
	public void analysedJournalPageUrls() {
	}

	@Test
	public void analysedJournal() {

		journalAnalyser.analysedJournal("https://www.meitulu.com/item/16354.html");


	}
}