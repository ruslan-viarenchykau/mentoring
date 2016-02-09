package com.gmail.isheremetov.jmp.coaching.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RestUtils {

    private final static Gson GSON = new Gson();
    private final static Type TYPE_LIST = new TypeToken<ArrayList<String>>() {}.getType();

    public final static String HOST = "http://epbygomw0076:8080/";
    public final static String KEYS = "keys/";
    public final static String LENGTH = "length";

    public static String getStringFromUrl(String url) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(url);
        String output = null;
        try {
            getRequest.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            output = IOUtils.toString(br);
            IOUtils.closeQuietly(br);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String postStringToUrl(String url, String body) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(url);
        String output = null;
        try {
            postRequest.addHeader("accept", "application/json");
            HttpEntity entity = new ByteArrayEntity(body.getBytes());
            postRequest.setEntity(entity);
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            output = IOUtils.toString(br);
            IOUtils.closeQuietly(br);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static List<String> getListFromUrl(String url) {
        return GSON.fromJson(getStringFromUrl(url), TYPE_LIST);
    }

}
