package club.geek66.downloader.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:04
 * @copyright: Copyright 2019 by orange
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalImage {

	public Integer index;

	public String path;

	public Integer size;

}
