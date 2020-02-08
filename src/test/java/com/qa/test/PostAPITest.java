package com.qa.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PostAPITest extends TestBase {
    TestBase testBase;
    String serviceUrl, apiURL, url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeMethod
    public void setUp() {
        testBase = new TestBase();
        serviceUrl = prop.getProperty("URL");
        apiURL = prop.getProperty("serviceURL");
        url = serviceUrl + apiURL;
    }

    @Test
    public void postApiTest() throws IOException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("John", "Analyst");
        mapper.writeValue(new File("src/main/java/com/qa/data/users.json"), users);
        String usersJSONString = mapper.writeValueAsString(users);
        System.out.println(usersJSONString);
        closeableHttpResponse= restClient.post(url, usersJSONString, headerMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Fail POST Request");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJSON = new JSONObject(responseString);
        System.out.println("Response from POST >>> " + responseJSON);
        Users usersResponseObj = mapper.readValue(responseString, Users.class);
        System.out.println("Response Validation >>> " + usersResponseObj);
        Assert.assertTrue(users.getName().equals(usersResponseObj.getName()));
        Assert.assertTrue(users.getJob().equals(usersResponseObj.getJob()));
        System.out.println("Response ID >>> " + users.getId());
        System.out.println("Response createdAt >>> " + users.getCreatedAt());
    }
}
