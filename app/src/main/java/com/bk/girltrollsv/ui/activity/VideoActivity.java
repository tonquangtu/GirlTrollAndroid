package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.OnFullScreenListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.CustomVideoView;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.Video;
import com.bk.girltrollsv.util.StringUtil;
import com.bk.girltrollsv.util.Utils;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import butterknife.Bind;

public class VideoActivity extends BaseActivity {

    @Bind(R.id.rlRoot)
    RelativeLayout mRootView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

//    @Bind(R.id.frame_container)
//    FrameLayout frameContainer;

    @Bind(R.id.txt_title_feed)
    TextView txtTitleFeed;

    @Bind(R.id.view_divider_info_comment_like)
    View viewDivider;

    @Bind(R.id.txt_num_like)
    TextView txtNumLike;

    @Bind(R.id.txt_num_comment)
    TextView txtNumComment;

    @Bind(R.id.ll_comment_like_share)
    LinearLayout llCommentLikeShare;

    @Bind(R.id.ll_info_num_comment_like)
    LinearLayout llInfoNumCommentLike;

    @Bind(R.id.btn_like)
    Button btnLike;

    @Bind(R.id.btn_comment)
    Button btnComment;

    @Bind(R.id.share_btn)
    ShareButton btnShare;

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

    }

    @Override
    public void initData() {

        if (mFeed == null || video == null) {
            return;
        }
        mCustomVideoView.initMediaPlayer(video);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mFeed.getMember().getUsername());
        actionBar.setSubtitle(mFeed.getTime());

        String like = mFeed.getLike() + AppConstant.SPACE + getString(R.string.base_like);
        String comment = mFeed.getComment() + AppConstant.SPACE + getString(R.string.base_comment);

        StringUtil.displayText(like, txtNumLike);
        StringUtil.displayText(comment, txtNumComment);
        StringUtil.displayText(mFeed.getTitle(), txtTitleFeed);

    }

    public void initToolbar() {

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initFrameContainer() {

        float ratio = 0.75f;
        int width = Utils.getScreenWidth(this);
        int height = (int) (ratio * width);
        frameContainer = new FrameLayout(this);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        frameContainer.setLayoutParams(layoutParams);
        mRootView.addView(frameContainer);

    }

    public void initVideoView() {

        mCustomVideoView = new CustomVideoView(this, frameContainer);
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
        llCommentLikeShare.setVisibility(visibility);
        llInfoNumCommentLike.setVisibility(visibility);
        txtTitleFeed.setVisibility(visibility);
        viewDivider.setVisibility(visibility);

    }

    public void initLikeCommentShare() {

        
        btnShare.setShareContent(getShareContent(mFeed));

    }

    public ShareLinkContent getShareContent(Feed feed) {

        String title = feed.getTitle();
        String description = this.getResources().getString(R.string.share_description);
        String url;

        if(feed.getVideo() != null) {
            url = feed.getVideo().getUrlVideo();
        } else {
            url = AppConstant.URL_BASE;
        }

        Uri uri = Uri.parse(url);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(description)
                .setContentUrl(uri)
                .build();

        return linkContent;
    }

}
