package club.geek66.downloader.meitulu.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class JournalImageDto {

	private Integer index;

	private String path;

	private Integer size;

	private JournalPageInfoDto pageInfo;

}
