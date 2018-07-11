package ru.innopolis.stc9.service.implementation;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class RestBridge {
    private RestBridge() {
    }

    public static String doWhileGetValidResponse(String url, Object o, Gson gson, boolean isWithObject, int countOfAction, int second) {
        String result = "@ERROR";
        for (int i = 0; i < countOfAction; i++) {
            try {
                result = doConnectAction(url, o, gson, isWithObject);
                break;
            } catch (IOException e) {
                sleepForSecCount(second);
            }
        }
        return result;
    }

    private static String doConnectAction(String url, Object o, Gson gson, boolean isWithObject) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        HttpResponse response;
        if (isWithObject) {
            post.setHeader("Content-type", "application/json; charset=UTF-8");
            post.setEntity(new StringEntity(gson.toJson(o), "UTF-8"));
        }
        response = httpClient.execute(post);
        return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
    }

    private static void sleepForSecCount(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
