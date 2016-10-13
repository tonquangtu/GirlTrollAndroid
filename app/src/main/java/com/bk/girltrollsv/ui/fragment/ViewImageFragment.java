package com.bk.girltrollsv.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.ViewImageAdapter;
import com.bk.girltrollsv.callback.OnLoadImageListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.DetailImageView;
import com.bk.girltrollsv.customview.TouchImageView;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.ui.activity.MainActivity;
import com.bk.girltrollsv.util.LikeCommentShareUtil;
import com.bk.girltrollsv.util.StringUtil;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

import static com.bk.girltrollsv.constant.AppConstant.*;

/**
 * Created by trung on 16/09/2016.
 */
public class ViewImageFragment extends BaseFragment {

    @Bind(R.id.expanded_image)
    TouchImageView mTouchImageView;
    MainActivity mMainActivity;

    ImageInfo imageInfo;
    //boolean mIsClickImage;
    private int mPosImage;


    //private Toolbar mToolbarViewImageFeed;


    DetailImageView mDetailImageView;


    public static ViewImageFragment newInstance(ImageInfo imageInfo, int posImage) {
        ViewImageFragment viewImageFragment = new ViewImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_INFO_TAG, imageInfo );
        args.putInt(POS_IMAGE_TAG, posImage);

        viewImageFragment.setArguments(args);
        //this.textImage = mainActivity.getTextImage();



//        mShortAnimationDuration = mainActivity.getResources().getInteger(
//                android.R.integer.config_longAnimTime);

        return viewImageFragment;
    }

    @Override
    protected void handleArguments(Bundle arguments) {
        imageInfo = arguments.getParcelable(IMAGE_INFO_TAG);
        //mIsClickImage = arguments.getBoolean("b");
        mPosImage = arguments.getInt(POS_IMAGE_TAG);
    }

    @Override
    protected void initView() {
        mMainActivity = getMainActivity();
        mDetailImageView = mMainActivity.getDetailImageView();
        //Log.d("trung", "initView" + mPosImage);
        LoadUtil.loadImage(imageInfo.getUrlImage(), mTouchImageView, new OnLoadImageListener() {
            @Override
            public void onLoadComplete() {
                //mDetailImageView.hidePBImageDetail();
                mDetailImageView.zoomIn(mTouchImageView,mPosImage);

            }
        });
//        if (mIsClickImage){
//            mDetailImageView.zoomIn(mTouchImageView);
//        }



        mTouchImageView.setView(mMainActivity);
        //mTouchImageView.setVisibility(View.VISIBLE);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_image;
    }


}
