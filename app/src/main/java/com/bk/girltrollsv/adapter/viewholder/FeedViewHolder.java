package com.bk.girltrollsv.adapter.viewholder;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.FeedItemOnClickListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.BaseInfoMember;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.util.LikeCommentShareUtil;
import com.bk.girltrollsv.util.StringUtil;
import com.bk.girltrollsv.util.networkutil.LoadUtil;
import com.facebook.share.widget.ShareButton;

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
    ImageButton imgBtnMore;

    @Bind(R.id.txt_title_feed)
    TextView txtTitleFeed;

    @Bind(R.id.txt_school)
    TextView txtSchool;

    @Bind(R.id.txt_num_like)
    TextView txtNumLike;

    @Bind(R.id.txt_num_comment)
    TextView txtNumComment;

    @Bind(R.id.img_btn_like)
    ImageView imgLike;

    @Bind(R.id.share_button)
    ShareButton shareButton;

    @Bind(R.id.img_btn_comment)
    ImageView imgComment;

    @Bind(R.id.frame_container)
    FrameLayout frameFeedContent;

    @Bind(R.id.ll_like)
    LinearLayout llLike;

    @Bind(R.id.ll_comment)
    LinearLayout llComment;

    Activity mActivity;

    FeedItemOnClickListener listener;

    int margin = 4;

    public FeedViewHolder(View itemView, Activity activity) {

        super(itemView);
        ButterKnife.bind(this, itemView);
        mActivity = activity;
        initListen();
    }

    public void populate(Feed feed) {

        BaseInfoMember member = feed.getMember();
        setImageAvatarMember(member.getAvatarUrl());
        String school = mActivity.getResources().getString(R.string.base_school) + feed.getSchool();
        String numLike = feed.getLike() + AppConstant.SPACE + mActivity.getResources().getString(R.string.base_like);
        String numComment = feed.getComment() + AppConstant.SPACE + mActivity.getResources().getString(R.string.base_comment);

        StringUtil.displayText(member.getUsername(), txtMemberName);
        StringUtil.displayText(feed.getTime(), txtTimeFeed);
        StringUtil.displayText(feed.getTitle(), txtTitleFeed);
        StringUtil.displayText(school, txtSchool);
        StringUtil.displayText(numLike, txtNumLike);
        StringUtil.displayText(numComment, txtNumComment);

        if (feed.getIsLike() == AppConstant.UN_LIKE) {
           imgLike.setImageResource(R.drawable.icon_unlike);
        } else {
            imgLike.setImageResource(R.drawable.icon_like);
        }
        shareButton.setShareContent(LikeCommentShareUtil.getShareContent(mActivity, feed));
    }

    public void setImageAvatarMember(String urlAvatarMember) {
        int height = (int) mActivity.getResources().getDimension(R.dimen.height_member_feed);
        int width = (int) mActivity.getResources().getDimension(R.dimen.width_member_feed);
        LoadUtil.loadAvatar(urlAvatarMember, cirImgMemberFeed, width, height);
    }

    public ImageView setImageViewLeft(GridLayout gridContent, int rowCount, int height, int width) {

        GridLayout.Spec row = GridLayout.spec(rowCount);
        GridLayout.Spec col = GridLayout.spec(0);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);
        layoutParams.setMargins(margin, margin, margin / 2, 0);
        layoutParams.width = width - margin - margin / 2;
        layoutParams.height = height - margin;

        ImageView imgItem = new ImageView(mActivity);
        imgItem.setLayoutParams(layoutParams);

        gridContent.addView(imgItem);
        return imgItem;
    }

    public ImageView setImageViewRight(GridLayout gridContent, int rowCount, int height, int width) {

        GridLayout.Spec row = GridLayout.spec(rowCount);
        GridLayout.Spec col = GridLayout.spec(1);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);
        layoutParams.setMargins(margin / 2, margin, margin, 0);
        layoutParams.width = width - margin - margin / 2;
        layoutParams.height = height - margin;

        ImageView imgItem = new ImageView(mActivity);
        imgItem.setLayoutParams(layoutParams);

        gridContent.addView(imgItem);
        return imgItem;
    }

    public ImageView setImageViewFullScreenWidth(GridLayout gridContent,int rowCount, int height, int width) {

        GridLayout.Spec row = GridLayout.spec(rowCount);
        GridLayout.Spec col = GridLayout.spec(0, 2);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, col);
        layoutParams.setMargins(margin, margin, margin, 0);
        layoutParams.width = width - margin - margin;
        layoutParams.height = height - margin;

        ImageView imgItem = new ImageView(mActivity);
        imgItem.setLayoutParams(layoutParams);

        gridContent.addView(imgItem);
        return imgItem;
    }

    public  int getScreenWidth() {
        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public void setListener(FeedItemOnClickListener listener) {
        this.listener = listener;
    }

    public void initListen() {

        imgBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickMore(getLayoutPosition(), v);
                }
            }
        });

        llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClickComment(getLayoutPosition(), imgComment);
                }
            }
        });

        llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener != null) {
                    listener.onClickLike(getLayoutPosition(), imgLike, txtNumLike);
                }
            }
        });
    }

}
