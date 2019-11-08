package club.geek66.downloader.meitulu.rpc;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

	public Document getJournalPage(Integer journalIndex) {
		return pageClient.getJournalPage(journalIndex);
	}

	public Document getJournalPage(Integer journalIndex, Integer pageNo) {
		return pageClient.getJournalPage(journalIndex, pageNo);
	}

	public Document getCombinationPage(String combinationIndex) {
		return pageClient.getCombinationPage(combinationIndex);
	}

	public Document getCombinationPage(String combinationIndex, Integer pageNo) {
		return pageClient.getCombinationPage(combinationIndex, pageNo);
	}

	public Document getSearchPage(String keyword) {
		return pageClient.getSearchPage(keyword);
	}

	public ResponseEntity<Resource> getModelImage(Integer journalIndex, Integer imageIndex) {
		return imageClient.getModelImage(journalIndex, imageIndex);
	}

	public ResponseEntity<Object> getModelImageInfo(Integer journalIndex, Integer imageIndex) {
		return imageClient.getModelImageInfo(journalIndex, imageIndex);
	}

}
