package club.geek66.downloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties
public class DownloaderApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		// 线程池维护线程的最少数量
		pool.setCorePoolSize(5);
		// 线程池维护线程的最大数量
		pool.setMaxPoolSize(100);
		// 当调度器shutdown被调用时等待当前被调度的任务完成
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}

	public static void main(String[] args) {
		SpringApplication.run(DownloaderApplication.class, args);
	}

}

