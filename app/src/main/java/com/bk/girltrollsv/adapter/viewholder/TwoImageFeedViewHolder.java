package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

/**
 * Created by Dell on 23-Aug-16.
 */
public class TwoImageFeedViewHolder extends FeedViewHolder {

    GridLayout gridContent;
    ImageView imgItemLeft;
    ImageView imgItemRight;


    public TwoImageFeedViewHolder(View itemView, Activity activity) {
        super(itemView, activity);

        initLayoutContent();
        frameFeedContent.addView(gridContent, 0);
    }

    public void initLayoutContent() {

        int screenWidth = getScreenWidth();
        int haftScreenWidth = screenWidth / 2;

        gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(1);
        gridContent.setColumnCount(2);

        imgItemLeft = setImageViewLeft(gridContent, 0, screenWidth, haftScreenWidth);
        imgItemRight = setImageViewRight(gridContent, 0, screenWidth, haftScreenWidth);
    }

    public void populate(Feed feed) {

        super.populate(feed);

        String urlImageThumbnailLeft = feed.getImages().get(0).getUrlImageThumbnail();
        String urlImageThumbnailRight = feed.getImages().get(1).getUrlImageThumbnail();

        ViewGroup.LayoutParams layoutParamsLeft = imgItemLeft.getLayoutParams();
        ViewGroup.LayoutParams layoutParamsRight = imgItemRight.getLayoutParams();

        LoadUtil.loadImageResize(urlImageThumbnailLeft, imgItemLeft,
                layoutParamsLeft.width, layoutParamsLeft.height);

        LoadUtil.loadImageResize(urlImageThumbnailRight, imgItemRight,
                layoutParamsRight.width, layoutParamsRight.height);
    }
}
