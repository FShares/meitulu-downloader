package club.geek66.downloader.impl.meitulu;

import club.geek66.downloader.base.JournalAnalyser;
import club.geek66.downloader.entity.Journal;
import club.geek66.downloader.impl.meitulu.entity.MeituluJournalImage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static club.geek66.downloader.entity.ImageExtensionName.JPG;

/**
 * @author: orange
 * @date: 19-2-1
 * @time: 下午10:16
 * @copyright: Copyright 2019 by orange
 */
@Component
@RequiredArgsConstructor
public class MeituluJournalAnalyser implements JournalAnalyser {

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

	private final RestTemplate restTemplate;


	private final Integer pageSize = 4; // configurable item;

	// 分页分析
	public List<Journal> analysedJournalsByPaging(String originalUrl) {

		List<Journal> journals = new LinkedList<>();

		List<String> urls = analysedJournalPageUrls(originalUrl);

		urls.parallelStream().forEach(url -> {
			Journal journal = analysedJournal(url);
			journals.add(journal);
		});


		// 根据分页下载图片
		return journals;
	}

	@Override
	@SneakyThrows
	public List<String> analysedJournalPageUrls(String journalPageUrl) {

		List<String> urls = new LinkedList<>();

		Elements aElements = getDocumentByUrl(journalPageUrl).select(".img>li>a");

		aElements.stream().map(image -> image.attr("href")).forEach(urls::add);

		return urls;

	}

	@Override
	public Journal analysedJournal(String journalUrl) {

		Document document = getDocumentByUrl(journalUrl);
		Element infoElement = document.selectFirst(".c_l");
		Element publisherElement = infoElement.selectFirst("p:eq(0)>a");

		String titleText = document.select(".weizhi>h1").text();

		// String publisherHref = publisherElement.attr("href");
		// String publisherName = publisherElement.text();
		// String numberText = infoElement.selectFirst("p:eq(1)").text();
		String imageSizeText = infoElement.selectFirst("p:eq(2)").text();
		// 姓名
		String publishTimeText = infoElement.selectFirst("p:eq(5)").text();

		Integer number = parseJournalNumber(journalUrl);
		Integer imageSize = Integer.valueOf(StringUtils.substringBetween(imageSizeText, " ", " "));

		Journal journal = new Journal(titleText, null, number, null);
		analysedJournalImages(journal, imageSize);

		return journal;
	}

	private Integer parseJournalNumber(String journalUrl) {
		return Integer.valueOf(StringUtils.substringBetween(journalUrl, "item/", "."));
	}

	@SneakyThrows
	private Date parseDate(String publishDateText) {

		String substring = publishDateText.substring(publishDateText.indexOf(" ") + 1);

		return simpleDateFormat.parse(substring);

	}

	@SneakyThrows
	private Document getDocumentByUrl(String url) {

		Document document = Jsoup.connect(url).get();

		return document;

	}

	private void analysedJournalImages(Journal journal, Integer imageSize) {

		for (int i = 0; i < imageSize; i++) {

			MeituluJournalImage journalImage = new MeituluJournalImage(String.valueOf(i + 1), JPG, journal);
			journal.getImages().add(journalImage);

		}

	}

}
