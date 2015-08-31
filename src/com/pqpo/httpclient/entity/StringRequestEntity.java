package com.pqpo.httpclient.entity;

import java.io.UnsupportedEncodingException;

import com.pqpo.httpclient.HttpException;
import com.pqpo.httpclient.HttpRequest;

public class StringRequestEntity extends RequestEntity {
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	private String charset = HttpRequest.DEFAULT_ENCODE;
	
	private StringBuilder sb = new StringBuilder();
	
	public StringBuilder appendString(String append){
		sb.append(append);
		return sb;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getCharset() {
		return charset;
	}
	
	@Override
	public byte[] getBody() throws HttpException {
		try {
			return sb.toString().getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new HttpException(e);
		}
	}

}
