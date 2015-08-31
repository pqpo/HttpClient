# HttpClient
##一个基于HttpURLConnection的轻量级HttpClient，请求实体均继承与RequestEntity，可拓展性强。
###
public class Test {

	public static void main(String[] args) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		HttpRequest httpRequest = new HttpRequest(HttpMethod.POST,"http://localhost:8080/HttpClientTest/NewFile.jsp");
		FormRequestEntity body = new FormRequestEntity();
		body.addParam("name", "admin");
		body.addParam("pass", "121212");
		httpRequest.setRequestEntity(body);
		HttpResponse response = httpClient.excute(httpRequest);
		System.out.println(response.body());
	}
}


