package club.geek66.downloader.meitulu.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author: orange
 * @date: 19-11-25
 * @time: 下午6:05
 * @copyright: Copyright 2019 by orange
 */
@Component
public class CustomPromptProvider implements PromptProvider {

	@Override
	public AttributedString getPrompt() {
		return new AttributedString("Downloader:>",
				AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
	}

}
