package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.common.configuration.DownloaderConfiguration;
import club.geek66.downloader.meitulu.dto.JournalCombinationPageInfoDto;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.reader.MeituluPageReader;
import club.geek66.downloader.meitulu.rpc.MeituluPageClient;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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

	private final DownloaderConfiguration configuration;

	private final MeituluJournalService journalService;

	private final ThreadPoolTaskExecutor taskExecutor;

	public void downloadJournalCombination(String combinationId) {
		JournalCombinationPageInfoDto combinationInfo = getJournalCombinationInfo(combinationId);

		Integer journalCount = combinationInfo.getJournalCount();
		Integer journalPageSize = configuration.getMeitulu().getJournalPageSize();

		int totalPage = (int) Math.ceil((double) journalCount / journalPageSize);

		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			Document combinationPage = pageNo != 1 ? pageClient.getCombinationPage(combinationId, pageNo) : pageClient.getCombinationPage(combinationId);
			List<JournalPageInfoDto> journalPageInfos = reader.readCombinationPageJournalsInfo(combinationPage);
			combinationInfo.getJournalPages().put(pageNo, journalPageInfos);
		}
		saveJournalCombination(combinationInfo);
	}

	private void saveJournalCombination(JournalCombinationPageInfoDto combinationInfo) {
		Map<Integer, List<JournalPageInfoDto>> journalPages = combinationInfo.getJournalPages();


		journalPages.values().forEach(journals ->
				journals.forEach(journalPage ->
						taskExecutor.execute(() ->
								journalService.downloadJournal(journalPage.getId())
						)
				));
	}

	private JournalCombinationPageInfoDto getJournalCombinationInfo(String combinationId) {
		Document combinationPage = pageClient.getCombinationPage(combinationId);
		return reader.readCombinationPage(combinationPage);
	}

}
