package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bk.girltrollsv.BaseApplication;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.EventBase;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.EventCatalogResponse;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.Paging;
import com.bk.girltrollsv.network.ConfigNetwork;
import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    ArrayList<Feed> initFeeds;
    Paging paging;
    ArrayList<EventBase> eventCatalogs;
    boolean isLoadNewFeedComplete = false;
    boolean isLoadEventCatalogComplete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        new TaskLoadInitData().execute();
        new TaskLoadFacebookSDK().execute();
        // load all thing need
    }

    public void loadInitNewFeed() {

        initFeeds = new ArrayList<>();
        paging = new Paging();

        // load new feed
        Map<String, String> tagLoadNewFeed = new HashMap<>();
        tagLoadNewFeed.put(AppConstant.CURRENT_FEED_ID_TAG, String.valueOf(AppConstant.DEFAULT_FEED_ID));
        tagLoadNewFeed.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));

        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callLoadNewFeed(tagLoadNewFeed);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                if (response.isSuccessful()) {

                    FeedResponse body = response.body();
                    if (body != null && body.getSuccess() == AppConstant.SUCCESS) {

                        if (body.getData() != null && body.getData().size() > 0) {
                            initFeeds.addAll(body.getData());
                        }
                        if (body.getPaging() != null) {
                            paging.setBefore(body.getPaging().getBefore());
                            paging.setAfter(body.getPaging().getAfter());
                        }
                        isLoadNewFeedComplete = true;
                        handleLaunchingMainActivity();
                    }
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                t.printStackTrace();
                isLoadNewFeedComplete = true;
                handleLaunchingMainActivity();
                // show error
            }
        });
    }

    public void loadEventCatalog() {

        eventCatalogs = new ArrayList<>();
        Call<EventCatalogResponse> call = ConfigNetwork.getServerAPI().callLoadEventCatalog();
        call.enqueue(new Callback<EventCatalogResponse>() {
            @Override
            public void onResponse(Call<EventCatalogResponse> call, Response<EventCatalogResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    EventCatalogResponse body = response.body();
                    if (body.getSuccess() == AppConstant.SUCCESS
                            && body.getData() != null
                            && body.getData().size() > 0) {

                        eventCatalogs.addAll(body.getData());
                        isLoadEventCatalogComplete = true;
                        handleLaunchingMainActivity();
                    }
                }
            }

            @Override
            public void onFailure(Call<EventCatalogResponse> call, Throwable t) {
                t.printStackTrace();
                isLoadEventCatalogComplete = true;
                handleLaunchingMainActivity();
                // show error
            }
        });
    }

    public void handleLaunchingMainActivity() {

        if( isLoadEventCatalogComplete) {

            Intent intent = new Intent(this, MainActivity.class);
            Bundle dataToMain = new Bundle();

            dataToMain.putParcelable(AppConstant.PAGING_TAG, paging);
            dataToMain.putParcelableArrayList(AppConstant.EVENT_CATALOG_TAG, eventCatalogs);
            dataToMain.putParcelableArrayList(AppConstant.FEEDS_TAG, initFeeds);

            intent.putExtra(AppConstant.PACKAGE, dataToMain);
            startActivity(intent);
            finish();

        }
    }

    private class TaskLoadFacebookSDK extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            FacebookSdk.sdkInitialize(BaseApplication.getContext());
            return null;
        }
    }

    private class TaskLoadInitData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            loadInitNewFeed();
            loadEventCatalog();
            return null;
        }
    }
}
