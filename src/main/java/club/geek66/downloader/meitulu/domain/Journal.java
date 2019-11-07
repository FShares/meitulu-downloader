package club.geek66.downloader.meitulu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * @author: orange
 * @date: 19-2-8
 * @time: 下午12:19
 * @copyright: Copyright 2019 by orange
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Journal extends BaseEntity {

	private Integer index; // 索引

	private String title; // 标题

	private Integer number; // 期刊编号

	private String resolution; // 分辨率

	private Date publishDate; // 发行时间

	private String additional; // 补充说明

	@ManyToMany
	@JoinTable(name = "journal__journal_combinations",
			joinColumns = @JoinColumn(name = "journal_id"),
			inverseJoinColumns = @JoinColumn(name = "journal_combination_id"))
	private List<JournalCombination> combinations;

	@OneToMany(mappedBy = "journal")
	private List<JournalImage> images; // 图片

}
