package club.geek66.downloader.meitulu.client.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 橙子
 * @date: 2019/10/31
 * @time: 21:11
 * @copyright: Copyright 2019 by 橙子
 */
@Component
public class ImageMessageConverter implements HttpMessageConverter<BufferedImage> {

	private static List<MediaType> SUPPORTED_MEDIA_TYPES = Arrays.asList(MediaType.IMAGE_GIF, MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG);

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return clazz == BufferedImage.class;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return clazz == BufferedImage.class;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return SUPPORTED_MEDIA_TYPES;
	}

	@Override
	public BufferedImage read(Class<? extends BufferedImage> clazz, HttpInputMessage inputMessage) throws IOException {
		return ImageIO.read(inputMessage.getBody());
	}

	@Override
	public void write(BufferedImage bufferedImage, MediaType contentType, HttpOutputMessage outputMessage) throws IOException {
		ImageIO.write(bufferedImage, contentType.getSubtype(), outputMessage.getBody());
	}

}
