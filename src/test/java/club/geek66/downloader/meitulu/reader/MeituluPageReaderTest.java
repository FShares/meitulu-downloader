package club.geek66.downloader.meitulu.reader;

import club.geek66.downloader.meitulu.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.rpc.MeituluPageClient;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: 橙子
 * @date: 2019/11/1
 * @time: 22:01
 * @copyright: Copyright 2019 by 橙子
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeituluPageReaderTest {

	@Autowired
	private MeituluPageReader reader;

	@Autowired
	private MeituluPageClient pageClient;

	@Test
	public void testReadJournalPage() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

		Document imagePage = pageClient.getJournalPage(19028);
		JournalPageInfoDto pageInfo = reader.readJournalPage(imagePage);

		Assert.assertEquals((long) pageInfo.getIndex(), 19028L);

		Assert.assertEquals(pageInfo.getTitle(), "[XIUREN秀人] No.1561 周于希Sandy&Emily顾奈奈酱 - 上演剧情私房系列");

		Assert.assertEquals(pageInfo.getMechanismName(), "秀人网");
		Assert.assertEquals(pageInfo.getMechanismId(), "xiuren");

		Assert.assertEquals(pageInfo.getNumber(), "No.1561");
		Assert.assertEquals((long) pageInfo.getImageCount(), 55L);
		Assert.assertEquals(pageInfo.getResolution(), "1333X2000");

		Assert.assertEquals(pageInfo.getModelName(), "周于希");
		Assert.assertEquals(pageInfo.getModelId(), "1096");

		Assert.assertEquals(dateFormat.format(pageInfo.getPublishDate()), "2019.07.19");
		Assert.assertEquals(pageInfo.getAdditional(), " 塞班岛旅拍模特合集写真发布，由模特@周于希Sandy与Emily顾奈奈酱上演剧情私房系列，情节丰富内容诱人");
	}

	@Test
	public void testReadCombinationPage() {
		Document combinationPage = pageClient.getCombinationPage("1225");
		JournalCombinationPageInfoDto pageInfo = reader.readCombinationPage(combinationPage);

		Assert.assertEquals(pageInfo.getIndex(), "1225");
		Assert.assertEquals(pageInfo.getTitle(), "李可可_李可儿写真图片_高清秀人李可可写真套图");
		Assert.assertEquals(pageInfo.getDescription(), "李可可资料\n李可可，别名：李可儿。秀人模特，身高163公分，来自深圳。");
		Assert.assertEquals((long) pageInfo.getJournalCount(), 30);
	}

	@Test
	public void testReadCombinationPageJournalsInfo() {
		Document combinationPage = pageClient.getCombinationPage("1214");

		List<JournalPageInfoDto> journalPageInfos = reader.readCombinationPageJournalsInfo(combinationPage);
		Assert.assertEquals((long) journalPageInfos.size(), 60L);

		JournalPageInfoDto first = journalPageInfos.get(0);
		Assert.assertTrue(first.getIndex() != null);
		Assert.assertTrue(first.getTitle() != null);

		Assert.assertTrue(first.getMechanismName() != null);
		Assert.assertTrue(first.getMechanismId() != null);

		Assert.assertTrue(first.getModelName() != null);
		Assert.assertTrue(first.getModelId() != null);

	}

}