package club.geek66.downloader.impl.meitulu.service;

import club.geek66.downloader.common.configuration.DownloaderConfiguration;
import club.geek66.downloader.common.domain.Journal;
import club.geek66.downloader.common.domain.JournalImage;
import club.geek66.downloader.impl.meitulu.rpc.MeituluImageClient;
import club.geek66.downloader.impl.meitulu.rpc.MeituluPageClient;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 21:31
 * @copyright: Copyright 2019 by 橙子
 */
@Service
@RequiredArgsConstructor
public class MeituluJournalService {

	private final MeituluPageClient pageClient;

	private final MeituluImageClient imageClient;

	private final DownloaderConfiguration configuration;

	private final MeituluPageReaderService reader;

	public void downloadJournal(Integer journalId) {
		Document document = pageClient.getImagePage(journalId.toString());
		Journal journal = reader.readJournalPage(document);
		List<JournalImage> images = journal.getImages();
		for (int i = 1; i <= journal.getImageCount(); i++) {
			JournalImage image = new JournalImage(i, generateImagePath(journalId, i), 0);
			images.add(image);
		}

		images.parallelStream().forEach(image -> {
			BufferedImage downloadImage = imageClient.getModelImage(journalId, image.getIndex());
			File self = Path.of(image.getPath()).toFile();
			File parent = self.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			try {
				ImageIO.write(downloadImage, "jpg", self);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	private String generateImagePath(Integer journalId, Integer imageIndex) {
		return Path.of(configuration.getHome(), journalId.toString(), imageIndex + ".jpg").toString();
	}


}
