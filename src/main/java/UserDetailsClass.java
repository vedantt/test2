import java.util.HashMap;
import java.util.Map;

public class UserDetailsClass {


    String url;

    UserDetailsClass(String scheme, String host, String port) {

        this.url = scheme + "://" + host + ":" + port;
    }


    public RestBase getUsers(String token) {

        RestBase restBase = new RestBase(url + "/api/v1/users");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer "+ token);
        restBase.setHeaders(headers);
        restBase.get();

        return restBase;

    }

    public RestBase getUsersFromPhoe(String token, String phone) {

        RestBase restBase = new RestBase(url + "/api/v1/users/" + phone);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer "+ token);
        restBase.setHeaders(headers);
        restBase.get();
        return restBase;

    }


    public RestBase login(String userName, String password) {
        RestBase restClient = new RestBase(url+"/authenticate ");
        String payload = "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}";
        restClient.post(payload);
        return restClient;


    }

}
