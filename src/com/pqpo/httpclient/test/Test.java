package com.pqpo.httpclient.test;

import java.io.IOException;
import com.pqpo.httpclient.HttpClient;
import com.pqpo.httpclient.HttpException;
import com.pqpo.httpclient.HttpMethod;
import com.pqpo.httpclient.HttpRequest;
import com.pqpo.httpclient.HttpResponse;
import com.pqpo.httpclient.entity.FormRequestEntity;

public class Test {
	public static void main(String[] args) throws HttpException, IOException {

		HttpClient httpClient = new HttpClient();
		HttpRequest httpRequest = new HttpRequest(HttpMethod.POST, "http://www.baidu.com");
		FormRequestEntity body = new FormRequestEntity();
		body.addParam("name", "name");
		body.addParam("pass", "pass");
		httpRequest.setRequestEntity(body);
		HttpResponse response = httpClient.excute(httpRequest);
		System.out.println(response.body("UTF-8"));
	}
}
