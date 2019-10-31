package club.geek66.downloader.impl.meitulu.rpc;

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
	@GetMapping("/item/{journalId}.html")
	Document getImagePage(@PathVariable String journalId);

	// 分类页面
	@GetMapping("/t/{classification}/")
	Document getClassificationPage(@PathVariable String classification);

	// 模特页面
	@GetMapping("/t/{modelId}/")
	Document getModelPage(@PathVariable String modelId);

	// 机构页面
	@GetMapping("/t/{mechanismId}/")
	Document getMechanismPage(@PathVariable String mechanismId);

	// 查询页面
	@GetMapping("/search/{keyword}")
	Document getSearchPage(@PathVariable String keyword);

}
