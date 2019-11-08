package club.geek66.downloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties
public class DownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DownloaderApplication.class, args);
	}

}

