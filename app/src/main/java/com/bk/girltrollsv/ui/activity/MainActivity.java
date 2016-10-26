package com.bk.girltrollsv.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.PagerMainAdapter;
import com.bk.girltrollsv.callback.OnClickTabListener;
import com.bk.girltrollsv.callback.OnLoginCompletedListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.CustomViewPager;
import com.bk.girltrollsv.customview.DetailImageView;
import com.bk.girltrollsv.customview.MainSegmentView;
import com.bk.girltrollsv.model.EventBase;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.Paging;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.view_root_main)
    View mViewRoot;

    @Bind(R.id.custom_vp_main)
    CustomViewPager mViewPagerMain;

    @Bind(R.id.segment_view_bottom_main)
    MainSegmentView mSegMain;

    @Bind(R.id.rl_view_image)
    RelativeLayout rlViewImage;

    private ArrayList<Feed> initFeeds;

    private Paging pagingLoadNewFeed;

    private ArrayList<EventBase> eventCatalogs;

    PagerMainAdapter mPagerMainAdapter;

    DetailImageView mDetailImageView;

    OnClickTabListener mOnTabListener;

    OnLoginCompletedListener mOnLoginCompletedListener;


    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        initSegView();

        initViewPager();

        guardView();

    }

    private void guardView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void handleIntent(Intent intent) {

        Bundle dataFromSplash = intent.getBundleExtra(AppConstant.PACKAGE);
        initFeeds = dataFromSplash.getParcelableArrayList(AppConstant.FEEDS_TAG);
        pagingLoadNewFeed = dataFromSplash.getParcelable(AppConstant.PAGING_TAG);
//        eventCatalogs = dataFromSplash.getParcelableArrayList(AppConstant.EVENT_CATALOG_TAG);
    }

    public void initSegView() {

        int[] icons = new int[]{
                R.drawable.icon_home, R.drawable.icon_menu, R.drawable.icon_upload,
                R.drawable.icon_event, R.drawable.icon_personal
        };

        int[] pressIcons = new int[]{
                R.drawable.icon_home_press, R.drawable.icon_menu_press, R.drawable.icon_upload_press,
                R.drawable.icon_event_press, R.drawable.icon_personal_press
        };

        mSegMain.setIcons(icons);
        mSegMain.setPressIcons(pressIcons);

        mSegMain.setOnSegmentViewSelectedListener(new MainSegmentView.OnSegmentViewSelectedListener() {
            @Override
            public void onSegmentViewSelected(int prePosition, int currPosition) {
                mViewPagerMain.setCurrentItem(currPosition);
                handleClickPersonalTab(currPosition);
            }
        });

    }

    public void initViewPager() {

        mPagerMainAdapter = new PagerMainAdapter(getSupportFragmentManager(), initFeeds, pagingLoadNewFeed, eventCatalogs);
        mViewPagerMain.setAdapter(mPagerMainAdapter);
        mViewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSegMain.setSelectedTab(position);
                handleClickPersonalTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public DetailImageView getDetailImageView(){

        if (mDetailImageView == null){

            mDetailImageView = new DetailImageView(rlViewImage, MainActivity.this);

        }
        return mDetailImageView;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){

            if (mDetailImageView != null)
                mDetailImageView.zoomOut();
            //rlViewImage.setVisibility(View.GONE);
        }

        return false;
    }

    public View getViewRoot() {
        return mViewRoot;
    }

    @Override
    public void onBackPressed() {

        if (mDetailImageView != null && mDetailImageView.isDisplayRLViewDetailImage()){
            mDetailImageView.zoomOut();
        }
        else {
            super.onBackPressed();
        }

    }

    public void setOnClickTabListener(OnClickTabListener onTabListener){
        this.mOnTabListener = onTabListener;

    }

    public void handleClickPersonalTab(int currPosition){

        if (mOnTabListener != null){
            mOnTabListener.onClickTab(currPosition);
        }
    }

    public void setmOnLoginCompletedListener(OnLoginCompletedListener onLoginCompletedListener){
        this.mOnLoginCompletedListener = onLoginCompletedListener;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){

            case AppConstant.RESULT_CODE_LOGIN:
                boolean isSuccessLogin = data.getBooleanExtra(AppConstant.IS_LOGIN_TAG, false);
                mOnLoginCompletedListener.onLoginCompleted(isSuccessLogin);
                break;
        }
    }
}