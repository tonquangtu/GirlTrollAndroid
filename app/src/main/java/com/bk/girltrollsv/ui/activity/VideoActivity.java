package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.OnFullScreenListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.CustomVideoView;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.Video;
import com.bk.girltrollsv.util.LikeCommentShareUtil;
import com.bk.girltrollsv.util.ScreenHelper;
import com.bk.girltrollsv.util.StringUtil;
import com.facebook.share.widget.ShareButton;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoActivity extends BaseActivity {

    @Bind(R.id.rlRoot)
    RelativeLayout mRootView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.toolbar_txt_member_name)
    TextView txtMember;

    @Bind(R.id.toolbar_txt_time)
    TextView txtTime;

    @Bind(R.id.txt_title_feed)
    TextView txtTitleFeed;

    @Bind(R.id.txt_num_like)
    TextView txtNumLike;

    @Bind(R.id.txt_num_comment)
    TextView txtNumComment;

    @Bind(R.id.img_btn_like)
    ImageView imgLike;

    @Bind(R.id.img_btn_comment)
    ImageView imgComment;

    @Bind(R.id.share_button)
    ShareButton shareButton;

    @Bind(R.id.ll_info_feed)
    LinearLayout llInfoFeed;

    @Bind(R.id.surface_view_video)
    SurfaceView surfaceView;

    CustomVideoView mCustomVideoView;

    Feed mFeed;

    Video video;

    FrameLayout frameContainer;


    @Override
    public int setContentViewId() {
        return R.layout.activity_video;
    }

    @Override
    public void handleIntent(Intent intent) {

        mFeed = intent.getBundleExtra(AppConstant.PACKAGE).getParcelable(AppConstant.FEED_TAG);
        if (mFeed != null) {
            video = mFeed.getVideo();
        }
    }

    @Override
    public void initView() {

        initToolbar();

        initFrameContainer();

        initVideoView();

        initLikeCommentShare();

        llInfoFeed.setBackgroundColor(getResources().getColor(R.color.background_video_activity));
        mRootView.setBackgroundColor(getResources().getColor(R.color.background_video_activity));
    }

    @Override
    public void initData() {

        if (mFeed == null || video == null) {
            return;
        }
        String comment = mFeed.getComment() + AppConstant.SPACE + getString(R.string.base_comment);
        StringUtil.displayText(comment, txtNumComment);
        StringUtil.displayText(mFeed.getTitle(), txtTitleFeed);

        shareButton.setShareContent(LikeCommentShareUtil.getShareContent(this, mFeed));
        setLikeInfo();
    }

    public void initToolbar() {

        mToolbar.setBackgroundColor(this.getResources().getColor(R.color.background_video_activity));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        StringUtil.displayText(mFeed.getMember().getUsername(), txtMember);
        StringUtil.displayText(mFeed.getTime(), txtTime);
    }

    public void initFrameContainer() {

        float ratio = 0.75f;
        int width = ScreenHelper.getScreenWidthInPx();
        int height = (int) (ratio * width);
        frameContainer = new FrameLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        frameContainer.setLayoutParams(layoutParams);
        mRootView.addView(frameContainer);
    }

    public void initVideoView() {

        mCustomVideoView = new CustomVideoView(this, frameContainer, surfaceView,  video);
        mCustomVideoView.setFullScreenListener(new OnFullScreenListener() {
            @Override
            public void onFullScreenChange(boolean isFull) {
                if (isFull) {
                    setVisibilityControl(View.GONE);
                } else {
                    setVisibilityControl(View.VISIBLE);
                }
            }
        });

    }

    public void initLikeCommentShare() {
        imgComment.setImageResource(R.drawable.icon_comment_white);
        shareButton.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mCustomVideoView.getMediaController().show();
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                break;
            case R.id.download_menu:
                downloadVideo();
                break;
            case R.id.report_menu:
                report();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_video_menu, menu);
        return true;
    }

    public void downloadVideo() {

    }

    public void report() {

    }

    public void setVisibilityControl(int visibility) {

        mToolbar.setVisibility(visibility);
        llInfoFeed.setVisibility(visibility);
        txtTitleFeed.setVisibility(visibility);
    }

    @OnClick(R.id.ll_like)
    public void onClickLike(View view) {
        LikeCommentShareUtil.handleClickLike(this, mFeed, imgLike,
                txtNumLike, R.drawable.icon_unlike_white);
    }

    @OnClick(R.id.ll_comment)
    public void onClickComment(View view) {
        LikeCommentShareUtil.handleClickComment(this, mFeed, imgComment);
    }

    public void setLikeInfo() {
        String like = mFeed.getLike() + AppConstant.SPACE + getString(R.string.base_like);
        StringUtil.displayText(like, txtNumLike);
        if (mFeed.getIsLike() == AppConstant.UN_LIKE) {
            imgLike.setImageResource(R.drawable.icon_unlike_white);
        } else {
            imgLike.setImageResource(R.drawable.icon_like);
        }
    }

    @Override
    protected void onDestroy() {
        mCustomVideoView.release();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mCustomVideoView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mCustomVideoView.play();
        super.onResume();
    }
}
