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
public class FourImageFeedViewHolder extends FeedViewHolder {

    ImageView[] imgItems;
    GridLayout gridContent;

    public FourImageFeedViewHolder(View itemView, Activity activity) {
        super(itemView, activity);
        initLayoutContent();
        frameFeedContent.addView(gridContent);
    }

    public void initLayoutContent() {

        int screenWidth = getScreenWidth();
        int halfScreenWidth = screenWidth / 2;
        int totalImage = 4;
        int totalRow = 2;

        gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(totalRow);
        gridContent.setColumnCount(2);

        int rowOrder;
        imgItems = new ImageView[totalImage];

        for (int i = 0; i < totalImage; i++) {

            rowOrder = i / 2;
            if (i % 2 == 0) {
                imgItems[i] = setImageViewLeft(gridContent, rowOrder, halfScreenWidth, halfScreenWidth);
            } else {
                imgItems[i] = setImageViewRight(gridContent, rowOrder, halfScreenWidth, halfScreenWidth);
            }
        }
    }

    public void populate(Feed feed) {

        super.populate(feed);

        String urlThumbnail1 = feed.getImages().get(0).getUrlImageThumbnail();
        String urlThumbnail2 = feed.getImages().get(1).getUrlImageThumbnail();
        String urlThumbnail3 = feed.getImages().get(2).getUrlImageThumbnail();
        String urlThumbnail4 = feed.getImages().get(3).getUrlImageThumbnail();

        LoadUtil.loadImageResize(urlThumbnail1, imgItems[0], imgItems[0].getLayoutParams().width, imgItems[0].getLayoutParams().height);
        LoadUtil.loadImageResize(urlThumbnail2, imgItems[1], imgItems[1].getLayoutParams().width, imgItems[1].getLayoutParams().height);
        LoadUtil.loadImageResize(urlThumbnail3, imgItems[2], imgItems[2].getLayoutParams().width, imgItems[2].getLayoutParams().height);
        LoadUtil.loadImageResize(urlThumbnail4, imgItems[3], imgItems[3].getLayoutParams().width, imgItems[3].getLayoutParams().height);
    }
}
