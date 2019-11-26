package club.geek66.downloader.meitulu.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: 橙子
 * @date: 2019/10/28
 * @time: 23:00
 * @copyright: Copyright 2019 by 橙子
 */
interface MeituluPageClient {

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

	/**
	 * @author: 橙子
	 * @date: 2019/10/28
	 * @time: 23:26
	 * @copyright: Copyright 2019 by 橙子
	 */
	@Getter
	@RequiredArgsConstructor
	enum MeituClassification {

		NV_SHEN("nvshen", "女神"), JI_PIN("jipin", "极品"), NENMO("nenmo", "嫩模"),

		HONG_REN("wangluohongren", "网络红人"), FENG_SU("fengsuniang", "风俗娘"), QI_ZHI("qizhi", "气质"),

		YOU_WU("youwu", "尤物"), BAO_RU("baoru", "爆乳"), XING_GAN("xinggan", "性感"),

		YOU_HUO("youhuo", "诱惑"), MEI_XIONG("meixiong", "美胸"), SHAO_FU("shaofu", "少妇"),

		CHANG_TUI("changtui", "长腿"), MENG_MEI("mengmeizi", "萌妹子"), LUO_LI("luoli", "萝莉"),

		KE_AI("keai", "可爱"), HU_WAI("huwai", "户外"), BI_JI_NI("bijini", "比基尼"),

		QING_CHUN("qingchun", "清纯"), WEI_MEI("weimei", "唯美"), QING_XIN("qingxin", "清新");

		private final String id;

		private final String display;

		@Override
		public String toString() {
			return this.display;
		}

	}

}
