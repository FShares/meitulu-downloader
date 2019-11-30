package club.geek66.downloader.meitulu;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DownloaderApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DownloaderApplication.class)
				.headless(false)
				.run(args);
	}

}

