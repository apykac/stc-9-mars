package ru.innopolis.stc9.service.implementation;

import com.google.gson.Gson;
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

    public static String doConnectAction(String url, Object o, Gson gson, boolean isWithObject) {
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
}
