package club.geek66.downloadpicture.util.http;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import lombok.SneakyThrows;

public class HttpsUtil {

	// Https客户端
	public static CloseableHttpClient createDefaultSSLClient() {

		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();

	}

	@SneakyThrows
	public static String getHtmlByUrl(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("User-Agent", "Baiduspider");
		HttpClient httpClient = HttpsUtil.createDefaultSSLClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		return EntityUtils.toString(httpEntity, "UTF-8");
	}

	@SneakyThrows
	public static HttpEntity getPictureInputStream(String pictureUrl, String refererUrl) {
		HttpGet httpGet = new HttpGet(pictureUrl);
		httpGet.setHeader("User-Agent", "Baiduspider");
		httpGet.setHeader("referer", refererUrl);
		HttpClient httpClient = HttpsUtil.createDefaultSSLClient();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		return httpEntity;
	}

}
