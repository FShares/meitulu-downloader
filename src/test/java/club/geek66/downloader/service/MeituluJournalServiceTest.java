package club.geek66.downloader.service;

import club.geek66.downloader.impl.meitulu.service.MeituluJournalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 22:33
 * @copyright: Copyright 2019 by 橙子
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituluJournalServiceTest {

	@Autowired
	private MeituluJournalService service;

	@Test
	public void testDownloadJournal() {
		service.downloadJournal(18993);
	}
}