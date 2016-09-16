package com.bk.girltrollsv.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.PagerMainAdapter;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.CustomViewPager;
import com.bk.girltrollsv.customview.MainSegmentView;
import com.bk.girltrollsv.model.EventBase;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.Paging;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.view_root_main)
    View mViewRoot;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.custom_vp_main)
    CustomViewPager mViewPagerMain;

    @Bind(R.id.segment_view_bottom_main)
    MainSegmentView mSegMain;

    private ArrayList<Feed> initFeeds;

    private Paging pagingLoadNewFeed;

    private ArrayList<EventBase> eventCatalogs;

    PagerMainAdapter mPagerMainAdapter;


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

            }
        });

    }

    public void initViewPager() {

        mPagerMainAdapter = new PagerMainAdapter(getSupportFragmentManager(), initFeeds, pagingLoadNewFeed, eventCatalogs);
        mViewPagerMain.setAdapter(mPagerMainAdapter);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public View getViewRoot() {
        return mViewRoot;
    }

    public int getToolbarHeight() {
        return mToolbar.getLayoutParams().height;
    }


}