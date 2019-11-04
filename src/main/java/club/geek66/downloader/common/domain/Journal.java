package club.geek66.downloader.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:19
 * @copyright: Copyright 2019 by orange
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

	public Integer id;

	public String title; // 标题

	public Integer number; // 期刊编号

	private String resolution;

	public Date publishTime; // 发行时间

	private String additional; // 补充说明

	public List<JournalImage> images = new ArrayList<>(); // 图片

}
