package club.geek66.downloader.impl.meitulu.entity;

import club.geek66.downloader.entity.ImageExtensionName;
import club.geek66.downloader.entity.Journal;
import club.geek66.downloader.entity.JournalImage;

/**
 * @author: orange
 * @date: 19-2-1
 * @time: 下午9:33
 * @copyright: Copyright 2019 by orange
 */
public class MeituluJournalImage extends JournalImage {

	private static final String srcFormat = "https://mtl.ttsqgs.com/images/img/%d/%s";

	public MeituluJournalImage(String name, ImageExtensionName extensionName, Journal journal) {
		super(name, extensionName, journal);
	}

	@Override
	public String getImageSrc() {

		Journal journal = this.getJournal();

		return String.format(srcFormat, journal.getNumber(), this.getFullName());
	}

}
