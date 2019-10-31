package club.geek66.downloader.impl.meitulu;

import club.geek66.downloader.impl.meitulu.rpc.MeituluImageClient;
import club.geek66.downloader.impl.meitulu.rpc.MeituluPageClient;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
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
@Configuration
@Import(FeignClientsConfiguration.class)
public class MeituClientConfiguration {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return (requestTemplate -> {
			requestTemplate.header("User-Agent", "Mozilla/5.0");
			requestTemplate.header("Referer", "https://www.meitulu.com/item/1.html");
		});
	}

	@Bean
	public MeituluPageClient meituluPageClient(Encoder encoder, Decoder decoder, Contract contract, Feign.Builder builder) {
		return builder
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract)
				.requestInterceptor(requestInterceptor())
				.target(MeituluPageClient.class, "https://www.meitulu.com");
	}

	@Bean
	public MeituluImageClient meituluImageClient(Encoder encoder, Decoder decoder, Contract contract, Feign.Builder builder) {
		return builder
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract)
				.requestInterceptor(requestInterceptor())
				.target(MeituluImageClient.class, "https://mtl.xtpxw.com");
	}

}
