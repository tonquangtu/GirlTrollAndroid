package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.model.Video;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 18-Aug-16.
 */
public class FeedViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.cir_img_member_feed)
    CircleImageView cirImgMemberFeed;

    @Bind(R.id.txt_member_name)
    TextView txtMemberName;

    @Bind(R.id.txt_time_feed)
    TextView txtTimeFeed;

    @Bind(R.id.img_more)
    ImageView imgMore;

    @Bind(R.id.txt_title_feed)
    TextView txtTitleFeed;

    @Bind(R.id.frame_container)
    FrameLayout frameFeedContent;

    Activity mActivity;
    int margin = 4;


    public FeedViewHolder(View itemView, Activity activity) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mActivity = activity;
    }

    public void populate(Feed feed) {


    }

    public ViewGroup getLayoutContent(Feed feed) {

        if (feed.getImageInfos() != null && feed.getImageInfos().size() > 0) {
            return getImageLayoutContent(feed.getImageInfos());

        } else if (feed.getVideo() != null) {
            return getVideoLayoutContent(feed.getVideo());
        }
        return null;
    }

    public ViewGroup getImageLayoutContent(ArrayList<ImageInfo> imageInfos) {

        int size = imageInfos.size();

        if (size == 1) {
            return getImageLayoutHaveOddImage(imageInfos);
        } else if (size == 2) {
            return getLayoutHaveTwoImage(imageInfos);
        } else if (size % 2 == 0) {
            return getImageLayoutHaveEvenImage(imageInfos);
        } else {
            return getImageLayoutHaveOddImage(imageInfos);
        }

    }

    public ViewGroup getVideoLayoutContent(Video video) {
        return null;

    }

    public ViewGroup getImageLayoutHaveOddImage(ArrayList<ImageInfo> imageInfos) {

        int screenWidth = getScreenWidth();
        int halfScreenWidth = screenWidth / 2;
        int size = imageInfos.size();
        int totalRow = size / 2 + 1;

        GridLayout gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(totalRow);
        gridContent.setColumnCount(2);

        int rowOrder = 0;
        ImageView[] imgItems = new ImageView[size];
        imgItems[0] = setImageViewFullScreenWidth(gridContent, imageInfos.get(0).getUrlImage(),
                rowOrder, halfScreenWidth, screenWidth);


        for (int i = 1; i < size; i++) {

            if (i % 2 == 0) {
                rowOrder = i / 2;
                imgItems[i] = setImageViewRight(gridContent, imageInfos.get(i).getUrlImage(),
                        rowOrder, halfScreenWidth, halfScreenWidth);
            } else {
                rowOrder = i / 2 + 1;
                imgItems[i] = setImageViewLeft(gridContent, imageInfos.get(i).getUrlImage(),
                        rowOrder, halfScreenWidth, halfScreenWidth);
            }
        }

        return gridContent;
    }

    public ViewGroup getImageLayoutHaveEvenImage(ArrayList<ImageInfo> imageInfos) {

        int screenWidth = getScreenWidth();
        int halfScreenWidth = screenWidth / 2;
        int size = imageInfos.size();
        int totalRow = size / 2;

        GridLayout gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(totalRow);
        gridContent.setColumnCount(2);

        int rowOrder;
        ImageView[] imgItems = new ImageView[size];

        for (int i = 0; i < size; i++) {

            if (i % 2 == 0) {
                rowOrder = i / 2;
                imgItems[i] = setImageViewRight(gridContent, imageInfos.get(i).getUrlImage(),
                        rowOrder, halfScreenWidth, halfScreenWidth);
            } else {
                rowOrder = i / 2 + 1;
                imgItems[i] = setImageViewLeft(gridContent, imageInfos.get(i).getUrlImage(),
                        rowOrder, halfScreenWidth, halfScreenWidth);
            }
        }

        return gridContent;

    }

    public GridLayout getLayoutHaveOneImage(ArrayList<ImageInfo> imageInfos) {

        int screenWidth = getScreenWidth();
        GridLayout gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(1);
        gridContent.setColumnCount(2);

        ImageView imgItem = setImageViewFullScreenWidth(gridContent, imageInfos.get(0).getUrlImage(),
                0, screenWidth, screenWidth);

        return gridContent;
    }

    public GridLayout getLayoutHaveTwoImage(ArrayList<ImageInfo> imageInfos) {

        int screenWidth = getScreenWidth();
        int haftScreenWidth = screenWidth / 2;

        GridLayout gridContent = new GridLayout(mActivity);
        gridContent.setRowCount(1);
        gridContent.setColumnCount(2);

        ImageView imgItem1 = setImageViewLeft(gridContent, imageInfos.get(0).getUrlImage(),
                0, screenWidth, haftScreenWidth);
        ImageView imgItem2 = setImageViewRight(gridContent, imageInfos.get(1).getUrlImage(),
                0, screenWidth, haftScreenWidth);

        return gridContent;
    }


    public ImageView setImageViewLeft(GridLayout gridContent, String urlImage,
                                      int rowCount, int height, int width) {

        GridLayout.Spec row = GridLayout.spec(rowCount);
        GridLayout.Spec col = GridLayout.spec(0);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);
        layoutParams.setMargins(margin, margin, margin / 2, 0);
        layoutParams.width = width - margin - margin / 2;
        layoutParams.height = height - margin;

        ImageView imgItem = new ImageView(mActivity);
        imgItem.setLayoutParams(layoutParams);
        LoadUtil.loadImageResize(urlImage, imgItem, layoutParams.width, layoutParams.height);

        gridContent.addView(imgItem, layoutParams);
        return imgItem;
    }


    public ImageView setImageViewRight(GridLayout gridContent, String urlImage,
                                       int rowCount, int height, int width) {

        GridLayout.Spec row = GridLayout.spec(rowCount);
        GridLayout.Spec col = GridLayout.spec(1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);
        layoutParams.setMargins(margin / 2, margin, margin, 0);
        layoutParams.width = width - margin - margin / 2;
        layoutParams.height = height - margin;

        ImageView imgItem = new ImageView(mActivity);
        imgItem.setLayoutParams(layoutParams);
        LoadUtil.loadImageResize(urlImage, imgItem, layoutParams.width, layoutParams.height);

        gridContent.addView(imgItem, layoutParams);
        return imgItem;
    }

    public ImageView setImageViewFullScreenWidth(GridLayout gridContent, String urlImage,
                                                 int rowCount, int height, int width) {

        GridLayout.Spec row = GridLayout.spec(rowCount);
        GridLayout.Spec col = GridLayout.spec(0, 2);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);
        layoutParams.setMargins(margin, margin, margin, 0);
        layoutParams.width = width - margin - margin;
        layoutParams.height = height - margin;

        ImageView imgItem = new ImageView(mActivity);
        imgItem.setLayoutParams(layoutParams);
        LoadUtil.loadImageResize(urlImage, imgItem, layoutParams.width, layoutParams.height);

        gridContent.addView(imgItem, layoutParams);
        return imgItem;

    }


    public int getScreenWidth() {
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

}
