package club.geek66.downloadpicture.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 期刊
 * 
 * @author: 橙子
 * @date: 2018年10月1日
 * @time: 下午9:50:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Periodical {

	// id
	private String id;

	// 标题
	private String title;

	// 模特名
	private String modelName;

	// 链接
	private String url;

	// html
	private String html;

	// 所有图片集合
	private List<Picture> pictureList;

}
