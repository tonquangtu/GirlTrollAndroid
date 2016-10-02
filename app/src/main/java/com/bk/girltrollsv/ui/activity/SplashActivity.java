package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.bk.girltrollsv.BaseApplication;
import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.databasehelper.DatabaseUtil;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
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
    ProgressBar pgbLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_splash);
        pgbLoader = (ProgressBar) findViewById(R.id.pgb_loader);
        pgbLoader.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);

        new TaskLoadInitData().execute();
        new TaskLoadFacebookSDK().execute();
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

            handleLoadNewFeed();
            return null;
        }
    }

    public void handleLoadNewFeed() {
//        int countImage = DatabaseUtil.getCountImage();
//        int countVideo = DatabaseUtil.getCountVideo();
//        int countFeed = DatabaseUtil.getCountFeed();
//        Log.e("tuton", "countImage:" + countImage);
//        Log.e("tuton", "countVideo:" + countVideo);
//        Log.e("tuton", "countFeed:" + countFeed);
        initFeeds = new ArrayList<>();
        ArrayList<Feed> feeds = DatabaseUtil.getFeedsOffset(AppConstant.LIMIT_FEED_LOCAL, 0);
        if (feeds != null && feeds.size() > 0) {
            initFeeds.addAll(feeds);
            handleLaunchingMainActivity();
        } else {
            loadFeedFromRemote();
        }


    }

    public void loadFeedFromRemote() {

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
                        new TaskInsertFeeds().execute(initFeeds);
                        handleLaunchingMainActivity();
                    }
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                t.printStackTrace();
                handleLaunchingMainActivity();
                // show error
            }
        });
    }

    public void handleLaunchingMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        Bundle dataToMain = new Bundle();
        dataToMain.putParcelableArrayList(AppConstant.FEEDS_TAG, initFeeds);

        intent.putExtra(AppConstant.PACKAGE, dataToMain);
        startActivity(intent);
        finish();
    }

    public class TaskInsertFeeds extends AsyncTask<ArrayList<Feed>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<Feed>... params) {
            DatabaseUtil.insertNewFeeds(params[0]);
            return null;
        }
    }

}
