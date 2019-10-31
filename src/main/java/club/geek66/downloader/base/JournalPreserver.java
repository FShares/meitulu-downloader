package club.geek66.downloader.base;

import club.geek66.downloader.entity.Journal;
import club.geek66.downloader.entity.JournalImage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:18
 * @copyright: Copyright 2019 by orange
 */
public interface JournalPreserver extends InitializingBean {

	/**
	 * 初始化保存目录
	 */
	@Override
	default void afterPropertiesSet() {

		try {
			Files.createDirectories(Paths.get(getSaveDirectory()));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	String getSaveDirectory();

	@SneakyThrows
	default void checkFile(Journal journal) {
		String saveDir = getSaveDirectory() + journal.getNumber();

		Files.createDirectories(Paths.get(saveDir));

		journal.getImages().forEach(meiTuImage -> {
			String fullPath = getFullPath(meiTuImage);
			boolean exists = Files.exists(Paths.get(fullPath));
			if (exists)
				throw new RuntimeException("图片已经写入过了");

		});

	}

	// 保存写真集
	default void saveJournal(Journal journal) {

		// 检查文件是否已经存在
		checkFile(journal);

		journal.getImages().forEach(this::saveImage);

	}

	void saveImage(JournalImage image);

	// 图片的完整路径
	default String getFullPath(JournalImage meiTuImage) {

		String savePathFormat = getSavePathFormat();

		Journal journal = meiTuImage.getJournal();
		return String.format(savePathFormat, journal.getNumber(), meiTuImage.getFullName());
	}

	default String getSavePathFormat() {
		return getSaveDirectory() + "%d/%s";
	}

}
