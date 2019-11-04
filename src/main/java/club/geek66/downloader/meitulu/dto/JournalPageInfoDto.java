package club.geek66.downloader.meitulu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: 橙子
 * @date: 2019/11/1
 * @time: 22:03
 * @copyright: Copyright 2019 by 橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalPageInfoDto {

	private Integer id; // id

	private String title; // 标题

	private String publishMechanism; // 发行机构

	private String journalNumber; // 期刊编号

	private Integer imageCount;

	private String resolution;

	private String modelName;

	private Date publishTime; // 发行时间

	private String additional; // 补充说明

}
