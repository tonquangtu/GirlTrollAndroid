package com.bk.girltrollsv.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.RVFeedsAdapter;
import com.bk.girltrollsv.callback.FeedItemOnClickListener;
import com.bk.girltrollsv.callback.OnLoadMoreListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.Paging;
import com.bk.girltrollsv.network.ConfigNetwork;
import com.bk.girltrollsv.ui.activity.CommentActivity;
import com.bk.girltrollsv.ui.activity.VideoActivity;
import com.bk.girltrollsv.util.SpaceItem;
import com.bk.girltrollsv.util.StringUtil;
import com.bk.girltrollsv.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 17-Aug-16.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.rvFeeds)
    RecyclerView rvFeeds;

    @Bind(R.id.swipe_refresh_layout_new_feed)
    SwipeRefreshLayout mRefreshNewFeed;

    @Bind(R.id.ll_error_load_feed)
    LinearLayout llErrorLoadFeed;

    @Bind(R.id.btn_reload_feed)
    Button btnReload;

    ArrayList<Feed> initFeeds;

    Paging pagingLoadNewFeed;

    RVFeedsAdapter feedsAdapter;

    Activity mActivity;

    boolean isRefreshing = false;

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
        mActivity = getActivity();
        initRv();
        initRefreshLayout();

        if (initFeeds == null || initFeeds.size() == 0) {
            rvFeeds.setVisibility(View.GONE);
        } else {
            llErrorLoadFeed.setVisibility(View.GONE);
        }
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

        feedsAdapter.setItemListener(new FeedItemOnClickListener() {
            @Override
            public void onClickImage(int posFeed, int posImage, View view) {

            }

            @Override
            public void onClickVideo(int posFeed, View view) {
                handleClickVideo(posFeed);
            }

            @Override
            public void onClickLike(int posFeed, View view) {
                handleClickLike(posFeed);
            }

            @Override
            public void onClickComment(int posFeed, View view) {
                handleClickComment(posFeed);
            }

            @Override
            public void onClickMore(int posFeed, View view) {

            }
        });
    }

    public void handleLoadMore() {

        if (Utils.checkInternetAvailable()) {
            loadMoreNewFeed();
        } else {
            Utils.toastShort(mActivity, R.string.no_network);
        }
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

        dataToServer.put(AppConstant.CURRENT_FEED_ID_TAG, afterFeedId);
        dataToServer.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));

        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callLoadNewFeed(dataToServer);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                feedsAdapter.removeLastItem();
                if (response.isSuccessful() && response.body() != null) {

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

    public void initRefreshLayout() {

        mRefreshNewFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handleRefreshNewFeed();
            }
        });
    }

    public void handleRefreshNewFeed() {

        if (!isRefreshing) {
            if (Utils.checkInternetAvailable()) {
                refreshNewFeed();
            } else {
                Utils.toastShort(mActivity, R.string.no_network);
                mRefreshNewFeed.setRefreshing(false);
            }
        } else {
            mRefreshNewFeed.setRefreshing(false);
        }
    }

    public void refreshNewFeed() {

        isRefreshing = true;
        String firstIdFeed;
        if (feedsAdapter.getFeeds().size() > 0) {
            firstIdFeed = feedsAdapter.getFeeds().get(0).getFeedId();
        } else {
            firstIdFeed = AppConstant.DEFAULT_FEED_ID;
        }

        Map<String, String> dataToServer = new HashMap<>();
        dataToServer.put(AppConstant.CURRENT_FEED_ID_TAG, firstIdFeed);
        dataToServer.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));
        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callRefreshNewFeed(dataToServer);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                if (response.isSuccessful()) {
                    FeedResponse body = response.body();
                    if (body != null && body.getSuccess() == AppConstant.SUCCESS) {
                        if (body.getData() != null && body.getData().size() > 0) {
                            if (feedsAdapter.getFeeds().size() == 0) {
                                pagingLoadNewFeed.setAfter(body.getPaging().getAfter());
                                pagingLoadNewFeed.setBefore(body.getPaging().getBefore());
                            }
                            feedsAdapter.insertItems(body.getData(), 0);
                            rvFeeds.scrollToPosition(0);
                        }
                    }
                }
                isRefreshing = false;
                mRefreshNewFeed.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                isRefreshing = false;
                mRefreshNewFeed.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }


    public void handleClickVideo(int posFeed) {

        Feed feed = feedsAdapter.getFeeds().get(posFeed);
        Intent intent = new Intent(mActivity, VideoActivity.class);
        Bundle data = new Bundle();
        data.putParcelable(AppConstant.FEED_TAG, feed);
        intent.putExtra(AppConstant.PACKAGE, data);
        mActivity.startActivity(intent);

    }

    public void handleClickComment(int positionFeed) {

        Feed feed = feedsAdapter.getFeeds().get(positionFeed);
        if (feed != null) {

            Bundle data = new Bundle();
            data.putString(AppConstant.FEED_ID_TAG, feed.getFeedId());
            Intent intent = new Intent(mActivity, CommentActivity.class);
            intent.putExtra(AppConstant.PACKAGE, data);
            mActivity.startActivity(intent);
        }
    }

    public void handleClickLike(int positionFeed) {

        // check login
        // if login then like. change image and start animation
        // sent info like to server.
    }

    @OnClick(R.id.btn_reload_feed)
    public void onClickReloadFeed(View view) {

        mRefreshNewFeed.setRefreshing(true);
        refreshNewFeed();
    }

}
