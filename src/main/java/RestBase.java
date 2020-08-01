import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class RestBase {

    Map<String, String> queryParametes = new HashMap<String, String>();
    private String url;
    private RequestSpecification request;
    private Response response;


    public RestBase() {
        request = given();
    }

    public RestBase(String url) {

        this();
        this.url = url;

    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public void setQueryParameter(Map<String, String> queryParameter) {

        request.queryParams(queryParameter);
    }
    public void setHeaders(Map<String, String> headers) {

        request.headers(headers);
    }

    public String getElementFromResponse(String path) {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(response.asString());
        return JsonPath.read(document, path).toString();

    }

    public void get() {

        System.out.println(url);
        response = request.relaxedHTTPSValidation().get(url);
    }

    public void post(String payload) {

        System.out.println(url);
        response = request.relaxedHTTPSValidation().body(payload).post(url);
    }


}
