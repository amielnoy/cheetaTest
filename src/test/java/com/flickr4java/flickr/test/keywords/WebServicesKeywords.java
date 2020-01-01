package com.flickr4java.flickr.test.keywords;

import io.qameta.allure.Step;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServicesKeywords {
    @Step("generate Ok Http REquest")
    public static Request generateOkHttpRequest() {
        Request request = new Request.Builder()
                .url("https://www.flickr.com/services/rest/?method=flickr.people.getUploadStatus&api_key=0b8a8ec5f0c6885115675594178fb8b1&format=json&nojsoncallback=1&auth_token=72157712433917248-e67e658cd7cb4f74&api_sig=39bd43bc8d2c7a9862aae414944a82fc")
                .method("GET", null)
                .addHeader("User-Agent", "PostmanRuntime/7.21.0")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "www.flickr.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Cookie", "xb=827096; localization=en-us%3Bil%3Bil; flrbp=1577705743-3e206ea2c3373dfc0b75e550dfc5ae697b7ebd04; flrbs=1577705743-3e248fc325a7145f29c0a8ddc18171e62c07a4d1; flrbgrp=1577705743-6521baaccf37668a673a31ccdf080a0fbf4386f7; flrbgdrp=1577705743-d73d10c2f0405b51d9d47dd94278c3096c58eee6; flrbgmrp=1577705743-4014e1110447426812cce9dda08f34768d70f546; flrbrst=1577705743-9816d35df83f2ebc458b62588d4bf93ec3700002; flrtags=1577705743-5b5cdcffe15171a8d1092b28532231d87d4426c5; flrbrp=1577705743-c32db2825d8b1e7d25ac4495ef5b6a94a4e93534; flrbrgs=1577705743-a039f2657a9e2e4f1769117d7a8b7a16adb0e0b0")
                .addHeader("Connection", "keep-alive")
                .build();
        return request;

    }

    public static OkHttpClient getHttpClient() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        return client;
    }

    public static String getHttpResponse(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("");
            return "";
        }
    }
}
