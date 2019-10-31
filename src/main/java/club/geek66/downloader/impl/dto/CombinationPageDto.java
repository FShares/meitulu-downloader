package club.geek66.downloader.impl.dto;

import club.geek66.downloader.common.domain.Journal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 23:44
 * @copyright: Copyright 2019 by 橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinationPageDto {

	private Integer journalCount;

	private List<Journal> journals;

}
