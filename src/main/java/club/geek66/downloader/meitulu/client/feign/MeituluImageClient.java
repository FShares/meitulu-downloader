package club.geek66.downloader.meitulu.client.feign;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: 橙子
 * @date: 2019/10/29
 * @time: 20:44
 * @copyright: Copyright 2019 by 橙子
 */
public interface MeituluImageClient {

	@GetMapping("/images/img/{journalIndex}/{imageIndex}.jpg")
	ResponseEntity<Resource> getModelImage(@PathVariable Integer journalIndex, @PathVariable Integer imageIndex);

	@RequestMapping(value = "/images/img/{journalIndex}/{imageIndex}.jpg", method = RequestMethod.HEAD)
	ResponseEntity<Object> getModelImageInfo(@PathVariable Integer journalIndex, @PathVariable Integer imageIndex);

}
