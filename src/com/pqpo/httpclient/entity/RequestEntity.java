package com.pqpo.httpclient.entity;

import com.pqpo.httpclient.HttpException;

/**
 * 请求实体基类
 * @author qiulinmin
 *
 */
public abstract class RequestEntity {	
	
	public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
	
	private String contentType = DEFAULT_CONTENT_TYPE;
	
	/**
	 * 获取请求content-type
	 * @return
	 */
	public String getContentType(){
		return contentType;
	}
	
	/**
	 * 设置Content-Type
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 * 获取请求正文
	 * @return
	 */
	public abstract byte[] getBody() throws HttpException;

}
