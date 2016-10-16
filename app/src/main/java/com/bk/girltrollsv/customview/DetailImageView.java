package com.bk.girltrollsv.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.ViewImageAdapter;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.ui.activity.MainActivity;
import com.bk.girltrollsv.util.LikeCommentShareUtil;
import com.bk.girltrollsv.util.ScreenHelper;
import com.bk.girltrollsv.util.StringUtil;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;

/**
 * Created by trung on 27/09/2016.
 */
public class DetailImageView {

    private MainActivity mMainActivity;

    private RelativeLayout mRLViewDetailImage;

    private Toolbar mToolbarViewImageFeed;

    private ViewPager mViewPagerImage;

    private TextView mTxtTitleFeed;
    private TextView mTxtNumLike;
    private TextView mTxtNumComment;
    private ImageView mImgBtnLike;
    private ImageView mImgBtnComment;
    private ShareButton mShareButton;
    private LinearLayout mLLInfoFeed;
    private ProgressBar mPBLoadImageDetail;
    private Feed mFeed;
    private int mPositionImage;
    private int mPositionImageClick;

    TextView txtMember;
    TextView txtTime;


    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private ArrayList<TouchImageView> mListTouchImageView;

    TouchImageView mTouchImageView;
    ViewImageAdapter mViewImageAdapter;
    private int mScreenWidth;

    Rect startBounds;
    Rect finalBounds;
    Point globalOffset;
    float scale1;
    float scale2;
    ImageView[] mViews;

    public DetailImageView(RelativeLayout rLViewDetailImage,  MainActivity mainActivity) {

        this.mRLViewDetailImage = rLViewDetailImage;
        this.mMainActivity = mainActivity;

        mToolbarViewImageFeed = (Toolbar) rLViewDetailImage.findViewById(R.id.toolbar);
        mViewPagerImage = (ViewPager) rLViewDetailImage.findViewById(R.id.view_pager_image);
        mTxtTitleFeed = (TextView) rLViewDetailImage.findViewById(R.id.txt_title_feed);
        mTxtNumLike = (TextView) rLViewDetailImage.findViewById(R.id.txt_num_like);
        mTxtNumComment = (TextView) rLViewDetailImage.findViewById(R.id.txt_num_comment);
        mImgBtnLike = (ImageView) rLViewDetailImage.findViewById(R.id.img_btn_like);
        mImgBtnComment = (ImageView)rLViewDetailImage.findViewById(R.id.img_btn_comment);
        mShareButton = (ShareButton) rLViewDetailImage.findViewById(R.id.share_button);
        mLLInfoFeed = (LinearLayout) rLViewDetailImage.findViewById(R.id.ll_info_feed);
        mPBLoadImageDetail = (ProgressBar) rLViewDetailImage.findViewById(R.id.pb_load_image_detail);
        mScreenWidth = ScreenHelper.getScreenWidthInPx();





        mPBLoadImageDetail.getIndeterminateDrawable().setColorFilter(mainActivity.getResources()
                .getColor(R.color.color_progress_bar), PorterDuff.Mode.MULTIPLY);

        txtMember = (TextView)rLViewDetailImage.findViewById(R.id.toolbar_txt_member_name);
        txtTime = (TextView)rLViewDetailImage.findViewById(R.id.toolbar_txt_time);

    }


    public void hideToolbarViewImageFeed() {

        mToolbarViewImageFeed.setVisibility(View.GONE);
    }

    public void displayToolbarViewImageFeed() {

        mToolbarViewImageFeed.setVisibility(View.VISIBLE);
    }

    public void hideLLInfoFeed() {

        mLLInfoFeed.setVisibility(View.GONE);
    }

    public void disPlayLLInfoFeed() {

        mLLInfoFeed.setVisibility(View.VISIBLE);
    }

    public void hideRLViewDetailImage() {

        mRLViewDetailImage.setVisibility(View.GONE);
    }

    public void displayRLViewDetailImage() {

        mRLViewDetailImage.setVisibility(View.VISIBLE);
    }

    public void hidePBImageDetail() {

        mPBLoadImageDetail.setVisibility(View.GONE);
    }

    public void displayPBImageDetail() {

        mPBLoadImageDetail.setVisibility(View.VISIBLE);
    }

    public void setAlphaRLViewDetailImage(int alpha) {

        mRLViewDetailImage.getBackground().setAlpha(alpha);
    }

    public boolean isDisplayToolbarAndLLInfoFeed() {

        return (mToolbarViewImageFeed.getVisibility() == View.VISIBLE);
    }

    public boolean isDisplayRLViewDetailImage(){

        return (mRLViewDetailImage.getVisibility() == View.VISIBLE);
    }



    public void viewDetailImage(FragmentManager fragmentManager, Feed feed, int posImage, ImageView[] views) {

        mViews = views;
        mPositionImageClick = posImage;
        mListTouchImageView = new ArrayList<TouchImageView>();
        mListTouchImageView.add(0, null);
        mListTouchImageView.add(1, null);
        mListTouchImageView.add(2, null);
        mListTouchImageView.add(3, null);

        if (mShortAnimationDuration == 0) {
            mShortAnimationDuration = AppConstant.DURATION_ZOOM;

        }
        displayPBImageDetail();
        //Log.d("trung", "view");
        mViewImageAdapter = new ViewImageAdapter(fragmentManager, feed, posImage);
        mViewPagerImage.setAdapter(mViewImageAdapter);
        mViewPagerImage.setCurrentItem(posImage);

        displayToolbarViewImageFeed();
        displayRLViewDetailImage();
        disPlayLLInfoFeed();

        initToolbar();
        initData(feed);

    }

    public void initToolbar() {

        mMainActivity.setSupportActionBar(mToolbarViewImageFeed);
        ActionBar actionBar = mMainActivity.getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

    }


    public void initData(Feed feed) {

        if (feed == null) {
            return;
        }
        mFeed = feed;
        String comment = feed.getComment() + AppConstant.SPACE + mMainActivity.getString(R.string.base_comment);
        StringUtil.displayText(comment, mTxtNumComment);
        StringUtil.displayText(feed.getTitle(), mTxtTitleFeed);
        StringUtil.displayText(mFeed.getMember().getUsername(), txtMember);
        StringUtil.displayText(mFeed.getTime(), txtTime);
        setLikeInfo();


        mImgBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeCommentShareUtil.handleClickLike(mMainActivity, mFeed, mImgBtnLike,
                        mTxtNumLike, R.drawable.icon_unlike_white);
            }
        });

        mImgBtnComment.setImageResource(R.drawable.icon_comment_white);
        mImgBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeCommentShareUtil.handleClickComment(mMainActivity, mFeed, v);
            }
        });

        mShareButton.setShareContent(LikeCommentShareUtil.getShareContent(mMainActivity, mFeed));

    }

    public void setLikeInfo() {
        String like = mFeed.getLike() + AppConstant.SPACE + mMainActivity.getString(R.string.base_like);
        StringUtil.displayText(like, mTxtNumLike);
        if (mFeed.getIsLike() == AppConstant.UN_LIKE) {
            mImgBtnLike.setImageResource(R.drawable.icon_unlike_white);
        } else {
            mImgBtnLike.setImageResource(R.drawable.icon_like);
        }
    }

    public void setDataImage(int posImage) {

        mTouchImageView = mListTouchImageView.get(posImage);
        //Log.d("trung", "data" + posImage);

        if (mTouchImageView == null) {
            return;
        }

        float[] f = new float[9];
        mTouchImageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = mTouchImageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);


        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.

        //expandedImageView.setImageResource(imageResId);
        //expandedImageView.setOnTouchListener(new unZoomOnTouchListener());


        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        startBounds = new Rect();
        finalBounds = new Rect();
        globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        mViews[posImage].getGlobalVisibleRect(startBounds);
        mMainActivity.findViewById(R.id.view_root_main)
                .getGlobalVisibleRect(finalBounds, globalOffset);

        //mTouchImageView.getGlobalVisibleRect(finalBounds);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        final int actHF = actH * mScreenWidth / actW;

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        scale1 = (float) startBounds.width() / mScreenWidth;
        scale2 = (float) startBounds.height() / actHF;

        //Log.d("trung", actW + " " + actHF + " ");
//        Log.d("trung", startBounds.width() + " " + startBounds.height() + " ");
//        Log.d("trung",  startBounds.top + " " + "top ");
//        Log.d("trung",  mView.getY() + " " +  "mview x");

        //float realStartHeight = ((float) startBounds.height() * 1080) / startBounds.width();


//        if ((float) finalBounds.width() / finalBounds.height()
//                > (float) startBounds.width() / startBounds.height()) {
//            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
//        } else {
        //Log.d("trung", "real" + (realStartHeight - actHF));
        // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height() ;
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.top -= (realStartHeight - actHF)/2;
//            startBounds.bottom += deltaHeight;

        float startHeight = scale2 * finalBounds.height();
        float deltaHeight = (startHeight - startBounds.height()) / 2;
        startBounds.top -= deltaHeight;
        startBounds.bottom += deltaHeight;

        mTouchImageView.setPivotX(0f);
        mTouchImageView.setPivotY(0f);

    }


    public void zoomIn(TouchImageView touchImageView, int posImage) {

        //Log.d("trung", "pos" + posImage);
        mListTouchImageView.set(posImage, touchImageView);


        if (posImage == mPositionImageClick) {
            setDataImage(mPositionImageClick);

            hidePBImageDetail();

            //mYouchImageView = touchImageView;


            //Log.d("trung",  finalBounds.left + " " + "final left ");

            // Hide the thumbnail and show the zoomed-in view. When the animation
            // begins, it will position the zoomed-in view in the place of the
            // thumbnail.
            //mView.setAlpha(0f);
            //displayRLViewDetailImage();
            setAlphaRLViewDetailImage(250);

            if (touchImageView == null) {
                return;
            }
            touchImageView.setVisibility(View.VISIBLE);
            //textImage.setVisibility(View.VISIBLE);
            // Set the pivot point for SCALE_X and SCALE_Y transformations
            // to the top-left corner of the zoomed-in view (the default
            // is the center of the view).


            // Construct and run the parallel animation of the four translation and
            // scale properties (X, Y, SCALE_X, and SCALE_Y).
            AnimatorSet set = new AnimatorSet();
            set
                    .play(ObjectAnimator.ofFloat(touchImageView, View.X,
                            startBounds.left, finalBounds.left))
                    .with(ObjectAnimator.ofFloat(touchImageView, View.Y,
                            startBounds.top, finalBounds.top))
                    .with(ObjectAnimator.ofFloat(touchImageView, View.SCALE_X,
                            scale1, 1f))
                    .with(ObjectAnimator.ofFloat(touchImageView,
                            View.SCALE_Y, scale2, 1f));
            set.setDuration(mShortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCurrentAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mCurrentAnimator = null;
                }
            });
            set.start();
            mCurrentAnimator = set;

        }


    }

    public void zoomOut() {
        if (mTouchImageView == null){

            hideRLViewDetailImage();
            return;
        }

        final int currentPosImage = mViewPagerImage.getCurrentItem();
        Log.d("trung", "currpos" + currentPosImage);
        mTouchImageView = mListTouchImageView.get(currentPosImage);

        if (mTouchImageView == null) {
            return;
        }
        if (mTouchImageView.onBackZoom()) {

            setDataImage(currentPosImage);


            //mTouchImageView = mListTouchImageView.get(currentPosImage);

            setAlphaRLViewDetailImage(0);
            hideToolbarViewImageFeed();
            hideLLInfoFeed();
            hidePBImageDetail();
            mViews[currentPosImage].setAlpha(0f);


            //float startScaleFinal = startScale;
            if (mCurrentAnimator != null) {
                mCurrentAnimator.cancel();

            }
            //Log.d("trung",  startBounds.top + " " + "top ");

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            AnimatorSet set = new AnimatorSet();
            set.play(ObjectAnimator
                    .ofFloat(mTouchImageView, View.Y, finalBounds.top, startBounds.top))
                    .with(ObjectAnimator.ofFloat(mTouchImageView, View.X, finalBounds.left, startBounds.left))
                    .with(ObjectAnimator
                            .ofFloat(mTouchImageView,
                                    View.SCALE_X, 1f, scale1))
                    .with(ObjectAnimator
                            .ofFloat(mTouchImageView,
                                    View.SCALE_Y, 1f, scale2));
            set.setDuration(mShortAnimationDuration);
            set.setInterpolator(new DecelerateInterpolator());
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mViews[currentPosImage].setAlpha(1f);

                    mTouchImageView.setVisibility(View.GONE);
                    hideRLViewDetailImage();
                    mCurrentAnimator = null;
                    mTouchImageView = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mViews[currentPosImage].setAlpha(1f);

                    mTouchImageView.setVisibility(View.GONE);
                    hideRLViewDetailImage();
                    mCurrentAnimator = null;
                }
            });
            set.start();
            mCurrentAnimator = set;
        }


    }


}
