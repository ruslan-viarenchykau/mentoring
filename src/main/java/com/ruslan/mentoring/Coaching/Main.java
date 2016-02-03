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
import java.util.concurrent.*;

public class Main {
    private static ArrayBlockingQueue<Entry> queue;

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> keys = getKeys();
        queue = new ArrayBlockingQueue<>(keys.size());

        ExecutorService executorService = Executors.newFixedThreadPool(keys.size());
        for (String key: keys) {
            executorService.execute(new GetStringsThread(key, queue));
        }

        int counter = 0;
        while (counter++ < keys.size()) {
            Entry entry = queue.take();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());

        }
        executorService.shutdown();
        // check results
    }

    private static List<String> getKeys() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://epbygomw0076.gomel.epam.com:8080/keys");
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            String jsonString = sb.toString();

            return new Gson().fromJson(jsonString, List.class);

        } finally {
            response.close();
        }
    }
}
