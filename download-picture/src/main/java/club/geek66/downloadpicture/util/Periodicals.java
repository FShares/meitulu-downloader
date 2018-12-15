package club.geek66.downloadpicture.util;

public class Periodicals {

	private static String ITEM_PREFIX = "item/";

	private static String ITEM_SUFFIX = ".html";

	/**
	 * 通过期刊的URL获取id<br>
	 * 例如 https://www.meitulu.com/item/13781.html<br>
	 * 获取结果为13781
	 * 
	 * @param url
	 * @return
	 */
	public static String getPeriodicalIdByUrl(String url) {
		String itemNo = url.substring(url.indexOf(ITEM_PREFIX) + ITEM_PREFIX.length(), url.indexOf(ITEM_SUFFIX));
		return itemNo;
	}

}
