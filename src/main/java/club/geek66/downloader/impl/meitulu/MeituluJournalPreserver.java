package club.geek66.downloader.impl.meitulu;

import club.geek66.downloader.base.JournalPreserver;
import club.geek66.downloader.DownloaderConfiguration;
import club.geek66.downloader.entity.Journal;
import club.geek66.downloader.entity.JournalImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

/**
 * @author: orange
 * @date: 19-2-1
 * @time: 下午11:09
 * @copyright: Copyright 2019 by orange
 */
@Component
@RequiredArgsConstructor
public class MeituluJournalPreserver implements JournalPreserver {

	private final DownloaderConfiguration configuration;

	private final RestTemplate restTemplate;

	@Override
	public String getSaveDirectory() {
		return configuration.getHome();
	}

	// 图片的完整路径
	public String getFullPath(JournalImage meiTuImage) {
		Journal journal = meiTuImage.getJournal();
		return String.format(getSavePathFormat(), journal.getNumber(), meiTuImage.getFullName());
	}

	public void saveJournals(List<Journal> journals) {
		journals.forEach(this::saveJournal);
	}

	// 保存图片
	public void saveImage(JournalImage image) {

		// 获取数据
		byte[] data = getImageAsByteArray(image);

		// 保存
		saveImageAsFile(data, image);

	}

	private byte[] getImageAsByteArray(JournalImage image) {

		Journal journal = image.getJournal();

		// 图片url
		String src = image.getImageSrc();

		// 请求头
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Referer", String.format("https://www.meitulu.com/item/%d.html", journal.getNumber()));
		httpHeaders.add("User-Agent", "Mozilla/5.0");

		HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

		// 请求
		ResponseEntity<byte[]> response = restTemplate.exchange(src, GET, requestEntity, byte[].class);

		return response.getBody();

	}

	// 保存图片为文件
	private void saveImageAsFile(byte[] data, JournalImage image) {

		// 获取图片路径
		String fullPath = getFullPath(image);

		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
			 FileOutputStream outputStream = new FileOutputStream(fullPath)) {
			inputStream.transferTo(outputStream);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

	}


}
