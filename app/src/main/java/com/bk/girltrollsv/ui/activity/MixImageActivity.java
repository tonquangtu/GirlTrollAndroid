package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

import butterknife.Bind;

public class MixImageActivity extends BaseActivity {

    @Bind(R.id.img_photo_post)
    ImageView imgPhotoPost;

    String mPhotoPath = null;

    @Override
    public void handleIntent(Intent intent) {

        Bundle fromData = intent.getBundleExtra(AppConstant.PACKAGE);
        mPhotoPath = fromData.getString(AppConstant.PHOTO_PATH_TAG);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_fix_image;
    }

    @Override
    public void initView() {
        LoadUtil.loadImageResize(mPhotoPath, imgPhotoPost, 800, 800);
    }

    @Override
    public void initData() {

    }

}
