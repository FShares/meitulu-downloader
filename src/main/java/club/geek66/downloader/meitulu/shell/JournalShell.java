package club.geek66.downloader.meitulu.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午4:47
 * @copyright: Copyright 2019 by orange
 */
@ShellComponent
public class JournalShell {

	@ShellMethod("Download journal")
	public String journal(String index) {
		return "下载" + index;
	}

	@ShellMethod("Version")
	public String version() {
		return "0.01";
	}

}
