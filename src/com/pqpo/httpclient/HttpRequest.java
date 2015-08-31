package com.pqpo.httpclient;

import java.util.HashMap;
import java.util.Map;

import com.pqpo.httpclient.entity.RequestEntity;

/**
 * http请求
 * @author qiulinmin
 *
 */
public class HttpRequest {
	
	public static final String DEFAULT_ENCODE = "UTF-8";
	
	public static final String HEADER_ACCEPT = "Accept";
	public static final String HEADER_ACCEPT_CHARTSET = "Accept-Charset";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_LENGTH = "Content-Length";
	
	private String url;
	private String chartset = DEFAULT_ENCODE;
	private HttpMethod method = HttpMethod.GET;
	private RequestEntity requestEntity;
	private Map<String,Object> header = new HashMap<String, Object>();
	
	public HttpRequest(HttpMethod method,String url) {
		this.url = url;
		this.method = method;
	}

	public void addHeader(String headName,Object headVaule){
		header.put(headName, headVaule);
	}
	
	public Object getHead(String headName){
		return header.get(headName);
	}
	
	public Map<String,Object> getHeaders(){
		return header;
	}
	
	public String getUrl() {
		return url;
	}
	
	public HttpMethod getMethod() {
		return method;
	}
	
	public String getChartset() {
		return chartset;
	}

	public void setChartset(String chartset) {
		this.chartset = chartset;
	}

	public void setRequestEntity(RequestEntity body){
		requestEntity = body;
	}
	
	public RequestEntity getRequestEntity() {
		return requestEntity;
	}
	
}
