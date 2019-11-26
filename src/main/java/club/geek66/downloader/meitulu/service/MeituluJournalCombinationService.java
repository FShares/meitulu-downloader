package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.client.MeituluPageClient;
import club.geek66.downloader.meitulu.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.reader.MeituluPageReader;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 23:29
 * @copyright: Copyright 2019 by 橙子
 */
@Service
@RequiredArgsConstructor
public class MeituluJournalCombinationService {

	private final MeituluPageClient pageClient;

	private final MeituluPageReader reader;

	private final MeituluJournalService journalService;

	public void downloadJournalCombination(String combinationIndex) {

		// 读取信息
		JournalCombinationPageInfoDto combinationInfo = getJournalCombinationInfo(combinationIndex);

		Integer journalCount = combinationInfo.getJournalCount();

		int totalPage = (int) Math.ceil((double) journalCount / 60);

		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			Document combinationPage = pageNo != 1 ? pageClient.getCombinationPage(combinationIndex, pageNo) : pageClient.getCombinationPage(combinationIndex);
			List<JournalPageInfoDto> journalPageInfos = reader.readCombinationPageJournalsInfo(combinationPage);
			combinationInfo.getJournalPages().put(pageNo, journalPageInfos);
		}
		saveJournalCombination(combinationInfo);
	}

	private void saveJournalCombination(JournalCombinationPageInfoDto combinationInfo) {
		Map<Integer, List<JournalPageInfoDto>> journalPages = combinationInfo.getJournalPages();

		journalPages.values().parallelStream().forEach(journals ->
				journals.forEach(journalPage ->
						journalService.downloadJournal(journalPage.getIndex())
				));
	}

	private JournalCombinationPageInfoDto getJournalCombinationInfo(String combinationIndex) {
		return reader.readCombinationPage(combinationIndex);
	}

}
