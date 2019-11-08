package club.geek66.downloader.meitulu.repository;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: orange
 * @date: 19-11-8
 * @time: 上午11:19
 * @copyright: Copyright 2019 by orange
 */
@SpringBootTest
@Rollback(false)
@RunWith(SpringRunner.class)
public class JournalCombinationRepositoryTest {

	/*@Autowired
	private JournalCombinationRepository combinationRepository;

	@Autowired
	private JournalRepository journalRepository;

	@Test
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void testCreate() {
		JournalCombination combination = new JournalCombination("2222", "标签1", CombinationType.TAG, "", "标签", "这是一个标签");
		Journal journal = new Journal(2332, "标题", "Vo.123", "1920x1080", new Date(), "附加说明");
		journal.setCombinations(Collections.singletonList(combination));
		combination.setJournals(Collections.singletonList(journal));
		journalRepository.save(journal);
		combinationRepository.save(combination);
	}*/

}