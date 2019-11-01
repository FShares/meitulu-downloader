package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.common.domain.JournalCombination;
import club.geek66.downloader.meitulu.rpc.MeituluPageClient;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

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

	private final MeituluPageReaderService reader;

	public JournalCombination downloadJournalCombination(String combinationId) {

		Document combinationPage = pageClient.getCombinationPage(combinationId);
		reader.readCombinationPage(combinationPage);

		return null;
	}

}
