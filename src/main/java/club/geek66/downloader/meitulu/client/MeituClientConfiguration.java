package club.geek66.downloader.meitulu.client;

import club.geek66.downloader.meitulu.common.configuration.DownloaderConfiguration;
import feign.Contract;
import feign.Feign;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


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
public class MeituClientConfiguration {

	private final DownloaderConfiguration configuration;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return (requestTemplate -> {
			requestTemplate.header("User-Agent", "Mozilla/5.0");
			requestTemplate.header("Referer", "https://www.meitulu.com/item/1.html");
		});
	}

	public Retryer customRetryer() {
		return new Retryer() {
			@Override
			public void continueOrPropagate(RetryableException e) {
				if (FeignException.NotFound.class.equals(e.getClass())) {
					log.warn(e.getMessage());
					throw e;
				}
				if (e.getMessage().contains("No PSK available")) {
					log.warn(e.getMessage());
				}
			}

			@Override
			public Retryer clone() {
				return this;
			}
		};
	}

	@Bean
	public MeituluPageClient meituluPageClient(Encoder encoder, Decoder decoder, Contract contract, Feign.Builder builder) {
		return builder
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract)
				.requestInterceptor(requestInterceptor())
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
