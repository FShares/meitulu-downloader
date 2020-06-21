package club.geek66.downloader.meitulu.client.feign;

import club.geek66.downloader.meitulu.setting.DownloaderSetting;
import feign.Contract;
import feign.Feign;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author: 橙子
 * @date: 2019/10/29
 * @time: 20:44
 * @copyright: Copyright 2019 by 橙子
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import(FeignClientsConfiguration.class)
public class MeituFeignConfiguration {

	private final DownloaderSetting configuration;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return (requestTemplate -> {
			requestTemplate.header("User-Agent", "Mozilla/5.0");
			//requestTemplate.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
			requestTemplate.header("Referer", "https://www.meitulu.com/item/1.html");
//			requestTemplate.header("Referer", "https://www.meitulu.com/");
			requestTemplate.header("Host","www.meitulu.com");
		});
	}

	public Retryer customRetryer() {
		List<String> errorMsgs = Arrays.asList("No PSK available", "status 504 reading");
		return new Retryer() {
			@Override
			public void continueOrPropagate(RetryableException e) {
				if (errorMsgs.contains(e.getMessage())) {
					// NOTHING TODO
					return;
				}
				if (FeignException.NotFound.class.equals(e.getClass())) {
					log.warn(e.getMessage());
					throw e;
				}
			}

			@Override
			public Retryer clone() {
				return this;
			}
		};
	}

	private ErrorDecoder errorDecoder() {
		ErrorDecoder.Default defaultDecoder = new ErrorDecoder.Default();
		return ((key, response) -> {
			Exception exception = defaultDecoder.decode(key, response);
			if (exception.getMessage().startsWith("status 504 reading")) {
				return new RetryableException(
						response.status(),
						exception.getMessage(),
						response.request().httpMethod(),
						exception,
						new Date());
			}
			return exception;
		});
	}

	@Bean
	public MeituluPageClient meituluPageClient(Encoder encoder, Decoder decoder, Contract contract, Feign.Builder builder) {
		return builder
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract)
				.requestInterceptor(requestInterceptor())
				.retryer(customRetryer())
				.errorDecoder(errorDecoder())
				.target(MeituluPageClient.class, configuration.getPageHost());
	}

	@Bean
	public MeituluImageClient meituluImageClient(Encoder encoder, Decoder decoder, Contract contract, Feign.Builder builder) {
		return builder
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract)
				.requestInterceptor(requestInterceptor())
				.retryer(customRetryer())
				.target(MeituluImageClient.class, configuration.getImageHost());
	}

}
