package club.geek66.downloader.impl.meitulu.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author: 橙子
 * @date: 2019/10/28
 * @time: 23:26
 * @copyright: Copyright 2019 by 橙子
 */
@Getter
@RequiredArgsConstructor
public enum MeituClassification {

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
