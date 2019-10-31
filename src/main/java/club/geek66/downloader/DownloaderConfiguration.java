package club.geek66.downloader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午3:49
 * @copyright: Copyright 2019 by orange
 */
@Data
@ConfigurationProperties("image")
public class DownloaderConfiguration {

	private String home = "/home/orange/.cache/journal/";

	private MeituluConfiguration meitulu = new MeituluConfiguration();

	public static class MeituluConfiguration {

		// 每一页的大小
		private Integer imagePagesize = 4;

		// 每一页的专辑数量
		private Integer JournalPageSize = 60;

	}

}
