package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.rpc.MeituluPageClient;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 橙子
 * @date: 2019/11/1
 * @time: 22:01
 * @copyright: Copyright 2019 by 橙子
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituluPageReaderServiceTest {

	@Autowired
	private MeituluPageReaderService reader;

	@Autowired
	private MeituluPageClient pageClient;

	@Test
	public void testReadJournalPage() {
		Document imagePage = pageClient.getJournalPage(18993);
		JournalPageInfoDto pageInfo = reader.readJournalPage(imagePage);
		Assert.assertEquals(pageInfo.getModelName(), "筱慧");
	}

	@Test
	public void testReadCombinationPage() {

	}

}