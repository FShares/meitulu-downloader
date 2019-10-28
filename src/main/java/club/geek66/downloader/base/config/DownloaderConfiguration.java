package club.geek66.downloader.base.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午3:49
 * @copyright: Copyright 2019 by orange
 */
@Data
@Component
public class DownloaderConfiguration {

	private String saveDirectory = "/home/orange/.cache/journal/";

	public static class MeituluConfiguration {

		// 每一页的大小
		private Integer journalImagePageSize = 4;

		// 每一页的专辑数量
		private Integer JournalPageSize = 60;

	}

}
