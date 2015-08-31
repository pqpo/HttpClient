package com.pqpo.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * http响应
 * @author qiulinmin
 *
 */
public class HttpResponse {

	private HttpRequest request;
	private InputStream is;
	private String charset;
	private String message;
	private int code;
	private int contentLength;

	public HttpResponse(HttpRequest request,InputStream is){
		this.request = request;
		this.is = is;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}

	public InputStream getInputStream() {
		return is;
	}
	
	public String getCharset() {
		return charset;
	}

	protected void setCharset(String charset) {
		this.charset = charset;
	}

	public int getCode() {
		return code;
	}
	protected void setCode(int code) {
		this.code = code;
	}
	public int getContentLength() {
		return contentLength;
	}
	protected void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	/**
	 * 获取响应正文
	 * @return
	 * @throws HttpException
	 */
	public String body() throws HttpException{
		return body(charset==null?request.getChartset():charset);
	}

	/**
	 * 获取响应正文
	 * @param chartset
	 * @return
	 * @throws HttpException
	 */
	public String body(String chartset) throws HttpException{
		if(is==null){
			return "";
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(contentLength);
			int read = -1;
			byte[] bytes = new byte[1024];
			while((read = is.read(bytes))!=-1){
				baos.write(bytes, 0, read);
			}
			return baos.toString(chartset);
		} catch (UnsupportedEncodingException e1) {
			throw new HttpException(e1);
		} catch (IOException e) {
			throw new HttpException(e);
		}finally{
			closeInputStream();
		}
	}

	private void closeInputStream(){
		if(is!=null){
			try {
				is.close();
			} catch (IOException e) {
				//ignore
			}
		}
	}


}
