package com.bk.girltrollsv.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.IDetailImage;
import com.bk.girltrollsv.callback.OnLoadImageListener;
import com.bk.girltrollsv.customview.DetailImageView;
import com.bk.girltrollsv.customview.TouchImageView;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

import butterknife.Bind;

import static com.bk.girltrollsv.constant.AppConstant.IMAGE_INFO_TAG;
import static com.bk.girltrollsv.constant.AppConstant.POS_IMAGE_TAG;

/**
 * Created by trung on 16/09/2016.
 */
public class ViewImageFragment extends BaseFragment {

    @Bind(R.id.expanded_image)
    TouchImageView mTouchImageView;

    Activity mActivity;
    ImageInfo imageInfo;
    //boolean mIsClickImage;
    private int mPosImage;
    DetailImageView mDetailImageView;


    public static ViewImageFragment newInstance(ImageInfo imageInfo, int posImage) {

        ViewImageFragment viewImageFragment = new ViewImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_INFO_TAG, imageInfo );
        args.putInt(POS_IMAGE_TAG, posImage);
        viewImageFragment.setArguments(args);
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
        mActivity = getActivity();
        if (mActivity instanceof IDetailImage)
            mDetailImageView = ((IDetailImage)mActivity).getDetailImageView();

        LoadUtil.loadImage(imageInfo.getUrlImage(), mTouchImageView, new OnLoadImageListener() {
            @Override
            public void onLoadComplete() {
                mDetailImageView.zoomIn(mTouchImageView,mPosImage);

            }
        });
        mTouchImageView.setView(mDetailImageView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_image;
    }



}
