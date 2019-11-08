package club.geek66.downloader.meitulu.controller;

import club.geek66.downloader.meitulu.domain.Journal;
import club.geek66.downloader.meitulu.service.MeituluJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 下午3:51
 * @copyright: Copyright 2019 by orange
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/journals")
public class JournalController {

	private final MeituluJournalService service;

	@PostMapping("/downloads")
	public ResponseEntity<Journal> createJournal(@RequestParam Integer index) {
		return ResponseEntity.ok(service.downloadJournal(index));
	}

	@GetMapping("/downloads")
	public ResponseEntity<Journal> getJournal(@RequestParam Integer index) {
		return ResponseEntity.ok(service.downloadJournal(index));
	}

}
