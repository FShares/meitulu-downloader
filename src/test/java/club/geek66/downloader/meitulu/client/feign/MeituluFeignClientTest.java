package club.geek66.downloader.meitulu.client.feign;

import club.geek66.downloader.meitulu.client.MeituClassification;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author: 橙子
 * @date: 2019/10/29
 * @time: 20:26
 * @copyright: Copyright 2019 by 橙子
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {JLineShellAutoConfiguration.class, StandardCommandsAutoConfiguration.class})
public class MeituluFeignClientTest {

	@Autowired
	private MeituluImageClient imageClient;

	@Autowired
	private MeituluPageClient pageClient;

	@Test
	public void testGetImage() {
		ResponseEntity<Resource> modelImage = imageClient.getModelImage(19484, 1);
		Assert.assertNotNull(modelImage);
	}

	@Test
	public void testCompareImage() throws IOException {
		Long realLength = imageClient.getModelImage(19484, 1).getBody().contentLength();
		Long expectLength = imageClient.getModelImageInfo(19484, 1).getHeaders().getContentLength();
		Assert.assertEquals(expectLength, realLength);
	}

	@Test
	public void testHeadImage() {
		ResponseEntity<Object> modelImageInfo = imageClient.getModelImageInfo(14195, 16);
		System.out.println(modelImageInfo);
	}

	@Test
	public void testGetJournalPage() {
		Document journalPage = pageClient.getJournalPage(19482);
		Assert.assertNotNull(journalPage);
	}

	@Test
	public void testGetJournalPage2() {
		Document journalPage = pageClient.getJournalPage(16443, 2);
		Assert.assertNotNull(journalPage);
	}

	@Test
	public void testGetClassificationPage() {
		Document classificationPage = pageClient.getCombinationPage(MeituClassification.BAO_RU.getId());
		Assert.assertNotNull(classificationPage);
	}

	@Test
	public void testGetModelPage() {
		// TODO handle 504 gateway timeout
		Document modelPage = pageClient.getCombinationPage("1148");
		Assert.assertNotNull(modelPage);
	}

	@Test
	public void testGetMechanismPage() {
		Document mechanismPage = pageClient.getCombinationPage("1371");
		Assert.assertNotNull(mechanismPage);
	}

	@Test
	public void testGetSearchPage() {
		// TODO handle 504 gateway timeout
		Document searchPage = pageClient.getSearchPage("筱慧");
		Assert.assertNotNull(searchPage);
	}

	@Test
	public void testGetCombinationPage() {
		Document combinationPage = pageClient.getCombinationPage("sugar-xiaotianxincc", 2);
		Assert.assertNotNull(combinationPage);
	}

}