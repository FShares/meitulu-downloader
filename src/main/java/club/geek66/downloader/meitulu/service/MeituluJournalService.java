package club.geek66.downloader.meitulu.service;

import club.geek66.downloader.meitulu.client.MeituluClient;
import club.geek66.downloader.meitulu.client.dto.JournalImageDto;
import club.geek66.downloader.meitulu.client.dto.JournalPageInfoDto;
import club.geek66.downloader.meitulu.ctx.DownloaderContext;
import club.geek66.downloader.meitulu.shell.DisplayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

	private final DisplayService helper;

	private final ObjectMapper mapper;

	@PostConstruct
	public void init() {
		System.setProperty("java.awt.headless", "false");
	}

	private void startReadPage(Integer journalIndex) {
		/*helper.display("read Journal with index" + journalIndex);*/
	}

	private void finishReadPage(JournalPageInfoDto pageInfo) {
		/*helper.display("Read Journal with index " + pageInfo.getIndex() + " has finished");
		helper.display("Journal has " + pageInfo.getImageCount() + " images");
		helper.display("Journal model Name is " + pageInfo.getModelName());
		helper.display("Journal title is " + pageInfo.getTitle());
		helper.display("Journal additional is " + pageInfo.getAdditional());*/
	}

	private void startDownloadImage(JournalImageDto image) {
		// helper.display("The journal is " + image.getPageInfo().getIndex() + ", And the image index with " + image.getPageInfo().getIndex() + "start download");
	}

	private void finishDownloadImage(JournalImageDto image) {
		// helper.display("The journal is " + image.getPageInfo().getIndex() + ", And the image index with " + image.getPageInfo().getIndex() + " is downloaded");
	}

	public void downloadJournal(Integer journalIndex) {
		startReadPage(journalIndex);
		JournalPageInfoDto pageInfo = client.getJournalPage(journalIndex);
		finishReadPage(pageInfo);
		downloadImage(pageInfo);
		saveJournalInfo(pageInfo);
		openView(pageInfo);
	}

	private void saveJournalInfo(JournalPageInfoDto pageInfo) {
		Path journalInfo = Path.of(context.getHome(), pageInfo.getIndex().toString(), "journal-info.json");
		if (!Files.exists(journalInfo)) {
			try {
				String json = mapper.writeValueAsString(pageInfo);
				Files.writeString(journalInfo, json);
			} catch (JsonProcessingException ignored) {
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void openView(JournalPageInfoDto pageInfo) {
		try {
			Desktop.getDesktop().open(getJournalFile(pageInfo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getJournalFile(JournalPageInfoDto journalPageInfo) {
		return Path.of(context.getHome(), journalPageInfo.getIndex().toString()).toFile();
	}

	private void downloadImage(JournalPageInfoDto pageInfo) {
		if (createDirectory(pageInfo)) {
			pageInfo.getImages().parallelStream().forEach(this::downloadImage);
		}
		// TODO create dir fail handle
	}

	private boolean createDirectory(JournalPageInfoDto pageInfo) {
		try {
			Files.createDirectories(Path.of(context.getHome(), pageInfo.getIndex().toString()));
			return true;
		} catch (IOException e) {
			log.error("Create directory for journal {}", pageInfo.getIndex());
			return false;
		}
	}

	private void downloadImage(JournalImageDto image) {
		JournalPageInfoDto pageInfo = image.getPageInfo();

		Path imagePath = Path.of(context.getHome(), pageInfo.getIndex().toString(), image.getIndex().toString() + ".jpg");
		File imageFile = imagePath.toFile();

		if (imageFile.exists()) {
			long contentLength = client.getLengthOfModelImage(pageInfo.getIndex(), image.getIndex());
			long fileLength = imageFile.length();
			if (contentLength == fileLength) {
				log.info("Image exist! journal index {}, image index {}", pageInfo.getIndex(), image.getIndex());
				return;
			}
		}

		try {
			startDownloadImage(image);
			Resource modelImage = client.getModelImage(pageInfo.getIndex(), image.getIndex());
			FileCopyUtils.copy(modelImage.getInputStream(), new FileOutputStream(imageFile));
			finishDownloadImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FeignException.NotFound notFound) {
			log.warn("Image not found! journal index {}, image index {}", pageInfo.getIndex(), image.getIndex());
		}
	}

}
