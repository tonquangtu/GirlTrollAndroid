package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bk.girltrollsv.model.PostedFeed;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

/**
 * Created by Dell on 02-Dec-16.
 */
public class TwoImagePostedFeedVH extends PostedFeedViewHolder {

    GridLayout gridContent;
    ImageView imgItemLeft;
    ImageView imgItemRight;


    public TwoImagePostedFeedVH(View itemView, Activity activity) {
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
        final ImageView [] items = new ImageView[2];
        items[0] = imgItemLeft;
        items[1] = imgItemRight;
        imgItemLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickImage(getLayoutPosition(), 0, items);
                }
            }
        });

        imgItemRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickImage(getLayoutPosition(), 1, items);
                }
            }
        });


    }

    public void populatePost(PostedFeed feed) {

        super.populatePost(feed);

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
