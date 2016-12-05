package com.bk.girltrollsv.networkconfig;

import com.bk.girltrollsv.constant.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 21-Aug-16.
 */
public class ConfigNetwork {

    private static Gson gson = new GsonBuilder().setLenient().create();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(AppConstant.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static ServiceAPI serviceAPI = builder.client(httpClient.build()).build().create(ServiceAPI.class);

//    public static ServiceAPI serviceAPI = builder.client(getRequestHeader()).build().create(ServiceAPI.class);

    private static  OkHttpClient getRequestHeader() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.readTimeout(200, TimeUnit.SECONDS);
        return httpClient.build();
    }

}
