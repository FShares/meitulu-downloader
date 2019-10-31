package club.geek66.downloader.base;

import club.geek66.downloader.common.domain.Journal;

import java.util.List;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:16
 * @copyright: Copyright 2019 by orange
 */
@Deprecated
public interface JournalAnalyser {

	/**
	 * 分析出当前写真页的url集合
	 *
	 * @param journalPageUrl
	 * @return
	 */
	List<String> analysedJournalPageUrls(String journalPageUrl);

	/**
	 * 通过写真url分析写真
	 *
	 * @param journalUrl
	 * @return
	 */
	Journal analysedJournal(String journalUrl);

}
