package club.geek66.downloader.meitulu.rpc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.image.BufferedImage;

/**
 * @author: 橙子
 * @date: 2019/10/29
 * @time: 20:44
 * @copyright: Copyright 2019 by 橙子
 */
public interface MeituluImageClient {

	// 图片页面
	@GetMapping("/images/img/{journalId}/{index}.jpg")
	BufferedImage getModelImage(@PathVariable Integer journalId, @PathVariable Integer index);

}
