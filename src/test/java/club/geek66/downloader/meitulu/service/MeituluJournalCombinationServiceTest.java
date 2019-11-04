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

	@Test
	public void downloadJournalCombination() {
		service.downloadJournalCombination("1148");
	}
}