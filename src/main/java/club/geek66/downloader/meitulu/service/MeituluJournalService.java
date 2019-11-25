package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.ctx.DownloaderContext;
import club.geek66.downloader.meitulu.dto.JournalImageDto;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.reader.MeituluPageReader;
import club.geek66.downloader.meitulu.rpc.MeituluClient;
import club.geek66.downloader.meitulu.shell.TerminalHelper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 21:31
 * @copyright: Copyright 2019 by 橙子
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MeituluJournalService {

	private final MeituluClient client;

	private final DownloaderContext context;

	private final MeituluPageReader reader;

	private final TerminalHelper helper;

	private void startReadPage(Integer journalIndex) {
		helper.println("read Journal with index" + journalIndex);
	}

	private void finishReadPage(JournalPageInfoDto pageInfo) {
		helper.println("Read Journal with index " + pageInfo.getIndex() + " has finished");
		helper.println("Journal has " + pageInfo.getImageCount() + " images");
		helper.println("Journal model Name is " + pageInfo.getModelName());
		helper.println("Journal title is " + pageInfo.getTitle());
		helper.println("Journal additional is " + pageInfo.getAdditional());
	}

	private void startDownloadImage(JournalImageDto image) {
		helper.println("The journal is " + image.getPageInfo().getIndex() + ", And the image index with " + image.getPageInfo().getIndex() + "start download");
	}

	private void finishDownloadImage(JournalImageDto image) {
		helper.println("The journal is " + image.getPageInfo().getIndex() + ", And the image index with " + image.getPageInfo().getIndex() + " is downloaded");
	}

	public void downloadJournal(Integer journalIndex) {

		startReadPage(journalIndex);
		JournalPageInfoDto pageInfo = reader.readJournalPage(journalIndex);
		finishReadPage(pageInfo);

		List<JournalImageDto> images = new ArrayList<>();
		for (int i = 1; i <= pageInfo.getImageCount(); i++) {
			String path = Path.of(context.getHome(), pageInfo.getIndex().toString(), i + ".jpg").toString();
			JournalImageDto image = new JournalImageDto(i, path, 0, pageInfo);
			images.add(image);
		}
		pageInfo.setImages(images);

		saveImageToLocal(pageInfo);
	}

	private void saveImageToLocal(JournalPageInfoDto pageInfo) {
		List<JournalImageDto> images = pageInfo.getImages();
		images.parallelStream().forEach(this::doSave);
	}

	private void doSave(JournalImageDto image) {
		JournalPageInfoDto journal = image.getPageInfo();
		File self = Path.of(image.getPath()).toFile();
		File parent = self.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (self.exists()) {
			ResponseEntity<Object> modelImageInfo = client.getModelImageInfo(journal.getIndex(), image.getIndex());
			long contentLength = modelImageInfo.getHeaders().getContentLength();
			long fileLength = self.length();
			if (contentLength == fileLength) {
				log.info("image exist! journal index {}, image index {}", journal.getIndex(), image.getIndex());
				return;
			}
		}

		try {
			startDownloadImage(image);
			ResponseEntity<Resource> modelImage = client.getModelImage(journal.getIndex(), image.getIndex());
			FileCopyUtils.copy(Objects.requireNonNull(modelImage.getBody()).getInputStream(), new FileOutputStream(self));
			finishDownloadImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FeignException.NotFound notFound) {
			log.warn("image not found! journal index {}, image index {}", journal.getIndex(), image.getIndex());
		}
	}

}
