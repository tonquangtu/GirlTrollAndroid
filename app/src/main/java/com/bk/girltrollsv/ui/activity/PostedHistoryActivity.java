package com.bk.girltrollsv.ui.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.RvPostedHistoryAdapter;
import com.bk.girltrollsv.callback.FeedItemOnClickListener;
import com.bk.girltrollsv.callback.UploadCallback;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.BaseInfoMember;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.model.PostedFeed;
import com.bk.girltrollsv.util.AccountUtil;
import com.bk.girltrollsv.util.DateUtil;
import com.bk.girltrollsv.util.SpaceItem;
import com.bk.girltrollsv.util.StringUtil;
import com.bk.girltrollsv.util.Utils;
import com.bk.girltrollsv.util.networkutil.UploadUtil;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Dell on 01-Dec-16.
 */
public class PostedHistoryActivity extends BaseActivity {

    public static final int UPLOAD_PHOTO = 1;
    public static final int VIEW_POST_HISTORY = 0;
    private int mFlagOpen = VIEW_POST_HISTORY;
    private ArrayList<String> mPhotoPaths;
    private String mTitlePost;
    private String mSchool;
    private String mAccountId;
    private int mTypePhoto;
    private RvPostedHistoryAdapter mPostedAdapter;
    private ArrayList<PostedFeed> mInitFeeds;
    private PostedFeed mPostedFeed;

    @Bind(R.id.img_back_activity)
    ImageButton mImgBackActivity;

    @Bind(R.id.txt_title_activity)
    TextView mTxtTitleActivity;

    @Bind(R.id.rv_posted_history)
    RecyclerView mRvPostedHistory;


    @Override
    public void handleIntent(Intent intent) {

        Bundle data = intent.getBundleExtra(AppConstant.PACKAGE);
        if (data != null) {

            mFlagOpen = data.getInt(AppConstant.FLAG_OPEN_POSTED_HISTORY);
            if (mFlagOpen == UPLOAD_PHOTO) {
                createPostedFeed(data);
                uploadPhoto();
            }
        }
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_posted_history;
    }

    @Override
    public void initView() {
        StringUtil.displayText(StringUtil.getStringFromRes(R.string.view_history_post), mTxtTitleActivity);
        initPostedHistoryList();
    }

    @Override
    public void initData() {

    }

    public void uploadPhoto() {
        UploadUtil.uploadPhotos(
                mPhotoPaths,
                mAccountId,
                mTitlePost,
                mSchool,
                mTypePhoto,
                mPostedFeed.getPostedCode(),
                new UploadCallback() {
                    @Override
                    public void uploadComplete(int success, String message, long postedCode) {
                        handleUploadCompleted(success, message, postedCode);
                        Utils.toastShort(PostedHistoryActivity.this, "Upload thanh cong : " + success);
                    }

                    @Override
                    public void uploadFail(long postedCode) {
                        handleUploadFail(postedCode);
                        Utils.toastShort(PostedHistoryActivity.this, "Upload that bai");
                    }
                });
    }

    public void initPostedHistoryList() {

        mInitFeeds = new ArrayList<>();
        if (mPostedFeed != null) {
            mInitFeeds.add(mPostedFeed);
        }
        int spaceBetweenItems = 15;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItem spaceItem = new SpaceItem(spaceBetweenItems, SpaceItem.VERTICAL_MARGIN_TOP);
        mRvPostedHistory.setLayoutManager(layoutManager);
        mRvPostedHistory.addItemDecoration(spaceItem);

        mPostedAdapter = new RvPostedHistoryAdapter(this, mRvPostedHistory, mInitFeeds);
        mRvPostedHistory.setAdapter(mPostedAdapter);
        mPostedAdapter.setItemListener(new FeedItemOnClickListener() {
            @Override
            public void onClickImage(int posFeed, int posImage, ImageView[] views) {

            }

            @Override
            public void onClickVideo(int posFeed, View view) {

            }

            @Override
            public void onClickLike(int posFeed, View viewLike, TextView txtNumLike) {

            }

            @Override
            public void onClickComment(int posFeed, View view) {

            }

            @Override
            public void onClickMore(int posFeed, View view) {

            }
        });

    }

    public void createPostedFeed(Bundle data) {

        mPhotoPaths = data.getStringArrayList(AppConstant.PHOTO_PATH_TAG);
        mTitlePost = data.getString(AppConstant.TITLE_UPLOAD_TAG);
        mSchool = data.getString(AppConstant.SCHOOL_TAG);
        mTypePhoto = data.getInt(AppConstant.PHOTO_TYPE_TAG);
        mAccountId = data.getString(AppConstant.ACCOUNT_ID_TAG);
        mPostedFeed = new PostedFeed();

        mPostedFeed.setTitle(mTitlePost);
        mPostedFeed.setTime(DateUtil.getDateString(new Date()));
        mPostedFeed.setIsLike(AppConstant.UN_LIKE);
        mPostedFeed.setNumLike(0);
        mPostedFeed.setComment(0);
        mPostedFeed.setSchool(mSchool);
        mPostedFeed.setVideo(null);
        ImageInfo imageInfo;
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        for (String photoPath : mPhotoPaths) {
            imageInfo = new ImageInfo();
            imageInfo.setType(mTypePhoto);
            imageInfo.setUrlImageThumbnail(photoPath);
            imageInfo.setUrlImage(photoPath);
            imageInfos.add(imageInfo);
            Log.e(AppConstant.LOG_TAG, photoPath); // test
        }
        mPostedFeed.setImages(imageInfos);

        BaseInfoMember member = new BaseInfoMember();
        member.setMemberId(mAccountId);
        member.setUsername(AccountUtil.getUsername());
        member.setAvatarUrl(AccountUtil.getAvatarUrl());
        mPostedFeed.setMember(member);
        mPostedFeed.setUploadState(PostedFeed.UPLOADING);
        mPostedFeed.setPostedCode(System.currentTimeMillis());
    }

    public void handleUploadCompleted(int success, String message, long postedCode) {

        String notificationTitle = "";
        if (success == AppConstant.SUCCESS) {
            mPostedAdapter.updateUploadingPost(postedCode, PostedFeed.UPLOAD_SUCCESS);
            notificationTitle = StringUtil.getStringFromRes(R.string.upload_success_message);
        } else {
            mPostedAdapter.updateUploadingPost(postedCode, PostedFeed.REJECT);
            notificationTitle = StringUtil.getStringFromRes(R.string.upload_reject_message);
        }

        sentUploadNotification(notificationTitle);
    }

    public void handleUploadFail(long postedCode) {

        mPostedAdapter.updateUploadingPost(postedCode, PostedFeed.UPLOAD_FAIL);
        String notificationTitle = StringUtil.getStringFromRes(R.string.upload_fail_message);
        sentUploadNotification(notificationTitle);
    }


    public void sentUploadNotification(String title) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo_small);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentText(StringUtil.getStringFromRes(R.string.touch_view_detail));
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);

        notificationManager.notify(AppConstant.UPLOAD_NOTIFICATION_ID, builder.build());
    }

    @OnClick(R.id.img_back_activity)
    public void back() {
        finish();
    }
}
