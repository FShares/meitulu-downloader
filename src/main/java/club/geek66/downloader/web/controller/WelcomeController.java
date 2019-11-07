package club.geek66.downloader.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 橙子
 * @date: 2019/11/7
 * @time: 23:31
 * @copyright: Copyright 2019 by 橙子
 */
@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeController {

	@GetMapping
	public String welcome() {
		return "hello";
	}

}

