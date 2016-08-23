package com.bk.girltrollsv.ui.activity;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.PagerMainAdapter;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.CustomViewPager;
import com.bk.girltrollsv.customview.MainSegmentView;
import com.bk.girltrollsv.model.EventBase;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.Paging;
import com.bk.girltrollsv.util.StringUtil;
import com.bk.girltrollsv.util.networkutil.LoadUtil;
import com.facebook.Profile;

import java.util.ArrayList;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.drawer_layout_main)
    DrawerLayout mDrawerMain;

    @Bind(R.id.toolbar)
    Toolbar mToolbarMain;

    @Bind(R.id.custom_vp_main)
    CustomViewPager mViewPagerMain;

    @Bind(R.id.segment_view_bottom_main)
    MainSegmentView mSegMain;

    @Bind(R.id.navigation_view_main)
    NavigationView mNavMain;

    @Bind(R.id.cir_img_profile)
    CircleImageView cirImgProfile;

    @Bind(R.id.ttx_profile_name)
    TextView txtProfileName;

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

        getDataFromSplash();

        initToolbar();

        initDrawer();

//        initProfile();

        initSegView();

        initViewPager();

        guardView();
    }

    private void guardView() {

    }

    @Override
    public void initData() {

    }

    public void getDataFromSplash() {

        Bundle dataFromSplash = getIntent().getBundleExtra(AppConstant.PACKAGE);

        initFeeds = dataFromSplash.getParcelableArrayList(AppConstant.FEEDS_TAG);
        pagingLoadNewFeed = dataFromSplash.getParcelable(AppConstant.PAGING_TAG);
        eventCatalogs = dataFromSplash.getParcelableArrayList(AppConstant.EVENT_CATALOG_TAG);
    }

    public void initToolbar() {

        setSupportActionBar(mToolbarMain);

    }

    public void initDrawer() {

        ActionBarDrawerToggle drawerToogle = new ActionBarDrawerToggle(
                this,
                mDrawerMain,
                mToolbarMain,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerMain.addDrawerListener(drawerToogle);
        drawerToogle.syncState();

        mNavMain.setNavigationItemSelectedListener(this);

    }

    public void initProfile() {

        Profile profile = Profile.getCurrentProfile();

        int widthProfile = 62;
        int heightProfile = 62;

        if (profile != null) {

            String url = profile.getProfilePictureUri(widthProfile, heightProfile).toString();
            LoadUtil.loadImage(url, cirImgProfile);
            StringUtil.displayText(profile.getName(), txtProfileName);
        }
    }

    public void initSegView() {

        int[] icons = new int[] {
                R.drawable.icon_home, R.drawable.icon_upload_new_feed,
                R.drawable.icon_event, R.drawable.icon_personal
        };

        int [] pressIcons = new int [] {
                R.drawable.icon_home_press, R.drawable.icon_upload_new_feed_press,
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

}