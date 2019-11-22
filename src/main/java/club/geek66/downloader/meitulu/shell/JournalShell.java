package club.geek66.downloader.meitulu.shell;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午4:47
 * @copyright: Copyright 2019 by orange
 */
/*@ShellComponent
@RequiredArgsConstructor
public class JournalShell {

	private final MeituluJournalService journalService;

	private final DownloaderConfiguration configuration;

	@ShellMethod("Download journal")
	@ShellMethodAvailability("checkHome")
	public String journal(@ShellOption({"-i", "--index"}) Integer index) {
		journalService.downloadJournal(index);
		return "下载完成" + index;
	}

	@ShellMethod("查看下载目录")
	@ShellMethodAvailability("checkHome")
	public String home() {
		return configuration.getHome();
	}

	@ShellMethod("设置下载目录, 若下载目录不存在将创建")
	public String setHome(String home) {
		File file = new File(home);
		if (!file.exists()) {
			try {
				file.createNewFile();
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
		configuration.setHome(home);
		return "设置下载目录成功";
	}

	public Availability checkHome() {
		return configuration.getHome() != null ? Availability.available() : Availability.unavailable("home目录未设置");
	}

	@ShellMethod("下载器版本")
	public String version() {
		return configuration.getVersion();
	}

}*/
