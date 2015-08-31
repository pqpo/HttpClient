package com.pqpo.httpclient;

public class HttpException extends Exception {
	private static final long serialVersionUID = -7573727076472408728L;
	
	private String message;
	private Throwable cause;
	
	public HttpException(String message){
		this.message = message;
		this.cause = null;
	}
	
	public HttpException(Throwable cause){
		this.cause = cause;
		this.message = cause.getMessage();
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public Throwable getCause() {
		return cause;
	}
}
