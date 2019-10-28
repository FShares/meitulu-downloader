package club.geek66.downloader.base.entity;

/**
 * @author: orange
 * @date: 19-2-1
 * @time: 下午9:30
 * @copyright: Copyright 2019 by orange
 */
public enum ImageExtensionName {

	JPEG("jpeg"), JPG("jpg"), PNG("png");

	@Override
	public String toString() {
		return extensionName;
	}

	ImageExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	private String extensionName;

}
