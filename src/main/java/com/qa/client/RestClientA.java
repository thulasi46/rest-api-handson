package com.qa.client;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class RestClientA {
    public void get(String url) throws IOException, ClientProtocolException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code ---> " + statusCode);
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API >>>> " + responseJson);
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String, String> allHeaders = new HashMap<String, String>();
        for (Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }
        System.out.println("All headers from response >>> " + allHeaders);
    }
}
