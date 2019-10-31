package club.geek66.downloader.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:19
 * @copyright: Copyright 2019 by orange
 */
@RequiredArgsConstructor
public class JournalPublisher {

	public final String publisherUrl; // 机构url

	public final List<Journal> journals; // 写真集合

}
