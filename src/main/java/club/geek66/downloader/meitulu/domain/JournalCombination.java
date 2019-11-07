package club.geek66.downloader.meitulu.domain;

import club.geek66.downloader.meitulu.domain.enumeration.CombinationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 21:38
 * @copyright: Copyright 2019 by 橙子
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JournalCombination extends BaseEntity {

	private String index;

	private String name;

	@Enumerated(EnumType.STRING)
	private CombinationType type;

	private String headImage;

	private String title;

	private String description;

	@ManyToMany
	@JoinTable(name = "journal__journal_combinations",
			joinColumns = {@JoinColumn(name = "journal_combination_id")},
			inverseJoinColumns = {@JoinColumn(name = "journal_id")})
	private List<Journal> journals;

}
