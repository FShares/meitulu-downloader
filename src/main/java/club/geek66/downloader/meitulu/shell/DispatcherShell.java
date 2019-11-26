package club.geek66.downloader.meitulu.shell;

import club.geek66.downloader.meitulu.ctx.DownloaderContext;
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
public class DispatcherShell {

	private final DownloaderContext context;

	private final DispatcherShellService shellService;

	private final DisplayService displayService;

	@ShellMethod("Download journal")
	@ShellMethodAvailability("checkHome")
	public void downloadJournal(@ShellOption(value = {"-i", "--index"}, help = "索引") Integer index) {
		shellService.downloadJournal(index);
	}

	@ShellMethod("查看下载目录")
	@ShellMethodAvailability("checkHome")
	public void showHome() {
		displayService.display(context.getHome());
	}

	@ShellMethod("设置下载目录, 若下载目录不存在将创建")
	public void setHome(String home) {
		context.setHome(home);
		displayService.display("设置下载目录成功");
	}

	@ShellMethod("下载器版本")
	public void showVersion() {
		displayService.display(context.getVersion());
	}

	public Availability checkHome() {
		return context.checkHome() ? Availability.available() : Availability.unavailable("请给目录合理权限");
	}

}
