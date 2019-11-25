package club.geek66.downloader.meitulu.reader;

import club.geek66.downloader.meitulu.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.rpc.MeituluClient;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MeituluPageReader {

	private final MeituluClient client;

	// 日期解析器
	private static final Collection<SimpleDateFormat> SUPPORTED_DATE_FORMATS = Arrays.asList(
			new SimpleDateFormat("yyyy.MM.dd"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd")
	);

	private static final String DETAIL_PREFIX = "：";

	private static final String ATTR_HREF = "href";

	/**
	 * 读取写真详情页面内信息
	 *
	 * @param journalIndex 索引
	 * @return 写真页面的信息
	 */
	public JournalPageInfoDto readJournalPage(Integer journalIndex) {
		Document journalPageDoc = client.getJournalPage(journalIndex);

		JournalPageInfoDto pageInfo = new JournalPageInfoDto();

		// 写真 index
		Optional.of(journalPageDoc.select("head>link[rel=\"alternate\"]"))
				.map(Elements::first)
				.map(ele -> ele.attr(ATTR_HREF))
				.map(this::subJournalIndex)
				.ifPresent(pageInfo::setIndex);

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

	/**
	 * 读取写真详情页面信息
	 *
	 * @param journalInfos 详情元素
	 * @param pageInfo     dto
	 */
	private void readJournalInfo(Elements journalInfos, JournalPageInfoDto pageInfo) {
		for (Element item : journalInfos) {
			String text = StringUtils.deleteWhitespace(item.text());
			if (StringUtils.isEmpty(text)) {
				continue;
			}

			if (text.startsWith("发行机构")) {
				// 机构名 & index
				this.readMechanismIndexAndName(pageInfo, item);
			} else if (text.startsWith("期刊编号")) {
				pageInfo.setNumber(StringUtils.substringAfter(text, DETAIL_PREFIX));
			} else if (text.startsWith("图片数量")) {
				int imageCount = subImageCount(text);
				pageInfo.setImageCount(imageCount);
			} else if (text.startsWith("分辨率")) {
				pageInfo.setResolution(StringUtils.substringAfter(text, DETAIL_PREFIX));
			} else if (text.startsWith("模特姓名")) {
				// 模特姓名 & index
				this.readModelIndexAndName(pageInfo, item);
			} else if (text.startsWith("发行时间")) {
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
					log.warn("Parse journal publish time fail from str {} with journal index {}", text, pageInfo.getIndex());
				}
			}
		}
	}

	/**
	 * 读取写真集合页面的具体信息
	 *
	 * @param combinationIndex 写真集合页面的索引
	 * @return 详细信息
	 */
	public JournalCombinationPageInfoDto readCombinationPage(String combinationIndex) {
		Document combinationPageDoc = client.getCombinationPage(combinationIndex);

		JournalCombinationPageInfoDto pageInfo = new JournalCombinationPageInfoDto();

		Optional.of(combinationPageDoc.select("link[rel=\"alternate\"]"))
				.map(Elements::first)
				.map(ele -> ele.attr(ATTR_HREF))
				.map(this::subCombinationIndex)
				.ifPresentOrElse(pageInfo::setIndex, () -> {
					throw new ReadMeituluPageException("fail to read combination page from " + combinationPageDoc.toString());
				});

		// title and description
		Optional.ofNullable(combinationPageDoc.select(".listtags_r"))
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
		Optional.ofNullable(combinationPageDoc.select("#pages>.a1"))
				.filter(Elements::hasText)
				.map(Elements::text)
				.map(text -> StringUtils.substringBefore(text, "条"))
				.map(Integer::valueOf)
				.or(() ->
						Optional.ofNullable(combinationPageDoc.select(".img"))
								.map(Elements::first)
								.map(Element::children)
								.map(Elements::size) // 只有一页
				).ifPresent(pageInfo::setJournalCount);
		return pageInfo;
	}

	/**
	 * 写真集合页面中的写真信息集合
	 *
	 * @param combinationPageDoc 文档
	 * @return
	 */
	public List<JournalPageInfoDto> readCombinationPageJournalsInfo(Document combinationPageDoc) {
		List<JournalPageInfoDto> pageInfos = new ArrayList<>();

		Optional.of(combinationPageDoc.select(".img>li"))
				.ifPresent(journalElements -> {
					for (Element element : journalElements) {
						JournalPageInfoDto pageInfo = new JournalPageInfoDto();

						for (Element journalInfo : element.children()) {
							String text = StringUtils.deleteWhitespace(journalInfo.text());
							if (text.startsWith("图片")) {
								Optional.of(text)
										.map(this::subImageCount)
										.ifPresent(pageInfo::setImageCount);
							} else if (text.startsWith("机构")) {
								this.readMechanismIndexAndName(pageInfo, journalInfo);
							} else if (text.startsWith("模特")) {
								this.readModelIndexAndName(pageInfo, journalInfo);
							} else if (text.startsWith("标签")) {
								// TODO tag support
							} else if (journalInfo.hasClass("p_title")) {
								Optional.ofNullable(journalInfo.children())
										.map(Elements::first)
										.ifPresent(aTag -> {
											pageInfo.setIndex(this.subJournalIndex(aTag.attr(ATTR_HREF)));
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

	private Integer subJournalIndex(String journalPageUrl) {
		String journalIndex = StringUtils.substringBetween(journalPageUrl, "/item/", ".html");
		return Integer.valueOf(journalIndex);
	}

	private Integer subImageCount(String imageText) {
		String imageCount = StringUtils.substringBetween(imageText, "：", "张");
		return Integer.valueOf(imageCount);
	}

	private String subCombinationIndex(String combinationPageUrl) {
		return StringUtils.substringBetween(combinationPageUrl, "/t/", "/");
	}

	private void readModelIndexAndName(JournalPageInfoDto pageInfo, Element element) {
		Optional.ofNullable(element.children())
				.map(Elements::first)
				.ifPresent(aTag -> {
					pageInfo.setModelName(aTag.text());
					pageInfo.setModelId(this.subCombinationIndex(aTag.attr(ATTR_HREF)));
				});
		// TODO multi model support
	}

	private void readMechanismIndexAndName(JournalPageInfoDto pageInfo, Element element) {
		Optional.ofNullable(element.children())
				.map(Elements::first)
				.ifPresent(aTag -> {
					pageInfo.setMechanismName(aTag.text());
					pageInfo.setMechanismId(this.subCombinationIndex(aTag.attr(ATTR_HREF)));
				});
	}

}
