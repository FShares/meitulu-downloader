package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.client.MeituluClient;
import club.geek66.downloader.meitulu.client.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.client.dto.JournalPageInfoDto;
import lombok.RequiredArgsConstructor;
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

	private final MeituluClient client;

	private final MeituluJournalService journalService;

	public void downloadJournalCombination(String combinationIndex) {

		// 读取信息
		JournalCombinationPageInfoDto combinationPage = client.getCombinationPage(combinationIndex);

		Integer journalCount = combinationPage.getJournalCount();

		int totalPage = (int) Math.ceil((double) journalCount / 60);

		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			List<JournalPageInfoDto> journalPageInfos = client.getCombinationPageJournals(combinationIndex, pageNo);
			combinationPage.getJournalPages().put(pageNo, journalPageInfos);
		}
		saveJournalCombination(combinationPage);
	}

	private void saveJournalCombination(JournalCombinationPageInfoDto combinationInfo) {
		Map<Integer, List<JournalPageInfoDto>> journalPages = combinationInfo.getJournalPages();

		journalPages.values().parallelStream().forEach(journals ->
				journals.forEach(journalPage ->
						journalService.downloadJournal(journalPage.getIndex())
				));
	}

}
