package com.bk.girltrollsv.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.RVFeedsAdapter;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.util.SpaceItem;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Dell on 17-Aug-16.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.rvFeeds)
    RecyclerView rvFeeds;

    ArrayList<Feed> initFeeds;

    RVFeedsAdapter feedsAdapter;


    public static HomeFragment newInstance(ArrayList<Feed> feeds) {

        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstant.FEEDS_TAG, feeds);
        homeFragment.setArguments(args);
        return homeFragment;

    }

    @Override
    protected void handleArguments(Bundle arguments) {

        initFeeds = arguments.getParcelableArrayList(AppConstant.FEEDS_TAG);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        initRv();
    }

    public void initRv() {

        int spaceBetweenItems = 10;
        feedsAdapter = new RVFeedsAdapter(getActivity(), initFeeds);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItem spaceItem = new SpaceItem(spaceBetweenItems, SpaceItem.VERTICAL);

        rvFeeds.setAdapter(feedsAdapter);
        rvFeeds.setLayoutManager(layoutManager);
        rvFeeds.addItemDecoration(spaceItem);
    }
}
