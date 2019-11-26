package club.geek66.downloader.meitulu.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 橙子
 * @date: 2019/11/25
 * @time: 22:26
 * @copyright: Copyright 2019 by 橙子
 */
@Data
@NoArgsConstructor
public class JournalImageDto {

	public JournalImageDto(Integer index, JournalPageInfoDto pageInfo) {
		this.index = index;
		this.pageInfo = pageInfo;
	}

	private Integer index;

	private Integer size;

	private String type;

	private JournalPageInfoDto pageInfo;

}
