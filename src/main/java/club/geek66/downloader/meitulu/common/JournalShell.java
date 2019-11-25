package club.geek66.downloader.meitulu.common;

import club.geek66.downloader.meitulu.service.MeituluJournalService;
import club.geek66.downloader.meitulu.shell.ShellContext;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午4:47
 * @copyright: Copyright 2019 by orange
 */
@ShellComponent
@RequiredArgsConstructor
public class JournalShell {

	private final MeituluJournalService journalService;

	private final ShellContext shellContext;

	@ShellMethod("Download journal")
	@ShellMethodAvailability("checkHome")
	public String journal(@ShellOption({"-i", "--index"}) Integer index) {
		journalService.downloadJournal(index);
		return "下载完成" + index;
	}

	@ShellMethod("查看下载目录")
	@ShellMethodAvailability("checkHome")
	public String home() {
		return shellContext.getHome();
	}

	@ShellMethod("设置下载目录, 若下载目录不存在将创建")
	public String setHome(String home) {
		shellContext.setHome(home);
		return "设置下载目录成功";
	}

	public Availability checkHome() {
		return shellContext.checkHome() ? Availability.available() : Availability.unavailable("请给目录合理权限");
	}

	@ShellMethod("下载器版本")
	public String version() {
		return shellContext.getVersion();
	}

}
