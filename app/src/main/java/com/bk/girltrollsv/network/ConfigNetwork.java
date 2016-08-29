package com.bk.girltrollsv.network;

import com.bk.girltrollsv.constant.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 21-Aug-16.
 */
public class ConfigNetwork {

    private static ServerAPI serverAPI;

    public static ServerAPI getServerAPI() {

        if(serverAPI == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(AppConstant.URL_BASE);
            builder.addConverterFactory(GsonConverterFactory.create(gson));
            Retrofit retrofit = builder.build();
            serverAPI = retrofit.create(ServerAPI.class);
        }
        return serverAPI;
    }
}
