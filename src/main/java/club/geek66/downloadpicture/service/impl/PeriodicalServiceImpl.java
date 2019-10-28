package club.geek66.downloadpicture.service.impl;

import java.util.List;

import club.geek66.downloadpicture.dao.PeriodicalDao;
import club.geek66.downloadpicture.dao.impl.PeriodicalDaoImpl;
import club.geek66.downloadpicture.domain.Periodical;
import club.geek66.downloadpicture.service.PeriodicalService;
import club.geek66.downloadpicture.util.MeiTuLuUrlHelper;
import lombok.SneakyThrows;

public class PeriodicalServiceImpl implements PeriodicalService {

	private PeriodicalDao periodicalDao = new PeriodicalDaoImpl();

	@Override
	public List<Periodical> listPeriodicalByModelName(String modelName) {

		return null;
	}

	@Override
	public List<Periodical> listPeriodicalByTagName(String tagName) {

		return null;
	}

	/**
	 * 通过首页获取Periodical集合
	 */
	@Override
	@SneakyThrows
	public List<Periodical> listPeriodicalByIndexHtml() {
		List<Periodical> periodicalList = periodicalDao.listPeriodicalUrl(MeiTuLuUrlHelper.URL_INDEX);
		return periodicalList;
	}

	@Override
	public List<Periodical> listPeriodicalBySearch(String keyword) {
		return periodicalDao.listPeriodicalUrl(MeiTuLuUrlHelper.URL_SEARCH_PREFIX + keyword);
	}

	@Override
	public List<Periodical> listPeriodicalByUrl(String url) {
		return periodicalDao.listPeriodicalUrl(url);
	}

}
