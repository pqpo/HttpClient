package com.pqpo.httpclient.entity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pqpo.httpclient.HttpException;
import com.pqpo.httpclient.HttpRequest;

/**
 * Form表单请求实体
 * @author pqpo
 *
 */
public class FormRequestEntity extends RequestEntity {
	
	private Map<String,String> params = new HashMap<String, String>();
	private String charset = HttpRequest.DEFAULT_ENCODE;
	
	public void addParam(String key,String value){
		params.put(key, value);
	}
	
	public void addParams(Map<String,String> params){
		for(Entry<String, String> entry:params.entrySet()){
			this.params.put(entry.getKey(), entry.getValue());
		}
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	public String getCharset() {
		return charset;
	}
	
	public Map<String,String> getParams(){
		return params;
	}
	
	public String getParam(String key){
		return params.get(key);
	}	
	
	@Override
	public byte[] getBody() throws HttpException {
		if(params.isEmpty()){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry:params.entrySet()){
			sb.append(entry.getKey())
			.append("=")
			.append(entry.getValue())
			.append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		try {
			return sb.toString().getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new HttpException(e);
		}
	}
}
