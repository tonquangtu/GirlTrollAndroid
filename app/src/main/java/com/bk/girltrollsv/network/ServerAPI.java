package com.bk.girltrollsv.network;

import com.bk.girltrollsv.model.dataserver.EventCatalogResponse;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Dell on 21-Aug-16.
 */
public interface ServerAPI {

    @POST("login")
    Call<LoginResponse> callLogin(@QueryMap Map<String, String> tag);

    @GET("feed/new")
    Call<FeedResponse> callLoadNewFeed(@QueryMap Map<String, String> tag);

    @POST("feed/top")
    Call<FeedResponse> callLoadTopFeed(@QueryMap Map<String, String> tag);

    @GET("event")
    Call<EventCatalogResponse> callLoadEventCatalog();


}