package club.geek66.downloadpicture.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 图片
 * 
 * @author: 橙子
 * @date: 2018年10月1日
 * @time: 下午9:53:02
 */
@Data
@AllArgsConstructor
public class Picture {

	// 下载地址
	private String url;

	// refererUrl
	private String refererUrl;

}
