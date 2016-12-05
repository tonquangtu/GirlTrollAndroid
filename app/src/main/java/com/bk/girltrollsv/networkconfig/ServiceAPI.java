package com.bk.girltrollsv.networkconfig;

import com.bk.girltrollsv.model.dataserver.EventCatalogResponse;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.LoginResponse;
import com.bk.girltrollsv.model.dataserver.MyResponse;
import com.bk.girltrollsv.model.dataserver.ServerResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
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

    @POST("feed")
    Call<MyResponse> testUpload(@QueryMap Map<String, String> tag);

    @Multipart
    @POST("feed")
    Call<ServerResponse> uploadSimplePhoto(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );


    @Multipart
    @POST("feed")
    Call<MyResponse> uploadSimplePhoto(@Part("memberId") RequestBody memberId,
                                          @Part("title") RequestBody title,
                                          @Part("school") RequestBody school,
                                          @Part("type") RequestBody type,
                                          @Part("totalFile") RequestBody totalFile,
                                          @Part MultipartBody.Part file);


    @Multipart
    @POST("feed")
    Call<MyResponse> uploadSimplePhoto(@Part("memberId") RequestBody memberId,
                                       @Part("title") RequestBody title,
                                       @Part("school") RequestBody school,
                                       @Part("type") RequestBody type,
                                       @Part("totalFile") RequestBody totalFile,
                                       @Part("file_0") RequestBody file1);


    @POST("feed")
    Call<MyResponse> uploadSimplePhoto(@Query("memberId") String memberId,
                                       @Query("title") String title,
                                       @Query("school") String school,
                                       @Query("type") String type,
                                       @Query("totalFile") String totalFile,
                                       @Body RequestBody file1);


    @Multipart
    @POST("feed")
    Call<MyResponse> uploadSimplePhoto(@Part("memberId") RequestBody memberId,
                                          @Part("title") RequestBody title,
                                          @Part("school") RequestBody school,
                                          @Part("type") RequestBody type,
                                          @Part("totalFile") RequestBody totalFile,
                                          @Part MultipartBody.Part file1,
                                          @Part MultipartBody.Part file2);


    @Multipart
    @POST("feed")
    Call<MyResponse> uploadSimplePhoto(@Part("memberId") RequestBody memberId,
                                            @Part("title") RequestBody title,
                                            @Part("school") RequestBody school,
                                            @Part("type") RequestBody type,
                                            @Part("totalFile") RequestBody totalFile,
                                            @Part MultipartBody.Part file1,
                                            @Part MultipartBody.Part file2,
                                            @Part MultipartBody.Part file3);

    @Multipart
    @POST("feed")
    Call<MyResponse> uploadSimplePhoto(@Part("memberId") RequestBody memberId,
                                            @Part("title") RequestBody title,
                                            @Part("school") RequestBody school,
                                            @Part("type") RequestBody type,
                                            @Part("totalFile") RequestBody totalFile,
                                            @Part MultipartBody.Part file1,
                                            @Part MultipartBody.Part file2,
                                            @Part MultipartBody.Part file3,
                                            @Part MultipartBody.Part file4);


    @Multipart
    @POST("feed")
    Call<MyResponse> uploadOnePhoto(@Part("memberId") RequestBody memberId,
                                    @Part("title") RequestBody title,
                                    @Part("school") RequestBody school,
                                    @Part("linkFace") RequestBody linkFace,
                                    @Part("type") RequestBody type,
                                    @Part("totalFile") RequestBody totalFile,
                                    @Part MultipartBody.Part file);


    @GET("feed/hot")
    Call<FeedResponse> callLoadHot(@QueryMap Map<String, String> tag);
}
