package club.geek66.downloader.meitulu.client;

import club.geek66.downloader.meitulu.client.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.client.dto.JournalPageInfoDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;
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
@EnableAutoConfiguration(exclude = {JLineShellAutoConfiguration.class, StandardCommandsAutoConfiguration.class})
public class MeituluClientTest {

	@Autowired
	private MeituluClient client;

	@Test
	public void getJournalPage() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

		JournalPageInfoDto pageInfo = client.getJournalPage(19028);

		Assert.assertEquals(19028, (long) pageInfo.getIndex());

		Assert.assertEquals("[XIUREN秀人] No.1561 周于希Sandy&Emily顾奈奈酱 - 上演剧情私房系列", pageInfo.getTitle());

		Assert.assertEquals("秀人网", pageInfo.getMechanismName());
		Assert.assertEquals("xiuren", pageInfo.getMechanismId());

		Assert.assertEquals("No.1561", pageInfo.getNumber());
		Assert.assertEquals(55, (long) pageInfo.getImageCount());
		Assert.assertEquals("1333X2000", pageInfo.getResolution());

		Assert.assertEquals("周于希", pageInfo.getModelName());
		Assert.assertEquals("1096", pageInfo.getModelId());

		Assert.assertEquals("2019.07.19", dateFormat.format(pageInfo.getPublishDate()));
		Assert.assertEquals(" 塞班岛旅拍模特合集写真发布，由模特@周于希Sandy与Emily顾奈奈酱上演剧情私房系列，情节丰富内容诱人", pageInfo.getAdditional());
	}

	@Test
	public void getCombinationPage() {
		JournalCombinationPageInfoDto pageInfo = client.getCombinationPage("1225");

		Assert.assertEquals("1225", pageInfo.getIndex());
		Assert.assertEquals("李可可_李可儿写真图片_高清秀人李可可写真套图", pageInfo.getTitle());
		Assert.assertEquals("李可可资料\n李可可，别名：李可儿。秀人模特，身高163公分，来自深圳。", pageInfo.getDescription());
		Assert.assertEquals(30, (long) pageInfo.getJournalCount());
	}

	@Test
	public void getCombinationPageJournals() {
		List<JournalPageInfoDto> journalPageInfos = client.getCombinationPageJournals("1214", 1);

		Assert.assertEquals(60, (long) journalPageInfos.size());

		JournalPageInfoDto first = journalPageInfos.get(0);
		Assert.assertNotNull(first.getIndex());
		Assert.assertNotNull(first.getTitle());
		Assert.assertNotNull(first.getModelId());
		Assert.assertNotNull(first.getModelName());
		Assert.assertNotNull(first.getMechanismId());
		Assert.assertNotNull(first.getMechanismName());
	}

	@Test
	public void getLengthOfImage() {
		Long lengthOfModelImage = client.getLengthOfModelImage(18959, 1);
		Assert.assertEquals(349466, (long) lengthOfModelImage);
	}

	@Test
	public void getModelImage() {
		Resource modelImage = client.getModelImage(18959, 3);
		Assert.assertNotNull(modelImage);
	}

}