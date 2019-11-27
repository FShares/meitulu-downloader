package club.geek66.downloader.meitulu.shell;

import club.geek66.downloader.meitulu.service.MeituluJournalCombinationService;
import club.geek66.downloader.meitulu.service.MeituluJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author: orange
 * @date: 19-11-25
 * @time: 下午5:53
 * @copyright: Copyright 2019 by orange
 */
@Service
@RequiredArgsConstructor
public class DispatcherShellService {

	private final MeituluJournalService journalService;

	private final MeituluJournalCombinationService combinationService;

	public void downloadJournal(Integer index) {
		journalService.downloadJournal(index);
	}

	public void downloadCombination(String index) {
		combinationService.downloadJournalCombination(index);
	}

}
