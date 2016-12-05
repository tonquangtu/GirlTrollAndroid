package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.bk.girltrollsv.model.PostedFeed;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

/**
 * Created by Dell on 02-Dec-16.
 */
public class OneImagePostedFeedVH extends PostedFeedViewHolder {

    ImageView imgItem;
    GridLayout gridContent;

    public OneImagePostedFeedVH(View itemView, Activity activity) {

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

    public void populatePost(PostedFeed feed) {
        super.populatePost(feed);

        String urlImageThumbnail = feed.getImages().get(0).getUrlImageThumbnail();
        LoadUtil.loadImageLocal(urlImageThumbnail, imgItem, imgItem.getLayoutParams().width, imgItem.getLayoutParams().height);




    }
}
