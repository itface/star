package com.itface.star.spring;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

/**
 * 通过调试跟踪Spring的源码，声明@ResponseBody时，Spring会通过AnnotationMethodHandlerAdapter去寻找对应的HttpMessageConverter, 我们这里声明返回的类型是String，
 * 于是对应StringHttpMessageConverter。通过实验，猜测这个StringHttpMessageConverter也就是<mvc:annotation-driven />触发的默认的字符串转换工作类。 
 * 比较不幸的是，StringHttpMessageConverter所使用的默认字符集是ISO-8859-1
 * public class StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {  
  
    public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");  
......  
这里不得不提的是与StringHttpMessageConverter 同级的类MappingJacksonHttpMessageConverter，天知道是什么原因：同一个作者，对于这两个类，默认字符集一个是ISO-8859-1，一个是UTF-8。 

既然事已如此，那就想办法把这个地方用到的ISO-8859-1也改成UTF-8了。有两个思路： 
1. 替换默认字符集； 
2. 替换StringHttpMessageConverter
 * @author Administrator
 *
 */
public class UTF8StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final List<Charset> availableCharsets;

	private boolean writeAcceptCharset = true;

	public UTF8StringHttpMessageConverter() {
		super(new MediaType("text", "plain", DEFAULT_CHARSET), MediaType.ALL);
		this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
	}

	/**
	 * Indicates whether the {@code Accept-Charset} should be written to any outgoing request.
	 * <p>Default is {@code true}.
	 */
	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	protected String readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException {
		Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
		return FileCopyUtils.copyToString(new InputStreamReader(inputMessage.getBody(), charset));
	}

	@Override
	protected Long getContentLength(String s, MediaType contentType) {
		Charset charset = getContentTypeCharset(contentType);
		try {
			return (long) s.getBytes(charset.name()).length;
		}
		catch (UnsupportedEncodingException ex) {
			// should not occur
			throw new InternalError(ex.getMessage());
		}
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
		if (writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
		FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset));
	}

	/**
	 * Return the list of supported {@link Charset}.
	 *
	 * <p>By default, returns {@link Charset#availableCharsets()}. Can be overridden in subclasses.
	 *
	 * @return the list of accepted charsets
	 */
	protected List<Charset> getAcceptedCharsets() {
		return this.availableCharsets;
	}

	private Charset getContentTypeCharset(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			return contentType.getCharSet();
		}
		else {
			return DEFAULT_CHARSET;
		}
	}

}
