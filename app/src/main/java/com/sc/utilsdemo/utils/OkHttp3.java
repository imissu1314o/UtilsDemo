package com.sc.utilsdemo.utils;

/**
 * Created by Admin on 2019/3/23.
 */


import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp3 {

    private OkHttpClient client;
    private static volatile OkHttp3 instance;
    private static Request request;
    private static OkHttpClient okHttpClient;
    private static Handler handler;

    //l拦截器
    private Interceptor getAppInterceptor(){
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("++++++", "拦截前: ");
                Response response = chain.proceed(request);
                Log.i("++++++", "拦截后: ");
                return response;
            }
        };
        return interceptor;
    }

    //构造方法https://blog.csdn.net/weixin_43860775/article/details/85945599
    public OkHttp3() {
        okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(3000,TimeUnit.SECONDS)
                .connectTimeout(3000,TimeUnit.SECONDS)
                .addInterceptor(getAppInterceptor())//添加了拦截器
                .build();
    }
    //单例OkHttp
    public static  OkHttp3 getInstance(){
        if (instance==null){
            synchronized (OkHttp3.class){
                if (instance==null){
                    instance=new OkHttp3();
                }
            }
        }
        return instance;
    }

    public static void doGet(String url, final NetCallBack netCallBack){
        handler = new Handler();
        okHttpClient = new OkHttpClient();
        request = new Request.Builder().url(url).method("GET", null).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netCallBack.onSuccess(string);
                    }
                });
            }
        });
    }

    public interface NetCallBack {
        void onSuccess(String o);
    }
}

