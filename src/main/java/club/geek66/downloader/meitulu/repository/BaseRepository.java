package club.geek66.downloader.meitulu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author: 橙子
 * @date: 2019/11/8
 * @time: 0:06
 * @copyright: Copyright 2019 by 橙子
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

}
