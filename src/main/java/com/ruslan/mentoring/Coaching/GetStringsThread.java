package com.ruslan.mentoring.Coaching;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class GetStringsThread implements Runnable {
    private String key;
    private ArrayBlockingQueue<Entry> queue;

    public GetStringsThread(String key, ArrayBlockingQueue<Entry> queue) {
        this.key = key;
        this.queue = queue;
    }

    public void run() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://epbygomw0076.gomel.epam.com:8080/keys/" + key);

        try (CloseableHttpResponse response = httpclient.execute(httpget)) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            String jsonString = sb.toString();

            List<String> strings = new Gson().fromJson(jsonString, List.class);
            queue.put(new Entry(key, strings));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}