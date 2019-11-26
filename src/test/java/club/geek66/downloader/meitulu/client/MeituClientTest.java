package club.geek66.downloader.meitulu.client;

import club.geek66.downloader.meitulu.client.MeituluPageClient.MeituClassification;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
public class MeituClientTest {

	@Autowired
	private MeituluPageClient pageClient;

	@Autowired
	private MeituluImageClient imageClient;

	@Test
	public void testGetImage() {
		ResponseEntity<Resource> modelImage = imageClient.getModelImage(19484, 1);
		Assert.assertNotNull(modelImage);
	}

	@Test
	public void testCompareImage() throws IOException {
		Long realLength = imageClient.getModelImage(19484, 1).getBody().contentLength();
		Long expectLength = imageClient.getModelImageInfo(19484, 1).getHeaders().getContentLength();
		Assert.assertEquals(realLength, expectLength);
	}

	@Test
	public void testHeadImage() {
		ResponseEntity<Object> modelImageInfo = imageClient.getModelImageInfo(14195, 16);
		System.out.println(modelImageInfo);
	}

	@Test
	public void testGetJournalPage() {
		Document imagePage = pageClient.getJournalPage(19482);
		Assert.assertNotNull(imagePage);
	}

	@Test
	public void testGetJournalPage2() {
		Document imagePage = pageClient.getJournalPage(16443, 2);
		Assert.assertNotNull(imagePage);
	}

	@Test
	public void testGetClassificationPage() {
		Document classificationPage = pageClient.getCombinationPage(MeituClassification.BAO_RU.getId());
		Assert.assertNotNull(classificationPage);
	}

	@Test
	public void testGetModelPage() {
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
		Document searchPage = pageClient.getSearchPage("筱慧");
		Assert.assertNotNull(searchPage);
	}

	@Test
	public void testGetCombinationPage() {
		Document combinationPage = pageClient.getCombinationPage("sugar-xiaotianxincc", 2);
		Assert.assertNotNull(combinationPage);
	}

}