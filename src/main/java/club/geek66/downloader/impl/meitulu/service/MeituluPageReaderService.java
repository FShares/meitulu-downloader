package club.geek66.downloader.impl.meitulu.service;

import club.geek66.downloader.common.domain.Journal;
import club.geek66.downloader.common.domain.JournalCombination;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 23:30
 * @copyright: Copyright 2019 by 橙子
 */
@Service
public class MeituluPageReaderService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

	public Journal readJournalPage(Document document) {
		Element journalInfo = document.select(".c_l").first();

		Journal journal = new Journal();
		for (Element child : journalInfo.children()) {
			String text = StringUtils.deleteWhitespace(child.text());
			if (StringUtils.isEmpty(text)) {
				continue;
			}

			if (text.contains("图片数量")) {
				int imageCount = Integer.valueOf(StringUtils.substringBetween(text, "：", "张"));
				journal.setImageCount(imageCount);
			}

			if (text.contains("发行时间")) {
				try {
					Date publishDate = DATE_FORMAT.parse(StringUtils.substringAfter(text, "："));
					journal.setPublishTime(publishDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return journal;
	}

	public JournalCombination readCombinationPage(Document document) {
		int count = 0;
		Element countEle = document.select("#pages>.a1").first();
		if (countEle != null && countEle.hasText()) {
			count = Integer.parseInt(StringUtils.substringBefore(countEle.text(), "条"));
		} else {
			Element ulEle = document.select(".img").first();
			count = ulEle.children().size();
		}
		return null;
	}

}
