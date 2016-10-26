package com.bk.girltrollsv.networkconfig;

import com.bk.girltrollsv.constant.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

}
