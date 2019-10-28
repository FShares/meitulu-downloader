package club.geek66.downloadpicture.util.regex;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author: 橙子
 * @date: 2018年10月2日
 * @time: 下午9:33:17
 */
public class MeituLuRegex {

	public static Integer totalPicture(String html) {
		String regex = "([\\s]?[\\d]*[\\s]?张)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			// 匹配的对象
			String group = matcher.group();
			String pictureSizeStr = group.replace(" ", "").replace("张", "");
			Integer pictureSize = Integer.valueOf(pictureSizeStr);
			return pictureSize;
		}
		return null;
	}

	public static Set<String> listPeriodicalUrl(String html) {

		Set<String> urls = new HashSet<>();

		String regex = "(https://www.meitulu.com/item/[\\d]*\\.html)";

		// 创建 Pattern 对象
		Pattern pattern = Pattern.compile(regex);

		// 现在创建 matcher 对象
		Matcher matcher = pattern.matcher(html);

		while (matcher.find()) {
			var matcherString = matcher.group();
			urls.add(matcherString);
		}
		return urls;
	}

}
