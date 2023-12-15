package com.lym.netty.client;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

public class AsyncHttpHelper {

    private AsyncHttpClient asyncHttpClient;

    private static class Singleton {
        static AsyncHttpHelper INSTANT = new AsyncHttpHelper();
    }

    private AsyncHttpHelper() {
    }

    public static AsyncHttpHelper getInstant() {
        return Singleton.INSTANT;
    }

    public void init(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    public CompletableFuture<Response> executeRequest(Request request) {
        ListenableFuture<Response> future = asyncHttpClient.executeRequest(request);
        return future.toCompletableFuture();
    }
}
