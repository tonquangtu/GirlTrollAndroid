package com.bk.girltrollsv.adapter.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.ui.fragment.EventFragment;
import com.bk.girltrollsv.ui.fragment.HomeFragment;
import com.bk.girltrollsv.ui.fragment.PersonalFragment;
import com.bk.girltrollsv.ui.fragment.UploadFeedFragment;

import java.util.ArrayList;

/**
 * Created by Dell on 17-Aug-16.
 */
public class PagerMainAdapter extends FragmentPagerAdapter {

    public final int PAGE_COUNT = 4;

    public static final int HOME_POS = 0;

    public static final int UPLOAD_FEED_POS = 1;

    public static final int EVENT_POS = 2;

    public static final int PERSONAL_POS = 3;

    ArrayList<Feed> initFeeds;

    public PagerMainAdapter(FragmentManager fm, ArrayList<Feed> initFeeds) {
        super(fm);
        this.initFeeds = initFeeds;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position) {

            case HOME_POS :
                fragment = HomeFragment.newInstance(initFeeds);
                break;
            case UPLOAD_FEED_POS :
                fragment = UploadFeedFragment.newInstance();
                break;

            case EVENT_POS :
                fragment = EventFragment.newInstance();
                break;

            case PERSONAL_POS :
                fragment = PersonalFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
