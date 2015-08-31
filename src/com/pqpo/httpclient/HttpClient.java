package com.pqpo.httpclient;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import com.pqpo.httpclient.entity.RequestEntity;

/**
 * 轻量级HttpClient
 * @author qiulinmin
 *
 */
public class HttpClient {
	
	public static final int DEFAULT_CONNECT_TIMEOUT = 15000;
	public static final int DEFAULT_READ_TIMEOUT = 15000;
	
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
	private int readTimeout = DEFAULT_READ_TIMEOUT;
	
	private HttpURLConnection connection;
	
	/**
	 * 执行请求
	 * @param request
	 * @return
	 * @throws HttpException
	 */
	public HttpResponse excute(HttpRequest request) throws HttpException{
			initConnection(request);
			setHeaders(request.getHeaders());
			HttpMethod method = request.getMethod();
			if(method==HttpMethod.POST||method==HttpMethod.PUT){
				writeEntity(request.getRequestEntity(),request.getChartset());
			}
			return getResponse(request);
	}

	/**
	 * 初始化连接
	 * @param request
	 * @throws HttpException
	 */
	private void initConnection(HttpRequest request) throws HttpException{
		String urlStr = request.getUrl();
		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setRequestMethod(request.getMethod().toString());
			connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setUseCaches(false);
		} catch (MalformedURLException e) {
			connection.disconnect();
			throw new HttpException(e);
		} catch (IOException e) {
			connection.disconnect();
			throw new HttpException(e);
		}
	}
	
	/**
	 * 获取响应
	 * @param request
	 * @return
	 * @throws HttpException
	 */
	private HttpResponse getResponse(HttpRequest request) throws HttpException {
		try {
			HttpResponse response = new HttpResponse(request,copyInputStream());
			response.setCharset(getCharsetFromContentType(connection.getHeaderField("Content-Type")));
			response.setContentLength(connection.getHeaderFieldInt("Content-Length", -1));
			response.setCode(connection.getResponseCode());
			response.setMessage(connection.getResponseMessage());
			return response;
		} catch (IOException e) {
			throw new HttpException(e);
		}finally{
			connection.disconnect();
		}
	}

	/**
	 * 获取输入流拷贝
	 * @return
	 * @throws HttpException
	 */
	private InputStream copyInputStream() throws HttpException {
		InputStream stream;
		try {
			int code = connection.getResponseCode();
			InputStream is;
			if(code<HttpURLConnection.HTTP_BAD_REQUEST){
				is = connection.getInputStream();
			}else{
				is = connection.getErrorStream();
				if(is==null){
					is = connection.getInputStream();
				}
			}
			//转换为自己的流供响应使用
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int read = -1;
			while((read = is.read(bytes)) != -1){
				bos.write(bytes, 0, read);
			}
			stream = new ByteArrayInputStream(bos.toByteArray());
			bos.flush();
			bos.close();
		} catch (IOException e) {
			throw new HttpException(e);
		}
		return stream;
	}

	/**
	 * 在响应头信息中找charset
	 * @param contentType
	 * @return
	 */
	private String getCharsetFromContentType(String contentType) {
		String charset = null;
		int indexOfChartset = contentType.indexOf("charset=");
		if(indexOfChartset!=-1){
			charset = contentType.substring(indexOfChartset+8, contentType.length());
		}
		if(charset==null){
			charset = HttpRequest.DEFAULT_ENCODE;
		}
		return charset;
	}

	/**
	 * 向输出流中写入实体
	 * @throws HttpException 
	 */
	private void writeEntity(RequestEntity requestEntity,String charset) throws HttpException {
		if(requestEntity==null||requestEntity.getBody()==null||"".equals(requestEntity.getBody())){
			return;
		}
		try {
			byte[] bodyBytes = requestEntity.getBody();
			connection.setRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, requestEntity.getContentType()+";charset="+charset);
			//设置请求实体长度
			connection.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, bodyBytes.length+"");
			OutputStream outputStream = connection.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			bos.write(bodyBytes);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			connection.disconnect();
			throw new HttpException(e);
		}
	}

	/**
	 * 写入头信息
	 * @param headers
	 */
	private void setHeaders(Map<String, Object> headers){
		for(Entry<String, Object> entry:headers.entrySet()){
			connection.setRequestProperty(entry.getKey(), entry.getValue().toString());
		}
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
	
}
