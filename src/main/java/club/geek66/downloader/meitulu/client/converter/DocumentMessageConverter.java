package club.geek66.downloader.meitulu.client.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 21:11
 * @copyright: Copyright 2019 by 橙子
 */
@Component
public class DocumentMessageConverter implements HttpMessageConverter<Document> {

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return clazz == Document.class;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return clazz == Document.class;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Collections.singletonList(MediaType.TEXT_HTML);
	}

	@Override
	public Document read(Class<? extends Document> clazz, HttpInputMessage inputMessage) throws IOException {
		String content = StreamUtils.copyToString(inputMessage.getBody(), Charset.defaultCharset());
		return Jsoup.parse(content);
	}

	@Override
	public void write(Document document, MediaType contentType, HttpOutputMessage outputMessage) throws IOException {
		byte[] bytes = document.toString().getBytes();
		outputMessage.getBody().write(bytes);
	}

}
