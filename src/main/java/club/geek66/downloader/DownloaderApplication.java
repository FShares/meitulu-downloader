package club.geek66.downloader;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
/*@EnableJpaRepositories*/
@EnableConfigurationProperties
public class DownloaderApplication {

	public static void main(String[] args) {
		/*SpringApplication.run(DownloaderApplication.class, args);*/
		SpringApplication application = new SpringApplication(DownloaderApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.setLogStartupInfo(false);
		application.run(args);
	}

}

