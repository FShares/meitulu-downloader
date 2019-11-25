package club.geek66.downloader.meitulu.common.configuration;

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
public class DownloaderConfiguration {

	private String version = "0.1";

	private String home = "/home/orange/.cache/journal/";

}
