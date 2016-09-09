package com.bk.girltrollsv.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.RVFeedsAdapter;
import com.bk.girltrollsv.callback.FeedItemOnClickListener;
import com.bk.girltrollsv.callback.OnLoadMoreListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.model.dataserver.Paging;
import com.bk.girltrollsv.network.ConfigNetwork;
import com.bk.girltrollsv.ui.activity.VideoActivity;
import com.bk.girltrollsv.util.LikeCommentShareUtil;
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

    @Bind(R.id.pgbReload)
    ProgressBar pgbReload;

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
            mRefreshNewFeed.setVisibility(View.GONE);
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
            public void onClickLike(int posFeed, ImageButton imgBtnLike, TextView txtNumLike) {
                Feed feed = feedsAdapter.getFeeds().get(posFeed);
                LikeCommentShareUtil.handleClickLike(mActivity, feed, imgBtnLike, txtNumLike, R.drawable.icon_unlike);
            }


            @Override
            public void onClickComment(int posFeed, View view) {
                Feed feed = feedsAdapter.getFeeds().get(posFeed);
                LikeCommentShareUtil.handleClickComment(mActivity, feed);
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
        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callLoadNewFeed(getTagLoadMore());
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
        mRefreshNewFeed.setColorSchemeResources(R.color.green_600, R.color.red_600, R.color.blue_grey_600);
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
        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callRefreshNewFeed(getTagRefresh());
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                if (response.isSuccessful()) {
                    addDataToFirstList(response.body());
                }
                isRefreshing = false;
                mRefreshNewFeed.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                isRefreshing = false;
                mRefreshNewFeed.setRefreshing(false);
            }
        });
    }

    public Map<String, String> getTagRefresh() {

        String firstIdFeed;
        if (feedsAdapter.getFeeds().size() > 0) {
            firstIdFeed = feedsAdapter.getFeeds().get(0).getFeedId();
        } else {
            firstIdFeed = AppConstant.DEFAULT_FEED_ID;
        }
        Map<String, String> tagRefresh = new HashMap<>();
        tagRefresh.put(AppConstant.CURRENT_FEED_ID_TAG, firstIdFeed);
        tagRefresh.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));
        return tagRefresh;
    }

    public Map<String, String> getTagLoadMore() {

        String afterFeedId;
        Map<String, String> tagLoadMore = new HashMap<>();
        if (!StringUtil.isEmpty(pagingLoadNewFeed.getAfter())) {
            afterFeedId = pagingLoadNewFeed.getAfter();
        } else {
            afterFeedId = String.valueOf(AppConstant.DEFAULT_FEED_ID);
        }
        tagLoadMore.put(AppConstant.CURRENT_FEED_ID_TAG, afterFeedId);
        tagLoadMore.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));
        return tagLoadMore;
    }

    public void addDataToFirstList(FeedResponse dataAdd) {

        if (dataAdd != null && dataAdd.getSuccess() == AppConstant.SUCCESS) {
            ArrayList<Feed> feedAdds = dataAdd.getData();
            if (feedAdds != null && feedAdds.size() > 0) {
                if (feedsAdapter.getFeeds().size() == 0) {

                    pagingLoadNewFeed.setAfter(dataAdd.getPaging().getAfter());
                    pagingLoadNewFeed.setBefore(dataAdd.getPaging().getBefore());
                    visibleControl(View.VISIBLE, View.GONE);
                }
                feedsAdapter.insertItems(feedAdds, 0);
                rvFeeds.scrollToPosition(0);

            } else if (feedsAdapter.getFeeds().size() == 0) {
                visibleControl(View.GONE, View.VISIBLE);
            }
        }
    }

    public void handleClickVideo(int posFeed) {

        Feed feed = feedsAdapter.getFeeds().get(posFeed);
        Intent intent = new Intent(mActivity, VideoActivity.class);
        Bundle data = new Bundle();
        data.putParcelable(AppConstant.FEED_TAG, feed);
        intent.putExtra(AppConstant.PACKAGE, data);
        mActivity.startActivity(intent);

    }

    @OnClick(R.id.btn_reload)
    public void onClickReload(View view) {

        if (Utils.checkInternetAvailable()) {
            visibleControl(View.GONE, View.GONE);
            pgbReload.setVisibility(View.VISIBLE);
            handleReload();
        } else {
            Utils.toastShort(mActivity, R.string.no_network);
        }
    }

    public void handleReload() {

        Call<FeedResponse> call = ConfigNetwork.getServerAPI().callRefreshNewFeed(getTagRefresh());
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                pgbReload.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    addDataToFirstList(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                pgbReload.setVisibility(View.GONE);
                visibleControl(View.GONE, View.VISIBLE);
            }
        });
    }

    public void visibleControl(int refreshVisibility, int errorVisibility) {
        mRefreshNewFeed.setVisibility(refreshVisibility);
        llErrorLoadFeed.setVisibility(errorVisibility);
    }


}
