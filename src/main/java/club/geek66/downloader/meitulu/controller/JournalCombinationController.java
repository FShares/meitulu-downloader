package club.geek66.downloader.meitulu.controller;

import club.geek66.downloader.meitulu.domain.JournalCombination;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午3:48
 * @copyright: Copyright 2019 by orange
 */
@RestController
@RequestMapping("/api/v1/journalCombinations")
public class JournalCombinationController {

	@GetMapping
	public Page<JournalCombination> getJournalCombinations() {
		return null;
	}

}
