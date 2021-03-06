import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UserDetailsTest {


    String token;
    UserDetailsClass test;

    @BeforeClass @Parameters({"scheme", "host", "port"})
    public void initParams(String scheme, String host, String port) {
        test = new UserDetailsClass(scheme, host, port);

    }


    @Test(description = "check it ")

    public void authenticateWithWrongCredentials() {

        RestBase restClient = test.login("incorrect", "correct");
        Assert.assertEquals(restClient.getStatusCode(), 401, "incorrect credentails");


    }

    @Test(description = "correct Credentials and check correct phone")

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

    @Test(description = "correct Credentials and check all  phones")

    public void authenticateWithCredentialsCheckAllPhoneNumbers() {

        RestBase restClient = test.login("rupeek", "password");
        Assert.assertEquals(restClient.getStatusCode(), 200, "logged in");
        token = restClient.getElementFromResponse("$.token");
        restClient = test.getUsers(token);

        JSONArray allPhoneNumbers = new JSONArray(restClient.getElementFromResponse("$..phone"));

        for (int i = 0; i < allPhoneNumbers.length(); i++) {
            restClient = test.getUsersFromPhoe(token, allPhoneNumbers.get(i).toString());
            Assert.assertEquals(restClient.getStatusCode(), 200, "found users");

        }


    }


    @Test(description = " bug 1 correct Credentials and check incorrect phone")

    public void authenticateWithCredentialsAndCheckIncorrectPhone() {

        RestBase restClient = test.login("rupeek", "password");
        Assert.assertEquals(restClient.getStatusCode(), 200, "logged in");
        token = restClient.getElementFromResponse("$.token");
        restClient = test.getUsers(token);
        Assert.assertEquals(restClient.getStatusCode(), 200, "found users");
        String phone = restClient.getElementFromResponse("$.[0].phone");
        restClient = test.getUsersFromPhoe(token, "phone");
        Assert.assertEquals(restClient.getStatusCode(), 404, "found users");

    }


}
