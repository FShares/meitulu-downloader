package club.geek66.downloader.meitulu;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan("club.geek66.downloader.meitulu")
public class DownloaderApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DownloaderApplication.class)
				.headless(false)
				.run(args);
	}

}

