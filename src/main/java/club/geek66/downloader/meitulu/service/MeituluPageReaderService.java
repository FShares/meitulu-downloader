package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.common.domain.JournalCombination;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 23:30
 * @copyright: Copyright 2019 by 橙子
 */
@Slf4j
@Service
public class MeituluPageReaderService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

	public JournalPageInfoDto readJournalPage(Document document) {
		JournalPageInfoDto pageInfo = new JournalPageInfoDto();

		Optional.ofNullable(document.select(".weizhi>h1"))
				.map(Elements::first)
				.map(Element::text)
				.ifPresent(pageInfo::setTitle);

		Element journalInfo = document.select(".c_l").first();
		for (Element child : journalInfo.children()) {
			String text = StringUtils.deleteWhitespace(child.text());
			if (StringUtils.isEmpty(text)) {
				continue;
			}

			if (text.contains("发行机构")) {
				Optional.ofNullable(child.children())
						.map(Elements::first)
						.map(Element::text)
						.ifPresent(pageInfo::setModelName);
			} else if (text.contains("期刊编号")) {
				pageInfo.setJournalNumber(StringUtils.substringAfter(text, "："));
			} else if (text.contains("图片数量")) {
				int imageCount = Integer.valueOf(StringUtils.substringBetween(text, "：", "张"));
				pageInfo.setImageCount(imageCount);
			} else if (text.contains("分辨率")) {
				pageInfo.setResolution(StringUtils.substringAfter(text, "："));
			} else if (text.contains("模特姓名")) {
				String modelName = child.children().first().text();
				pageInfo.setModelName(modelName);
			} else if (text.contains("发行时间")) {
				try {
					Date publishDate = DATE_FORMAT.parse(StringUtils.substringAfter(text, "："));
					pageInfo.setPublishTime(publishDate);
				} catch (ParseException e) {
					log.warn("Parse journal publish time fail from str {}", text);
				}
			}
		}

		Optional.ofNullable(document.select(".buchongshuoming"))
				.map(Elements::first)
				.map(Element::text)
				.map(text -> StringUtils.substringAfter(text, "："))
				.ifPresent(pageInfo::setAdditional);

		return pageInfo;
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
