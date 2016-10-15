package com.bk.girltrollsv.adapter.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.ui.activity.MainActivity;
import com.bk.girltrollsv.ui.fragment.ViewImageFragment;

import java.util.ArrayList;

/**
 * Created by trung on 16/09/2016.
 */
public class ViewImageAdapter extends FragmentStatePagerAdapter {
    ArrayList<ImageInfo> listImageInfo;
    ViewImageFragment mFragment = null;
    int mPosImage;
    //boolean mIsClickImage= false;

    public ViewImageAdapter(FragmentManager fm, Feed feed, int posImage) {
        super(fm);
        this.listImageInfo = feed.getImages();
        this.mPosImage = posImage;

    }

    @Override
    public Fragment getItem(int position) {

        mFragment = ViewImageFragment.newInstance(listImageInfo.get(position), position);

        return mFragment;
    }

    @Override
    public int getCount() {
        return listImageInfo.size();
    }

}
