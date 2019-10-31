package club.geek66.downloader.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:19
 * @copyright: Copyright 2019 by orange
 */
@Getter
@RequiredArgsConstructor
public class Journal {

	public final String title; // 标题

	public final JournalPublisher publisher; // 发行机构

	public final Integer number; // 期刊编号

	public final List<JournalImage> images = new ArrayList<>(); // 图片

	public final Date publishTime; // 发行时间

}
