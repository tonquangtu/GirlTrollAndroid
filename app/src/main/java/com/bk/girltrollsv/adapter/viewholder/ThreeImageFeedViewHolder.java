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
public class ThreeImageFeedViewHolder extends FeedViewHolder {

    GridLayout gridContent;
    ImageView [] imgItems;

    public ThreeImageFeedViewHolder(View itemView, Activity activity) {

        super(itemView, activity);
        initLayoutContent();
        frameFeedContent.addView(gridContent, 0);

    }

    public void initLayoutContent() {

        int screenWidth = getScreenWidth();
        int halfScreenWidth = screenWidth / 2;
        int totalRow = 2;

        gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(totalRow);
        gridContent.setColumnCount(2);

        imgItems = new ImageView[3];
        imgItems[0] = setImageViewFullScreenWidth(gridContent,0, halfScreenWidth, screenWidth);
        imgItems[1] = setImageViewLeft(gridContent, 1, halfScreenWidth, halfScreenWidth);
        imgItems[2] = setImageViewRight(gridContent, 1, halfScreenWidth, halfScreenWidth);
    }

    public void populate(Feed feed) {

        super.populate(feed);
        String urlThumbnail1 = feed.getImages().get(0).getUrlImageThumbnail();
        String urlThumbnail2 = feed.getImages().get(1).getUrlImageThumbnail();
        String urlThumbnail3 = feed.getImages().get(2).getUrlImageThumbnail();

        LoadUtil.loadImageResize(urlThumbnail1, imgItems[0], imgItems[0].getLayoutParams().width, imgItems[0].getLayoutParams().height);
        LoadUtil.loadImageResize(urlThumbnail2, imgItems[1], imgItems[1].getLayoutParams().width, imgItems[1].getLayoutParams().height);
        LoadUtil.loadImageResize(urlThumbnail3, imgItems[2], imgItems[2].getLayoutParams().width, imgItems[2].getLayoutParams().height);
    }
}
