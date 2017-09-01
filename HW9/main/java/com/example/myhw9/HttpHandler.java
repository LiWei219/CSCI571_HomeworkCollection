package com.example.myhw9;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/21.
 */

/*

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public static String datastr;

    public String makeServiceCall(){
//    public void makeServiceCall(){
        //create url
      //  OkHttpClient client = new OkHttpClient.Builder().readTimeout(8, TimeUnit.SECONDS).build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(10,TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://sample-env.dinpjbwdem.us-west-2.elasticbeanstalk.com").newBuilder();
        urlBuilder.addQueryParameter("search", "info");
        urlBuilder.addQueryParameter("key", MainActivity.key);
        urlBuilder.addQueryParameter("type", "user");
        String path = urlBuilder.build().toString();

        Request request = new Request.Builder().url(path).build();
        //asynchronize method

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e("TAG",Thread.currentThread().getName() + "结果 " + e.toString());
            }

            public void onResponse(Call call,final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                 // do something wih the result
                    datastr = response.body().string();
                }
            }

        });
     //   String test="got it!";
    //    while(datastr.equals("")){}
        return datastr;
    }
}   */

public class HttpHandler {
    private OkHttpClient client;
    private Request.Builder builder;
    public static String jsonStr;

    public static void get(String url){
        call(url);
    }

    private static void call(String url){
        OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(10,TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {

            Handler mainHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(Call call,final IOException e) {
                //失败
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG",Thread.currentThread().getName() + "结果 " + e.toString());
                    }
                });
            }
            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isSuccessful()) {
                            try{
                                throw new IOException("Unexpected code " + response);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        } else {
                            // do something wih the result
                            try{
                                jsonStr = response.body().string();
                                String test = "got it";
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        });
    }

}
