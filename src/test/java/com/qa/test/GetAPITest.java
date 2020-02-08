package com.qa.test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase {
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

    @Test (priority = 1)
    public void getTestWithoutHeaders() throws IOException, ClientProtocolException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code ---> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not Matching");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON >>> " + responseJson);
        String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("PPV is " + perPageValue);
        Assert.assertEquals(perPageValue, "6");
        String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
        System.out.println(lastName);
        System.out.println("Response JSON from API >>>> " + responseJson);
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("All headers from response >>> " + allHeaders);
    }


    @Test(priority = 2)
    public void getTestWithHeaders() throws IOException, ClientProtocolException {
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        closeableHttpResponse = restClient.get(url);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code ---> " + statusCode);
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not Matching");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON >>> " + responseJson);
        String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
        System.out.println("PPV is " + perPageValue);
        Assert.assertEquals(perPageValue, "6");
        String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
        System.out.println(lastName);
        System.out.println("Response JSON from API >>>> " + responseJson);
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("All headers from response >>> " + allHeaders);
    }

}
