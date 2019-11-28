package club.geek66.downloader.meitulu.setting;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午3:49
 * @copyright: Copyright 2019 by orange
 */
@Data
@Configuration
@ConfigurationProperties("downloader")
public class DownloaderSetting {

	private String home = "/home/orange/.cache/journal/";

	private String imageHost = "https://mtl.xtpxw.com";

	private String pageHost = "https://www.meitulu.com";

}
