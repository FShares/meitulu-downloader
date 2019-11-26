package club.geek66.downloader.meitulu.client.feign;

import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: 橙子
 * @date: 2019/10/28
 * @time: 23:00
 * @copyright: Copyright 2019 by 橙子
 */
public interface MeituluPageClient {

	// 图片页面
	@GetMapping("/item/{journalIndex}.html")
	Document getJournalPage(@PathVariable Integer journalIndex);

	// 第二页开始到结束需要传pageNo否则用上
	@GetMapping("/item/{journalIndex}_{pageNo}.html")
	Document getJournalPage(@PathVariable Integer journalIndex, @PathVariable Integer pageNo);

	@GetMapping("/t/{combinationIndex}/")
	Document getCombinationPage(@PathVariable String combinationIndex);

	@GetMapping("/t/{combinationIndex}/{pageNo}.html")
	Document getCombinationPage(@PathVariable String combinationIndex, @PathVariable Integer pageNo);

	// 查询页面
	@GetMapping("/search/{keyword}")
	Document getSearchPage(@PathVariable String keyword);

}
