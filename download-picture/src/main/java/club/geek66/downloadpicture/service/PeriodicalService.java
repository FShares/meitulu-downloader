package club.geek66.downloadpicture.service;

import java.util.List;

import club.geek66.downloadpicture.domain.Periodical;

/**
 * 周刊Service
 * 
 * @author: 橙子
 * @date: 2018年10月2日
 * @time: 下午10:21:31
 */
public interface PeriodicalService {

	/**
	 * 通过首页获取所有周刊
	 * 
	 * @return
	 */
	List<Periodical> listPeriodicalByIndexHtml();

	/**
	 * 通过模特名获取所有期刊
	 * 
	 * @param modelName
	 * @return
	 */
	List<Periodical> listPeriodicalByModelName(String modelName);

	/**
	 * 通过标签名获取
	 * https://www.meitulu.com/search/%E5%A8%87%E8%89%B3%E5%B0%8F%E8%90%9D%E8%8E%89%E5%BE%90cake
	 * @param tagName
	 * @return
	 */
	List<Periodical> listPeriodicalByTagName(String tagName);
	
	/**
	 * 通过关键词搜索
	 * @param keyword
	 * @return
	 */
	List<Periodical> listPeriodicalBySearch(String keyword);
	
	/**
	 * 通过完整url获取
	 * @param url
	 * @return
	 */
	List<Periodical> listPeriodicalByUrl(String url);

}
