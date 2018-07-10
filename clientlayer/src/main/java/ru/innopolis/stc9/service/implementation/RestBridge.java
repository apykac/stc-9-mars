package ru.innopolis.stc9.service.implementation;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RestBridge {
    private RestBridge() {
    }

    private static String doConnectAction(String url, Object o, Gson gson, boolean isWithObject) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        HttpResponse response;
        try {
            if (isWithObject) {
                post.setHeader("Content-type", "application/json; charset=UTF-8");
                post.setEntity(new StringEntity(gson.toJson(o), "UTF-8"));
            }
            response = httpClient.execute(post);
            return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String doWhileGetValidResponse(String url, Object o, Gson gson, boolean isWithObject, int countOfAction, int second) {
        String result = "@ERROR";
        for (int i = 0; i < countOfAction; i++) {
            try {
                result = doConnectAction(url, o, gson, isWithObject);
                break;
            } catch (JsonSyntaxException gse) {
                try {
                    Thread.sleep(second * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
