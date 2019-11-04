package club.geek66.downloader.meitulu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 23:44
 * @copyright: Copyright 2019 by 橙子
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalCombinationPageInfoDto {

	private String headImage;

	private String description;

	private Integer journalCount;

	private Map<Integer, List<JournalPageInfoDto>> journalPages = new HashMap<>();

}
