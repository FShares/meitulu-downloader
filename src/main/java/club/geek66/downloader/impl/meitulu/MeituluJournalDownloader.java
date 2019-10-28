package club.geek66.downloader.impl.meitulu;

import club.geek66.downloader.base.entity.Journal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 入口类
 *
 * @author: orange
 * @date: 19-2-1
 * @time: 下午11:05
 * @copyright: Copyright 2019 by orange
 */
@Component
@RequiredArgsConstructor
public class MeituluJournalDownloader {

	private final MeituluJournalAnalyser journalAnalyser;

	private final MeituluJournalPreserver journalPreserver;

	// 分页下载写真
	public void downloadJournalByPaging(String url) {

		List<Journal> journals = journalAnalyser.analysedJournalsByPaging(url);

		journalPreserver.saveJournals(journals);

	}

}
