import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UserDetailsParallelTest {


    String token;
    UserDetailsClass test;


    @BeforeClass @Parameters({"scheme", "host", "port"})
    public void initParams(String scheme, String host, String port) {
        test = new UserDetailsClass(scheme, host, port);

    }


    @Test(description = "correct Credentials and check correct phone", threadPoolSize = 500, invocationCount = 500, timeOut = 100000)

    public void authenticateWithCredentialsAndCheckPhoneRecord() {

        RestBase restClient = test.login("rupeek", "password");
        Assert.assertEquals(restClient.getStatusCode(), 200, "logged in");
        token = restClient.getElementFromResponse("$.token");
        restClient = test.getUsers(token);
        Assert.assertEquals(restClient.getStatusCode(), 200, "found users");
        String phone = restClient.getElementFromResponse("$.[0].phone");
        restClient = test.getUsersFromPhoe(token, phone);
        Assert.assertEquals(restClient.getStatusCode(), 200, "found user");

    }




}
