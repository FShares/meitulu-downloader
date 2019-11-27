package club.geek66.downloader.meitulu.client;

import club.geek66.downloader.meitulu.client.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.client.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.client.feign.MeituluImageClient;
import club.geek66.downloader.meitulu.client.feign.MeituluPageClient;
import club.geek66.downloader.meitulu.client.reader.MeituluPageReader;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午4:14
 * @copyright: Copyright 2019 by orange
 */
@Component
@RequiredArgsConstructor
public class MeituluClient {

	private final MeituluPageClient pageClient;

	private final MeituluImageClient imageClient;

	private final MeituluPageReader reader;

	public JournalPageInfoDto getJournalPage(Integer journalIndex) {
		Document journalPage = pageClient.getJournalPage(journalIndex);
		return reader.readJournalPage(journalPage);
	}

	public JournalCombinationPageInfoDto getCombinationPage(String combinationIndex) {
		Document combinationPage = pageClient.getCombinationPage(combinationIndex);
		JournalCombinationPageInfoDto combinationPageInfo = reader.readCombinationPage(combinationPage);
		fillCombinationPageJournalsInfo(combinationPageInfo);
		return combinationPageInfo;
	}

	private void fillCombinationPageJournalsInfo(JournalCombinationPageInfoDto combinationPageInfo) {
		Integer journalCount = combinationPageInfo.getJournalCount();

		int totalPage = (int) Math.ceil((double) journalCount / 60);
		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			List<JournalPageInfoDto> journalPageInfos = getCombinationPageJournals(combinationPageInfo.getIndex(), pageNo);
			combinationPageInfo.getJournalPages().put(pageNo, journalPageInfos);
		}
	}

	public List<JournalPageInfoDto> getCombinationPageJournals(String combinationIndex, Integer pageNo) {
		Document combinationPage;
		if (pageNo != 1) {
			combinationPage = pageClient.getCombinationPage(combinationIndex, pageNo);
		} else {
			combinationPage = pageClient.getCombinationPage(combinationIndex);
		}
		return reader.readCombinationPageJournalsInfo(combinationPage);
	}

	public Long getLengthOfModelImage(Integer journalIndex, Integer imageIndex) {
		ResponseEntity<Object> modelImageInfo = imageClient.getModelImageInfo(journalIndex, imageIndex);
		return modelImageInfo.getHeaders().getContentLength();
	}

	public Resource getModelImage(Integer journalIndex, Integer imageIndex) {
		return imageClient.getModelImage(journalIndex, imageIndex).getBody();
	}

}
