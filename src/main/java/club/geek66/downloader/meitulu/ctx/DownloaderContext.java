package club.geek66.downloader.meitulu.ctx;

import club.geek66.downloader.meitulu.setting.DownloaderSetting;
import club.geek66.downloader.meitulu.setting.SettingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

	private final SettingManager manager;

	@Value("@project.version@")
	private String version;

	@PostConstruct
	public void init() {
		DownloaderSetting setting = manager.getSetting();
		setHome(setting.getHome());
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
		DownloaderSetting setting = manager.getSetting();
		setting.setHome(home);
		manager.updateSetting(setting);
	}

	public boolean checkHome() {
		try {
			DownloaderSetting setting = manager.getSetting();
			checkPermission(new File(setting.getHome()));
			return true;
		} catch (ShellContextException e) {
			return false;
		}
	}

	private void checkPermission(File file) throws ShellContextException {
		boolean canWrite = file.canWrite();
		boolean canRead = file.canRead();
		if (canWrite && canRead) {
			return;
		}
		throw new ShellContextException("检查当前目录发现一个问题, 读?" + canRead + "写?" + canWrite + ", 请赋予读写权限");
	}

	public String getHome() {
		return Path.of(manager.getSetting().getHome()).toString();
	}

	public String getVersion() {
		return version;
	}

	private class ShellContextException extends RuntimeException {

		private ShellContextException(String msg) {
			super(msg);
		}

	}

}
