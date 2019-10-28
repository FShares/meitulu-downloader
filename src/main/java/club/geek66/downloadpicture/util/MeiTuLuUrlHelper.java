package club.geek66.downloadpicture.util;

public interface MeiTuLuUrlHelper {

	// 标签搜索URL前缀
	String URL_TAG_PREFIX = "https://www.meitulu.com/t/";

	// 首页
	String URL_INDEX = "https://www.meitulu.com/";

	// 每个具体的期刊前缀
	String URL_ITEM_PREFIX = "https://www.meitulu.com/item/";

	// 期刊图片的路径前缀
	String URL_IMG_PREFIX = "https://mtl.ttsqgs.com/images/img/";
	
	String URL_SEARCH_PREFIX = "https://www.meitulu.com/search/";

	// 获取每张图片的referer地址
	static String getRefererUrl(String itemNo, Integer pageNo) {

		String refererUrl = URL_ITEM_PREFIX + itemNo + (pageNo == 1 ? "" : "_" + pageNo) + ".html";

		return refererUrl;
	}

	// 获取每张图片的图片Url
	static String getPictureUrl(String itemNo, Integer pageIndex) {

		String pictureUrl = URL_IMG_PREFIX + itemNo + "/" + pageIndex + ".jpg";

		return pictureUrl;
	}

}
