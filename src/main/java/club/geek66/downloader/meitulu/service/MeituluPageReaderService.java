package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	public JournalPageInfoDto readJournalPage(Document journalPage) {
		JournalPageInfoDto pageInfo = new JournalPageInfoDto();

		Optional.of(journalPage.select("head>link[rel=\"alternate\"]"))
				.map(Elements::first)
				.map(ele -> ele.attr("href"))
				.map(this::subJournalId)
				.ifPresent(pageInfo::setId);

		Optional.ofNullable(journalPage.select(".weizhi>h1"))
				.map(Elements::first)
				.map(Element::text)
				.ifPresent(pageInfo::setTitle);

		Element journalInfo = journalPage.select(".c_l").first();
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
				int imageCount = subImageCount(text);
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

		Optional.ofNullable(journalPage.select(".buchongshuoming"))
				.map(Elements::first)
				.map(Element::text)
				.map(text -> StringUtils.substringAfter(text, "："))
				.ifPresent(pageInfo::setAdditional);

		return pageInfo;
	}

	public JournalCombinationPageInfoDto readCombinationPage(Document combinationPage) {
		JournalCombinationPageInfoDto pageInfoDto = new JournalCombinationPageInfoDto();

		Optional.ofNullable(combinationPage.select("#pages>.a1"))
				.filter(Elements::hasText)
				.map(Elements::text)
				.map(text -> StringUtils.substringBefore(text, "条"))
				.map(Integer::valueOf)
				.or(() ->
						Optional.ofNullable(combinationPage.select(".img"))
								.map(Elements::first)
								.map(Element::children)
								.map(Elements::size)
				).ifPresent(pageInfoDto::setJournalCount);


		// TODO read more info such as description ...
		return pageInfoDto;
	}

	public List<JournalPageInfoDto> readJournalsInfo(Document combinationPage) {
		List<JournalPageInfoDto> pageInfos = new ArrayList<>();

		Elements journalElements = combinationPage.select(".img>li");
		for (Element element : journalElements) {
			JournalPageInfoDto pageInfo = new JournalPageInfoDto();

			for (Element child : element.children()) {
				String text = StringUtils.deleteWhitespace(element.text());
				if (child.is("a")) {
					Optional.of(child)
							.map(ele -> ele.attr("href"))
							.map(this::subJournalId)
							.ifPresent(pageInfo::setId);
				} else if (text.contains("图片")) {
					Optional.of(text)
							.map(this::subImageCount)
							.ifPresent(pageInfo::setImageCount);
				}
				// TODO sub read info such as model name...
			}
			pageInfos.add(pageInfo);
		}

		return pageInfos;
	}

	private Integer subJournalId(String journalPageUrl) {
		String journalId = StringUtils.substringBetween(journalPageUrl, "/item/", ".html");
		return Integer.valueOf(journalId);
	}

	private Integer subImageCount(String imageText) {
		String imageCount = StringUtils.substringBetween(imageText, "：", "张");
		return Integer.valueOf(imageCount);
	}

}
