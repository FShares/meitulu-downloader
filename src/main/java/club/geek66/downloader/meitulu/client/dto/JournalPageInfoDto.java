package club.geek66.downloader.meitulu.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

	private Integer index; // meitulu 写真id

	private String title; // 标题

	private String mechanismName; // 发行机构

	@JsonIgnore
	private String mechanismId; // 机构id

	private String number; // 期刊编号

	private Integer imageCount; // 图片数量

	private String resolution; // 分辨率

	private String modelName; // 模特名

	@JsonIgnore
	private String modelId; // 模特id

	private Date publishDate; // 发行时间

	private String additional; // 补充说明

	@JsonIgnore
	private List<JournalImageDto> images;

}
