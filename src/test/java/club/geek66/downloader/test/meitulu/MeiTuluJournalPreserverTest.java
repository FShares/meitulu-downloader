package club.geek66.downloader.test.meitulu;

import club.geek66.downloader.base.JournalPreserver;
import club.geek66.downloader.base.entity.Journal;
import club.geek66.downloader.impl.meitulu.entity.MeituluJournalImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static club.geek66.downloader.base.entity.ImageExtensionName.JPG;

/**
 * @author: orange
 * @date: 19-2-2
 * @time: 下午10:37
 * @copyright: Copyright 2019 by orange
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MeiTuluJournalPreserverTest {

	@Autowired
	private JournalPreserver journalPreserver;

	@Test
	public void saveJournal() {

		long l = System.currentTimeMillis();

		Journal meiTuJournal = new Journal("Foo", null, 14781, new Date());

		for (int i = 1; i < 37; i++) {
			MeituluJournalImage meiTuImage = new MeituluJournalImage(String.valueOf(i), JPG, meiTuJournal);
			meiTuJournal.getImages().add(meiTuImage);
		}

		journalPreserver.saveJournal(meiTuJournal);

		System.out.println(System.currentTimeMillis() - l);

	}

	@Test
	public void saveJournal2() {

		long l = System.currentTimeMillis();

		Journal meiTuJournal = new Journal("Foo", null, 16148, new Date());

		for (int i = 1; i <= 57; i++) {
			MeituluJournalImage meiTuImage = new MeituluJournalImage(String.valueOf(i), JPG, meiTuJournal);
			meiTuJournal.getImages().add(meiTuImage);
		}

		journalPreserver.saveJournal(meiTuJournal);

		System.out.println(System.currentTimeMillis() - l);

	}

}