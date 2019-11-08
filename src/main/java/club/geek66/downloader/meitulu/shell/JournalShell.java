package club.geek66.downloader.meitulu.shell;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午4:47
 * @copyright: Copyright 2019 by orange
 */
@ShellComponent
public class JournalShell {

	@Value("${downloader.version}")
	private String version;

	private String home;

	@ShellMethod("Download journal")
	@ShellMethodAvailability("checkHome")
	public String journal(@ShellOption({"-i", "--index"}) String index) {
		return "下载" + index;
	}

	@ShellMethod("查看下载目录")
	@ShellMethodAvailability("checkHome")
	public String home() {
		return home;
	}

	@ShellMethod("设置下载目录, 若下载目录不存在将创建")
	public String setHome(String home) {
		File file = new File(home);
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					return "创建目录失败";
				}
			} catch (IOException e) {
				return "创建目录失败:" + e.getMessage();
			}
		} else {
			if (!file.canWrite()) {
				return "无写权限";
			}
			if (!file.canRead()) {
				return "无读权限";
			}
		}
		this.home = home;
		return "设置下载目录成功";
	}

	public Availability checkHome() {
		return home != null ? Availability.available() : Availability.unavailable("home目录未设置");
	}

	@ShellMethod("下载器版本")
	public String version() {
		return version;
	}

}
