package com.bk.girltrollsv.networkconfig;

import com.bk.girltrollsv.model.dataserver.EventCatalogResponse;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.LoginResponse;
import com.bk.girltrollsv.model.dataserver.MyResponse;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by Dell on 21-Aug-16.
 */
public interface ServiceAPI {

    @POST("login/facebook")
    Call<LoginResponse> callFacebookLogin(@QueryMap Map<String, String> tag);

    @POST("login/normal")
    Call<LoginResponse> callNormalLogin(@QueryMap Map<String, String> tag);

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

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadPhoto(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);


}
