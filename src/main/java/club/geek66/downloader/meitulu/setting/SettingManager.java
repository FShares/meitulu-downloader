package club.geek66.downloader.meitulu.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author: orange
 * @date: 19-11-28
 * @time: 下午5:33
 * @copyright: Copyright 2019 by orange
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SettingManager {

	private final ObjectMapper mapper;

	@Autowired
	private DownloaderSetting defaultSetting;

	private Path settingPath;

	private DownloaderSetting tempSetting;

	@PostConstruct
	public void initSetting() throws IOException {

		DownloaderSetting newSetting = new DownloaderSetting();
		BeanUtils.copyProperties(defaultSetting, newSetting);
		defaultSetting = newSetting;

		settingPath = Path.of(System.getProperty("user.home"), ".journal-downloader", "conf.json");
		if (!Files.exists(settingPath)) {
			Files.createDirectories(settingPath.getParent());
			updateSetting(defaultSetting);
		}
	}

	private DownloaderSetting readLocalSetting() {
		try {
			String settingJson = Files.readString(settingPath);
			return mapper.readValue(settingJson, DownloaderSetting.class);
		} catch (Exception e) {
			return defaultSetting;
		}
	}

	public DownloaderSetting getSetting() {
		if (tempSetting != null) {
			return tempSetting;
		}
		return readLocalSetting();
	}

	public DownloaderSetting updateSetting(DownloaderSetting patchSetting) {
		DownloaderSetting originSetting = readLocalSetting();
		if (originSetting != patchSetting) {
			BeanUtils.copyProperties(patchSetting, originSetting);
		}
		saveSetting(patchSetting);
		tempSetting = originSetting;
		return originSetting;
	}

	private void saveSetting(DownloaderSetting patchSetting) {
		try {
			String setting = mapper.writeValueAsString(patchSetting);
			Files.write(settingPath, setting.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
