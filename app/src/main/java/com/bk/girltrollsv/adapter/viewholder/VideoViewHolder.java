package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

/**
 * Created by Dell on 23-Aug-16.
 */
public class VideoViewHolder extends FeedViewHolder {

    ImageView imgThumbnailVideo;

    ImageView imgBtnPlay;


    public VideoViewHolder(View itemView, Activity activity) {
        super(itemView, activity);
        initLayoutContent();
    }

    public void initLayoutContent() {

        int screenWidth = getScreenWidth() - margin * 2;
        int height = 9* screenWidth / 16 - margin * 2;
        RelativeLayout rlContainer = new RelativeLayout(mActivity);

        RelativeLayout.LayoutParams layoutParamsThumbnailVideo = new RelativeLayout.LayoutParams(screenWidth, height);
        layoutParamsThumbnailVideo.setMargins(margin, margin, margin, margin);
        layoutParamsThumbnailVideo.addRule(RelativeLayout.CENTER_IN_PARENT);
        imgThumbnailVideo = new ImageView(mActivity);
        imgThumbnailVideo.setBackgroundColor(Color.BLACK);
        imgThumbnailVideo.setLayoutParams(layoutParamsThumbnailVideo);
        rlContainer.addView(imgThumbnailVideo);

        RelativeLayout.LayoutParams layoutParamsPlay = new RelativeLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParamsPlay.addRule(RelativeLayout.CENTER_IN_PARENT);

        imgBtnPlay = new ImageView(mActivity);
        imgBtnPlay.setLayoutParams(layoutParamsPlay);
        imgBtnPlay.setImageResource(R.drawable.icon_play);
        rlContainer.addView(imgBtnPlay);

        frameFeedContent.addView(rlContainer);

        rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickVideo(getLayoutPosition(), v);
                }
            }
        });

    }


    public void populate(Feed feed) {

        super.populate(feed);

        String thumbnailUrl = feed.getVideo().getUrlVideoThumbnail();
        int height = imgThumbnailVideo.getLayoutParams().height;
        int width = imgThumbnailVideo.getLayoutParams().width;
        LoadUtil.loadImageResize(thumbnailUrl, imgThumbnailVideo, width, height);
    }


}
