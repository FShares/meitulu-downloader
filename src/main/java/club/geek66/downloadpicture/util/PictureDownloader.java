package club.geek66.downloadpicture.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;

import club.geek66.downloadpicture.domain.Periodical;
import club.geek66.downloadpicture.domain.Picture;
import club.geek66.downloadpicture.util.http.HttpsUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 下载类
 * 
 * @author: 橙子
 * @date: 2018年10月3日
 * @time: 下午10:19:39
 */
@Slf4j
public class PictureDownloader {

	private static String DOWNLOAD_PATH = "D:/pictures/";

	private static File file = new File(DOWNLOAD_PATH);

	private PictureDownloader() {
	}

	public static void init() {
		if (!file.exists()) {
			// 不存在, 创建
			log.info("下载文件夹初始完毕");
			boolean success = file.mkdir();
			if (success) {
				log.info("下载文件夹初始完毕");
			} else {
				log.error("下载文件夹初始失败, 请取消文件夹权限");
				System.exit(-1);
			}
		}

		if (file.isDirectory()) {
			log.info("下载文件夹初始完毕");
		} else {
			log.error("下载文件夹可能没有, 又或者是一个文件");
			System.exit(-1);
		}
	}

	public static void downloadPicture(Periodical periodical) {

		List<Picture> pictureList = periodical.getPictureList();

		// 周刊id相当于文件名
		String id = periodical.getId();

		// 临时
		List<File> tempFile = Arrays.stream(file.listFiles()).parallel().filter(file -> file.getName().equals(id))
				.collect(Collectors.toList());

		// 所有的图片都要下载到这个文件夹
		File parentFile = tempFile.size() == 0 ? new File(file, id) : tempFile.get(0);

		if (!parentFile.exists()) {
			// 不存在
			parentFile.mkdir();
		}

		pictureList.parallelStream().forEach(picture -> {

			String fileName = pictureList.indexOf(picture) + 1 + "";

			HttpEntity httpEntity = HttpsUtil.getPictureInputStream(picture.getUrl(), picture.getRefererUrl());

			File file = new File(parentFile, fileName + ".jpg");

			try {

				InputStream inputStream = httpEntity.getContent();
				System.out.println("file ->" + file.length() + "input -> " + httpEntity.getContentLength());

				if (file.exists() || file.length() > httpEntity.getContentLength()) {
					// 大小相同说明一样
					return;
				}
				IOUtils.copy(inputStream, new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

	}

}
