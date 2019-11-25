package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.common.configuration.DownloaderConfiguration;
import club.geek66.downloader.meitulu.domain.Journal;
import club.geek66.downloader.meitulu.domain.JournalImage;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.reader.MeituluPageReader;
import club.geek66.downloader.meitulu.rpc.MeituluImageClient;
import club.geek66.downloader.meitulu.rpc.MeituluPageClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

	private final MeituluPageClient pageClient;

	private final MeituluImageClient imageClient;

	private final DownloaderConfiguration configuration;

	private final MeituluPageReader reader;

	public Journal downloadJournal(Integer journalIndex) {
		Journal journal = new Journal();
		JournalPageInfoDto pageInfo = reader.readJournalPage(pageClient.getJournalPage(journalIndex));
		BeanUtils.copyProperties(pageInfo, journal);

		journal.setImages(generateJournalImage(pageInfo, journal));
		saveImageToLocal(journal);
		return null;
	}

	private void saveImageToLocal(Journal journal) {
		List<JournalImage> images = journal.getImages();
		images.parallelStream().forEach(this::doSave);
	}

	private void doSave(JournalImage image) {
		Journal journal = image.getJournal();
		File self = Path.of(image.getPath()).toFile();
		File parent = self.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		} else {
			if (self.exists()) {
				ResponseEntity<Object> modelImageInfo = imageClient.getModelImageInfo(journal.getIndex(), image.getIndex());
				long contentLength = modelImageInfo.getHeaders().getContentLength();
				long fileLength = self.length();
				if (contentLength == fileLength) {
					log.info("image exist! journal index {}, image index {}", journal.getIndex(), image.getIndex());
					return;
				}
			}
		}


		try {
			ResponseEntity<Resource> modelImage = imageClient.getModelImage(journal.getIndex(), image.getIndex());
			FileCopyUtils.copy(modelImage.getBody().getInputStream(), new FileOutputStream(self));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FeignException.NotFound notFound) {
			log.warn("image not found! journal index {}, image index {}", journal.getIndex(), image.getIndex());
		}
	}

	private List<JournalImage> generateJournalImage(JournalPageInfoDto pageInfoDto, Journal journal) {
		List<JournalImage> images = new ArrayList<>();
		for (int i = 1; i <= pageInfoDto.getImageCount(); i++) {
			JournalImage image = new JournalImage(i, journal, generateImagePath(pageInfoDto.getIndex(), i), 0);
			images.add(image);
		}
		return images;
	}

	private String generateImagePath(Integer journalIndex, Integer imageIndex) {
		return Path.of(configuration.getHome(), journalIndex.toString(), imageIndex + ".jpg").toString();
	}

}
