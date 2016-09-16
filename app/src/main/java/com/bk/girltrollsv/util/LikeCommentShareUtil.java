package com.bk.girltrollsv.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.MyResponse;
import com.bk.girltrollsv.network.ConfigNetwork;
import com.bk.girltrollsv.ui.activity.CommentActivity;
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

    public static void handleClickLike(Activity activity, Feed feed, View viewLike, TextView txtNumLike, int idUnLikeIcon) {

        String accountId = AccountUtil.getAccountId();
        if (accountId != null) {

            changeLikeState(activity, feed, accountId, viewLike, txtNumLike, idUnLikeIcon);
        } else {

            // open activity to login
        }
        changeLikeState(activity, feed, accountId, viewLike, txtNumLike, idUnLikeIcon);
    }

    public static void changeLikeState(Activity activity, Feed feed, String accountId,
                                       View viewLike, TextView txtNumLike, int idUnLikeIcon) {

        ImageView imgLike = (ImageView)viewLike;
        if (feed.getIsLike() == AppConstant.UN_LIKE) {
            feed.setLikeState(AppConstant.LIKE);
            feed.setNumLike(feed.getLike() + 1);
            imgLike.setImageResource(R.drawable.icon_like);

        } else {
            feed.setLikeState(AppConstant.UN_LIKE);
            feed.setNumLike(feed.getLike() - 1);
            imgLike.setImageResource(idUnLikeIcon);
        }
        String likeLine = feed.getLike() + AppConstant.SPACE + activity.getResources().getString(R.string.base_like);
        StringUtil.displayText(likeLine, txtNumLike);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.scale_like);
        imgLike.startAnimation(animation);

        Map<String, String> tag = new HashMap<>();
        tag.put(AppConstant.MEMBER_ID, accountId);
        tag.put(AppConstant.FEED_ID, feed.getFeedId());
        tag.put(AppConstant.TYPE, feed.getIsLike() + "");
        Call<MyResponse> call = ConfigNetwork.getServerAPI().callLike(tag);
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

    public static void handleClickComment(Activity activity, Feed feed) {

        if (feed != null) {

            Bundle data = new Bundle();
            data.putString(AppConstant.FEED_ID_TAG, feed.getFeedId());
            Intent intent = new Intent(activity, CommentActivity.class);
            intent.putExtra(AppConstant.PACKAGE, data);
            activity.startActivity(intent);
        }
    }

    public static ShareLinkContent getShareContent(Activity activity, Feed feed) {
        String title = feed.getTitle();
        String description = activity.getResources().getString(R.string.share_description);
        String url;

        if(feed.getImages() != null && feed.getImages().size() > 0) {
            url = feed.getImages().get(0).getUrlImage();
        } else if(feed.getVideo() != null) {
            url = feed.getVideo().getUrlVideo();
        } else {
            url = AppConstant.URL_BASE;
        }

        Uri uri = Uri.parse(url);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(description)
                .setContentUrl(uri)
                .build();

        return linkContent;
    }
}
