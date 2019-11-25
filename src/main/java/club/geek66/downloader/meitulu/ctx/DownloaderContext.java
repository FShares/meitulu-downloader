package club.geek66.downloader.meitulu.ctx;

import club.geek66.downloader.meitulu.common.configuration.DownloaderConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author: orange
 * @date: 19-11-25
 * @time: 下午4:31
 * @copyright: Copyright 2019 by orange
 */
@Component
@RequiredArgsConstructor
public final class DownloaderContext {

	private final DownloaderConfiguration config;

	private String home;

	@PostConstruct
	public void init() {
		setHome(config.getHome());
	}

	public void setHome(String home) throws ShellContextException {
		File file = new File(home);
		if (!file.exists()) {
			try {
				Files.createDirectories(Path.of(file.getPath()));
			} catch (IOException e) {
				throw new ShellContextException("创建目录失败:" + e.getMessage());
			}
		}
		checkPermission(file);
		this.home = home;
	}

	public boolean checkHome() {
		try {
			checkPermission(new File(home));
			return true;
		} catch (ShellContextException e) {
			return false;
		}
	}

	private void checkPermission(File file) throws ShellContextException {
		boolean canWrite = file.canWrite();
		boolean canRead = file.canRead();
		if (!(canWrite && canRead)) {
			throw new ShellContextException("请授予读写权限, 可读?" + canRead + "可写?" + canWrite);
		}
	}

	public String getHome() {
		return this.home;
	}

	public String getVersion() {
		return config.getVersion();
	}

	private class ShellContextException extends RuntimeException {

		private ShellContextException(String msg) {
			super(msg);
		}

	}

}
