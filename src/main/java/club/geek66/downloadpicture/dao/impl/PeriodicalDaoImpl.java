package club.geek66.downloadpicture.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import club.geek66.downloadpicture.dao.PeriodicalDao;
import club.geek66.downloadpicture.domain.Periodical;
import club.geek66.downloadpicture.domain.Picture;
import club.geek66.downloadpicture.util.Periodicals;
import club.geek66.downloadpicture.util.http.HttpsUtil;
import club.geek66.downloadpicture.util.regex.MeituLuRegex;
import lombok.SneakyThrows;

public class PeriodicalDaoImpl implements PeriodicalDao {

	@Override
	@SneakyThrows
	public List<Periodical> listPeriodicalUrl(String url) {

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", "Baiduspider");

		HttpClient httpClient = HttpsUtil.createDefaultSSLClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);

		HttpEntity httpEntity = httpResponse.getEntity();

		var html = EntityUtils.toString(httpEntity);

		Set<String> periodicalUrlSet = MeituLuRegex.listPeriodicalUrl(html);

		List<Periodical> periodicalList = new ArrayList<>();

		periodicalUrlSet.parallelStream().forEach((periodicalUrl) -> {

			Periodical periodical = new Periodical();
			periodical.setUrl(periodicalUrl);

			String id = Periodicals.getPeriodicalIdByUrl(periodical.getUrl());
			periodical.setId(id);

			addPicture(periodical);

			periodicalList.add(periodical);

		});

		return periodicalList;
	}

}
