package com.bk.girltrollsv.network;

import com.bk.girltrollsv.model.dataserver.EventCatalogResponse;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.LoginResponse;
import com.bk.girltrollsv.model.dataserver.MyResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Dell on 21-Aug-16.
 */
public interface ServerAPI {

    @POST("login/facebook")
    Call<LoginResponse> callFacebookLogin(@QueryMap Map<String, String> tag);

    @GET("feed/new")
    Call<FeedResponse> callLoadNewFeed(@QueryMap Map<String, String> tag);

    @POST("feed/top")
    Call<FeedResponse> callLoadTopFeed(@QueryMap Map<String, String> tag);

    @GET("event")
    Call<EventCatalogResponse> callLoadEventCatalog();

    @GET("feed/refresh")
    Call<FeedResponse> callRefreshNewFeed(@QueryMap Map<String, String> tag);

    @GET("feed/like")
    Call<MyResponse> callLike(@QueryMap Map<String, String> tag);


}
