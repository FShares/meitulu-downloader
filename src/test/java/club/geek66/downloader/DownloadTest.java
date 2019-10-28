package club.geek66.downloader;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import static org.springframework.http.HttpMethod.GET;

/**
 * @author: orange
 * @date: 19-2-2
 * @time: 下午2:03
 * @copyright: Copyright 2019 by orange
 */
public class DownloadTest {

	@Test
	@SneakyThrows
	public void test() {

		RestTemplate restTemplate = new RestTemplate();

		// 请求头
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Referer", "https://www.meitulu.com/item/2.html");
		httpHeaders.add("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

		HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

		// 请求
		ResponseEntity<byte[]> response = restTemplate.exchange("https://mtl.ttsqgs.com/images/img/14781/1.jpg", GET, requestEntity, byte[].class);

		// 获取数据
		byte[] data = response.getBody();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

		inputStream.transferTo(new FileOutputStream("/home/orange/a.jpg"));


	}

}
