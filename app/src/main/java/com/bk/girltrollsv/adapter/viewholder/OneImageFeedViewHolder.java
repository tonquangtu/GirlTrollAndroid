package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

/**
 * Created by Dell on 23-Aug-16.
 */
public class OneImageFeedViewHolder extends FeedViewHolder {

    ImageView imgItem;
    GridLayout gridContent;

    public OneImageFeedViewHolder(View itemView, Activity activity) {

        super(itemView, activity);
        initLayoutContent();
        frameFeedContent.addView(gridContent, 0);

    }

    public void initLayoutContent() {

        int screenWidth = getScreenWidth();
        gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(1);
        gridContent.setColumnCount(2);
        imgItem = setImageViewFullScreenWidth(gridContent, 0, screenWidth, screenWidth);
        final ImageView [] items = new ImageView[1];
        items[0] = imgItem;
        imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickImage(getLayoutPosition(), 0, items);
                }
            }
        });
    }

    public void populate(Feed feed) {
        super.populate(feed);

        String urlImageThumbnail = feed.getImages().get(0).getUrlImageThumbnail();
        LoadUtil.loadImageResize(urlImageThumbnail, imgItem, imgItem.getLayoutParams().width, imgItem.getLayoutParams().height);
    }
}
