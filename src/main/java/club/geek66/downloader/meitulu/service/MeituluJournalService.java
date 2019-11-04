package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.common.configuration.DownloaderConfiguration;
import club.geek66.downloader.common.domain.Journal;
import club.geek66.downloader.common.domain.JournalImage;
import club.geek66.downloader.meitulu.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.rpc.MeituluImageClient;
import club.geek66.downloader.meitulu.rpc.MeituluPageClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

	private final MeituluPageReaderService reader;

	public void downloadJournal(Integer journalId) {
		Journal journal = new Journal();
		JournalPageInfoDto pageInfo = reader.readJournalPage(pageClient.getJournalPage(journalId));
		BeanUtils.copyProperties(pageInfo, journal);

		journal.setImages(generateJournalImage(pageInfo));
		saveImageToLocal(journal);
	}

	private void saveImageToLocal(Journal journal) {
		List<JournalImage> images = journal.getImages();
		images.parallelStream().forEach(image -> {
			File self = Path.of(image.getPath()).toFile();
			File parent = self.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			} else {
				if (self.exists()) {
					return;
				}
			}
			try {
				BufferedImage downloadImage = imageClient.getModelImage(journal.getId(), image.getIndex());
				ImageIO.write(downloadImage, "jpg", self);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FeignException.NotFound notFound) {
				log.warn("iamge not found! journal id {}, image index {}", journal.getId(), image.getIndex());
			}
		});
	}

	private List<JournalImage> generateJournalImage(JournalPageInfoDto pageInfoDto) {
		List<JournalImage> images = new ArrayList<>();
		for (int i = 1; i <= pageInfoDto.getImageCount(); i++) {
			JournalImage image = new JournalImage(i, generateImagePath(pageInfoDto.getId(), i), 0);
			images.add(image);
		}
		return images;
	}

	private String generateImagePath(Integer journalId, Integer imageIndex) {
		return Path.of(configuration.getHome(), journalId.toString(), imageIndex + ".jpg").toString();
	}


}
