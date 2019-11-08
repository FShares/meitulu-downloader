package club.geek66.downloader.meitulu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:04
 * @copyright: Copyright 2019 by orange
 */
@Data
/*@Entity*/
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JournalImage extends BaseEntity {

	public Integer index;

	/*@ManyToOne*/
	public Journal journal;

	public String path;

	public Integer size;

}
