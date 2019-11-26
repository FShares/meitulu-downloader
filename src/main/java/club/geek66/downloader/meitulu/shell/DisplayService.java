package club.geek66.downloader.meitulu.shell;

import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * @author: 橙子
 * @date: 2019/11/25
 * @time: 21:45
 * @copyright: Copyright 2019 by 橙子
 */
@Component
public class DisplayService {

	@Lazy
	@Autowired
	private Terminal terminal;

	private Object lock = new Object();

	public void display(String msg) {
		PrintWriter writer = terminal.writer();
		synchronized (lock) {
			writer.println(msg);
			writer.flush();
		}
	}

}
