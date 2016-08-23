package com.bk.girltrollsv.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.RVFeedsAdapter;
import com.bk.girltrollsv.callback.OnLoadMoreListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.Paging;
import com.bk.girltrollsv.network.ConfigNetwork;
import com.bk.girltrollsv.util.SpaceItem;
import com.bk.girltrollsv.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 17-Aug-16.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.rvFeeds)
    RecyclerView rvFeeds;

    ArrayList<Feed> initFeeds;

    Paging pagingLoadNewFeed;

    RVFeedsAdapter feedsAdapter;


    public static HomeFragment newInstance(ArrayList<Feed> feeds, Paging pagingLoadNewFeed) {

        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(AppConstant.FEEDS_TAG, feeds);
        args.putParcelable(AppConstant.PAGING_TAG, pagingLoadNewFeed);
        homeFragment.setArguments(args);
        return homeFragment;

    }

    @Override
    protected void handleArguments(Bundle arguments) {

        initFeeds = arguments.getParcelableArrayList(AppConstant.FEEDS_TAG);
        pagingLoadNewFeed = arguments.getParcelable(AppConstant.PAGING_TAG);

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

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItem spaceItem = new SpaceItem(spaceBetweenItems, SpaceItem.VERTICAL);

        rvFeeds.setLayoutManager(layoutManager);
        rvFeeds.addItemDecoration(spaceItem);

        // must set adapter after setLayoutManager
        feedsAdapter = new RVFeedsAdapter(getActivity(), rvFeeds, initFeeds);
        rvFeeds.setAdapter(feedsAdapter);

        feedsAdapter.setLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handleLoadMore();
            }
        });
    }

    public void handleLoadMore() {

        loadMoreNewFeed();
    }

    public void loadMoreNewFeed() {

        feedsAdapter.insertLastItem(null);

        String afterFeedId;
        Map<String, String> dataToServer = new HashMap<>();
        if (!StringUtil.isEmpty(pagingLoadNewFeed.getAfter())) {
            afterFeedId = pagingLoadNewFeed.getAfter();
        } else {
            afterFeedId = String.valueOf(AppConstant.DEFAULT_FEED_ID);
        }
        Log.e("tuton", afterFeedId);
        dataToServer.put(AppConstant.CURRENT_FEED_ID_TAG, afterFeedId);
        dataToServer.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));

        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callLoadNewFeed(dataToServer);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                feedsAdapter.removeLastItem();
                if(response.isSuccessful() && response.body() != null) {

                    feedsAdapter.insertLastItems(response.body().getData());
                    pagingLoadNewFeed.setAfter(response.body().getPaging().getAfter());
                    pagingLoadNewFeed.setBefore(response.body().getPaging().getBefore());
                }
                feedsAdapter.endLoadingMore();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                feedsAdapter.removeLastItem();
                feedsAdapter.endLoadingMore();
            }
        });

    }
}
