package com.bk.girltrollsv.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.girltrollsv.BaseApplication;
import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.ConfirmDialogListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.databasehelper.DatabaseUtil;
import com.bk.girltrollsv.dialog.ConfirmDialogFragment;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.MyResponse;
import com.bk.girltrollsv.networkconfig.ConfigNetwork;
import com.bk.girltrollsv.ui.activity.CommentActivity;
import com.bk.girltrollsv.ui.activity.LoginActivity;
import com.facebook.share.model.ShareLinkContent;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 09-Sep-16.
 */
public class LikeCommentShareUtil {

    public static void handleClickLike(Activity activity, Feed feed, View viewLike, TextView txtNumLike, int iconUnLike) {

        if (Utils.checkInternetAvailable()) {
            String accountId = AccountUtil.getAccountId();
            if (accountId != null) {
                changeLikeState(activity, feed, accountId, viewLike, txtNumLike, iconUnLike);
            } else {
                keepLikeState(activity, viewLike);
                confirmLaunchingLogin(activity);
            }
        } else {
            keepLikeState(activity, viewLike);
            Utils.toastShort(activity, R.string.no_network);
        }
    }

    public static void changeLikeState(Activity activity, Feed feed, String accountId,
                                       View viewLike, TextView txtNumLike, int iconUnLike) {

        ImageView imgLike = (ImageView) viewLike;
        if (feed.getIsLike() == AppConstant.UN_LIKE) {
            feed.setLikeState(AppConstant.LIKE);
            feed.setNumLike(feed.getLike() + 1);
            imgLike.setImageResource(R.drawable.icon_liked);

        } else {
            feed.setLikeState(AppConstant.UN_LIKE);
            feed.setNumLike(feed.getLike() - 1);
            imgLike.setImageResource(iconUnLike);
        }
        String likeLine = feed.getLike() + AppConstant.SPACE + activity.getResources().getString(R.string.base_like);
        StringUtil.displayText(likeLine, txtNumLike);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale_like);
        imgLike.startAnimation(animation);
        updateLikeInfoLocal(feed, accountId);

        Map<String, String> tag = new HashMap<>();
        tag.put(AppConstant.MEMBER_ID, accountId);
        tag.put(AppConstant.FEED_ID, feed.getFeedId() + "");
        tag.put(AppConstant.TYPE, feed.getIsLike() + "");
        Call<MyResponse> call = ConfigNetwork.serviceAPI.callLike(tag);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                // do something
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                // show error
            }
        });
    }

    private static void updateLikeInfoLocal(final Feed feed,  final String accountId) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                DatabaseUtil.updateNumLike(feed.getFeedId(), feed.getLike());
                int preLikeState = DatabaseUtil.isLikeFeed(feed.getFeedId(), accountId);
                if (preLikeState != -1) {
                    DatabaseUtil.updateLikeState(feed.getIsLike(), feed.getFeedId(), accountId);
                } else {
                    DatabaseUtil.insertLikeState(feed.getIsLike(), feed.getFeedId(), accountId);
                }
                return null;
            }
        }.execute();
    }

    public static void keepLikeState(Activity activity, View viewLike) {

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale_like);
        viewLike.startAnimation(animation);
    }

    private static void confirmLaunchingLogin(final Activity activity) {

        String title = activity.getString(R.string.confirm_login);
        String message = activity.getString(R.string.message_confirm_login);
        ConfirmDialogFragment confirmDialog = ConfirmDialogFragment.newInstance(title, message);
        confirmDialog.setPositiveText(R.string.text_login_normal);

        confirmDialog.setListener(new ConfirmDialogListener() {
            @Override
            public void onPositivePress(DialogInterface dialog, int which) {
                Intent intent = new Intent(activity, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.FLAG_LOGIN_FINISH, AppConstant.FINISH_WHEN_COMPLETE);
                intent.putExtra(AppConstant.PACKAGE, bundle);
                activity.startActivity(intent);
            }

            @Override
            public void onNegativePress(DialogInterface dialog, int which) {

            }
        });
        confirmDialog.show(activity.getFragmentManager(), ConfirmDialogFragment.class.getSimpleName());
    }


    public static void handleClickComment(final Activity activity, final Feed feed, View commentView) {

        if (feed != null) {
            Animation animationComment = AnimationUtils.loadAnimation(BaseApplication.getContext(), R.anim.scale_comment);
            commentView.startAnimation(animationComment);
            Utils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Bundle data = new Bundle();
                    data.putInt(AppConstant.FEED_ID_TAG, feed.getFeedId());
                    Intent intent = new Intent(activity, CommentActivity.class);
                    intent.putExtra(AppConstant.PACKAGE, data);
                    activity.startActivity(intent);
                }
            }, 100);
        }
    }

    public static ShareLinkContent getShareContent(Activity activity, Feed feed) {
        String title = feed.getTitle();
        String description = activity.getResources().getString(R.string.share_description);
        String url;

        if (feed.getImages() != null && feed.getImages().size() > 0) {
            url = feed.getImages().get(0).getUrlImage();
        } else if (feed.getVideo() != null) {
            url = feed.getVideo().getUrlVideo();
        } else {
            url = AppConstant.URL_BASE;
        }

        Uri uri = Uri.parse(url);
        return new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(description)
                .setContentUrl(uri)
                .build();
    }
}
