package club.geek66.downloader.meitulu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 22:33
 * @copyright: Copyright 2019 by 橙子
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {JLineShellAutoConfiguration.class, StandardCommandsAutoConfiguration.class})
public class MeituluJournalServiceTest {

	@Autowired
	private MeituluJournalService service;


	@Test
	public void testDownloadJournal() {
		service.downloadJournal(21075);
//		service.downloadJournal(20238);
//		service.downloadJournal(21222);
//		service.downloadJournal(17196);
//		service.downloadJournal(14324);
//		service.downloadJournal(14107);
//		service.downloadJournal(12847);
//		service.downloadJournal(12689);
//		service.downloadJournal(14059);
//		service.downloadJournal(20841);
//		service.downloadJournal(16868);
//		service.downloadJournal(15008);
//		service.downloadJournal(16237);
//		service.downloadJournal(16238);
//		service.downloadJournal(17634);
//		service.downloadJournal(20739);
//		service.downloadJournal(20858);
//		service.downloadJournal(20691);
//		service.downloadJournal(19751);
//		service.downloadJournal(19019);



//		service.downloadJournal(20398);
//		service.downloadJournal(20399);
//		service.downloadJournal(20400);
//		service.downloadJournal(21273);
//		service.downloadJournal(21251);
//		service.downloadJournal(21244);
//		service.downloadJournal(21222);
	}

}