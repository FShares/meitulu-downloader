package club.geek66.downloader.meitulu.reader;

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
import java.util.Arrays;
import java.util.Collection;
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
public class MeituluPageReader {

	private static final Collection<SimpleDateFormat> SUPPORTED_DATE_FORMATS = Arrays.asList(
			new SimpleDateFormat("yyyy.MM.dd"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd")
	);

	private static final String DETAIL_PREFIX = "：";

	// 写真页面内信息
	public JournalPageInfoDto readJournalPage(Document journalPageDoc) {
		JournalPageInfoDto pageInfo = new JournalPageInfoDto();

		// 写真 id
		Optional.of(journalPageDoc.select("head>link[rel=\"alternate\"]"))
				.map(Elements::first)
				.map(ele -> ele.attr("href"))
				.map(this::subJournalId)
				.ifPresent(pageInfo::setId);

		// 写真标题
		// 示例: [XIUREN秀人] No.1526 模特@筱慧cindy塞班旅拍写真
		Optional.ofNullable(journalPageDoc.select(".weizhi>h1"))
				.map(Elements::first)
				.map(Element::text)
				.ifPresent(pageInfo::setTitle);

		// 写真信息
		// 示例:
		// 发行机构:		秀人网
		// 期刊编号：	No.1526
		// 图片数量：	47 张
		// 分 辨 率：	1333X2000
		// 模特姓名：	筱慧
		// 发行时间： 	2019.07.01
		Optional.ofNullable(journalPageDoc.select(".c_l"))
				.map(Elements::first)
				.map(Element::children)
				.ifPresent(journalInfos -> this.readJournalInfo(journalInfos, pageInfo));


		// 补充说明
		// 示例: 模特@筱慧cindy塞班旅拍写真发布，极致魅惑的黑丝与皇帝的新装精彩私房诱惑，写真包含两套服饰
		Optional.ofNullable(journalPageDoc.select(".buchongshuoming"))
				.map(Elements::first)
				.map(Element::text)
				.map(text -> StringUtils.substringAfter(text, DETAIL_PREFIX))
				.ifPresent(pageInfo::setAdditional);
		return pageInfo;
	}

	private void readJournalInfo(Elements journalInfos, JournalPageInfoDto pageInfo) {
		for (Element item : journalInfos) {
			String text = StringUtils.deleteWhitespace(item.text());
			if (StringUtils.isEmpty(text)) {
				continue;
			}

			if (text.contains("发行机构")) {
				// 机构名 & id
				this.readMechanismIdAndName(pageInfo, item);
			} else if (text.contains("期刊编号")) {
				pageInfo.setJournalNumber(StringUtils.substringAfter(text, DETAIL_PREFIX));
			} else if (text.contains("图片数量")) {
				int imageCount = subImageCount(text);
				pageInfo.setImageCount(imageCount);
			} else if (text.contains("分辨率")) {
				pageInfo.setResolution(StringUtils.substringAfter(text, DETAIL_PREFIX));
			} else if (text.contains("模特姓名")) {
				// 模特姓名 & id
				this.readModelIdAndName(pageInfo, item);
			} else if (text.contains("发行时间")) {
				for (SimpleDateFormat dateFormat : SUPPORTED_DATE_FORMATS) {
					if (pageInfo.getPublishDate() != null) {
						break;
					}
					try {
						pageInfo.setPublishDate(dateFormat.parse(StringUtils.substringAfter(text, DETAIL_PREFIX)));
					} catch (ParseException ignored) {

					}
				}
				if (pageInfo.getPublishDate() == null) {
					log.warn("Parse journal publish time fail from str {} with journal id {}", text, pageInfo.getId());
				}
			}
		}
	}

	// 写真集合页面
	public JournalCombinationPageInfoDto readCombinationPage(Document combinationPage) {
		JournalCombinationPageInfoDto pageInfo = new JournalCombinationPageInfoDto();

		// title and description
		Optional.ofNullable(combinationPage.select(".listtags_r"))
				.map(Elements::first)
				.map(Element::children)
				.ifPresent(elements -> {
					StringBuilder description = new StringBuilder();
					for (int i = 0; i < elements.size(); i++) {
						String text = elements.get(i).text();
						if (i != 0) {
							description.append(text);
							if (i < elements.size() - 1) {
								description.append('\n');
							}
						} else {
							pageInfo.setTitle(text);
						}
					}
					pageInfo.setDescription(description.toString());
				});

		// 图片数量
		Optional.ofNullable(combinationPage.select("#pages>.a1"))
				.filter(Elements::hasText)
				.map(Elements::text)
				.map(text -> StringUtils.substringBefore(text, "条"))
				.map(Integer::valueOf)
				.or(() ->
						Optional.ofNullable(combinationPage.select(".img"))
								.map(Elements::first)
								.map(Element::children)
								.map(Elements::size) // 只有一页
				).ifPresent(pageInfo::setJournalCount);
		return pageInfo;
	}

	// 写真集合页面中的写真信息集合
	public List<JournalPageInfoDto> readCombinationPageJournalsInfo(Document combinationPage) {
		List<JournalPageInfoDto> pageInfos = new ArrayList<>();

		Optional.of(combinationPage.select(".img>li"))
				.ifPresent(journalElements -> {
					for (Element element : journalElements) {
						JournalPageInfoDto pageInfo = new JournalPageInfoDto();

						for (Element journalInfo : element.children()) {
							String text = StringUtils.deleteWhitespace(journalInfo.text());
							if (text.contains("图片")) {
								Optional.of(text)
										.map(this::subImageCount)
										.ifPresent(pageInfo::setImageCount);
							} else if (text.contains("机构")) {
								this.readMechanismIdAndName(pageInfo, journalInfo);
							} else if (text.contains("模特")) {
								this.readModelIdAndName(pageInfo, journalInfo);
							} else if (text.contains("标签")) {
								// TODO tag support
							} else if (journalInfo.hasClass("p_title")) {
								Optional.ofNullable(journalInfo.children())
										.map(Elements::first)
										.ifPresent(aTag -> {
											pageInfo.setId(this.subJournalId(aTag.attr("href")));
											pageInfo.setTitle(aTag.text());
										});
							}
							// TODO sub read info such as model name...
						}
						pageInfos.add(pageInfo);
					}
				});

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

	private String subCombinationId(String combinationPageUrl) {
		return StringUtils.substringBetween(combinationPageUrl, "/t/", "/");
	}

	private void readModelIdAndName(JournalPageInfoDto pageInfo, Element element) {
		Optional.ofNullable(element.children())
				.map(Elements::first)
				.ifPresent(aTag -> {
					pageInfo.setModelName(aTag.text());
					pageInfo.setModelId(this.subCombinationId(aTag.attr("href")));
				});
		// TODO multi model support
	}

	private void readMechanismIdAndName(JournalPageInfoDto pageInfo, Element element) {
		Optional.ofNullable(element.children())
				.map(Elements::first)
				.ifPresent(aTag -> {
					pageInfo.setMechanismName(aTag.text());
					pageInfo.setMechanismId(this.subCombinationId(aTag.attr("href")));
				});
	}

}
