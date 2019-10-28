package club.geek66.downloader.base.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:04
 * @copyright: Copyright 2019 by orange
 */
@Data
@RequiredArgsConstructor
public abstract class JournalImage {

	public final String name; // 名字

	public final ImageExtensionName extensionName; // 扩展名

	public final Journal journal; // 所属的写真集

	public String getFullName() {
		return "" + name + "." + extensionName;
	}

	/**
	 * 构建图片的src, 用于下载图片
	 *
	 * @return
	 */
	public abstract String getImageSrc();



}
