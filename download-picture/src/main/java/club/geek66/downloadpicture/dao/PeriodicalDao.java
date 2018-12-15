package club.geek66.downloadpicture.dao;

import java.util.ArrayList;
import java.util.List;

import club.geek66.downloadpicture.domain.Periodical;
import club.geek66.downloadpicture.domain.Picture;
import club.geek66.downloadpicture.util.MeiTuLuUrlHelper;
import club.geek66.downloadpicture.util.http.HttpsUtil;
import club.geek66.downloadpicture.util.regex.MeituLuRegex;

public interface PeriodicalDao {

	final int IMG_PAGE_SIZE = 4;

	List<Periodical> listPeriodicalUrl(String url);

	default void addPicture(Periodical periodical) {
		List<Picture> pictureList = new ArrayList<>();
		periodical.setPictureList(pictureList);

		var url = periodical.getUrl();
		String html = HttpsUtil.getHtmlByUrl(url);

		Integer totalPicture = MeituLuRegex.totalPicture(html);

		int totalPage = (totalPicture + IMG_PAGE_SIZE - 1) / IMG_PAGE_SIZE;

		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			// 从第一个开始遍历
			for (int index = 1; index <= IMG_PAGE_SIZE; index++) {

				// 算出图片索引
				int pageIndex = (pageNo - 1) * IMG_PAGE_SIZE + index;

				if (pageIndex <= totalPicture) {
					// 如果小于总数量

					// refererUrl
					String refererUrl = MeiTuLuUrlHelper.getRefererUrl(periodical.getId(), pageNo);

					// 图片url
					String pictureUrl = MeiTuLuUrlHelper.getPictureUrl(periodical.getId(), pageIndex);

					// 添加到集合
					pictureList.add(new Picture(pictureUrl, refererUrl));

				}

			}

		}

	}

}
