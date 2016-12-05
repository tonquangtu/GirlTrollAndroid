package com.bk.girltrollsv.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.RvFeedsAdapter;
import com.bk.girltrollsv.callback.FeedItemOnClickListener;
import com.bk.girltrollsv.callback.HidingScrollListener2;
import com.bk.girltrollsv.callback.IDetailImage;
import com.bk.girltrollsv.callback.OnLoadMoreListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.customview.DetailImageView;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.dataserver.FeedResponse;
import com.bk.girltrollsv.networkconfig.ConfigNetwork;
import com.bk.girltrollsv.util.AccountUtil;
import com.bk.girltrollsv.util.LikeCommentShareUtil;
import com.bk.girltrollsv.util.SpaceItem;
import com.bk.girltrollsv.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotActivity extends BaseActivity implements IDetailImage {

    @Bind(R.id.rvFeeds)
    RecyclerView rvFeeds;

    @Bind(R.id.swipe_refresh_layout_new_feed)
    SwipeRefreshLayout mRefreshNewFeed;

    @Bind(R.id.ll_error_load_feed)
    LinearLayout llErrorLoadFeed;

    @Bind(R.id.pgbReload)
    ProgressBar pgbReload;

    @Bind(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.rl_view_image)
    RelativeLayout rlViewImage;

    @Bind(R.id.img_back_activity)
    View mBtnBackActivity;

    @Bind(R.id.txt_title_activity)
    TextView mTxtTitleActivity;

    RvFeedsAdapter feedsAdapter;

    DetailImageView mDetailImageView;

    LinearLayoutManager layoutManager;

    boolean isRefreshing = false;

    private int mType;

    private int mLimit = AppConstant.DEFAULT_LIMIT;

    @Override
    public void handleIntent(Intent intent) {

        mType = intent.getIntExtra(AppConstant.TYPE_VIEW_HOT, AppConstant.HOT_PHOTO);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_hot_photo;
    }

    @Override
    public void initView() {

        initTitle();
        initProgressBar();
        initRv();
        initRefreshLayout();
        firstLoadHotFeed();

    }

    public void initTitle() {

        if (mType == AppConstant.HOT_PHOTO) {
            mTxtTitleActivity.setText(AppConstant.HOT_PHOTO_TITLE);
        } else {
            mTxtTitleActivity.setText(AppConstant.HOT_VIDEO_TITLE);
        }

        mBtnBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotActivity.this.finish();
            }
        });
    }

    public void initProgressBar() {
        pgbReload.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.color_progress_bar),
                PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void initData() {

    }

    public void initRv() {

        int spaceBetweenItems = 15;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItem spaceItem = new SpaceItem(spaceBetweenItems, SpaceItem.VERTICAL);
        rvFeeds.setLayoutManager(layoutManager);
        rvFeeds.addItemDecoration(spaceItem);

        // must set adapter after setLayoutManager
        feedsAdapter = new RvFeedsAdapter(this, rvFeeds, null);
        rvFeeds.setAdapter(feedsAdapter);
        feedsAdapter.setLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreHotFeed();
            }
        });
        feedsAdapter.setItemListener(new FeedItemOnClickListener() {
            @Override
            public void onClickImage(int posFeed, int posImage, ImageView[] views) {

                Feed feed = feedsAdapter.getFeeds().get(posFeed);
                viewDetailImage(posImage, feed, views);
            }

            @Override
            public void onClickVideo(int posFeed, View view) {
                handleClickVideo(posFeed);
            }

            @Override
            public void onClickLike(int posFeed, View viewLike, TextView txtNumLike) {
                Feed feed = feedsAdapter.getFeeds().get(posFeed);
                LikeCommentShareUtil.handleClickLike(HotActivity.this, feed, viewLike, txtNumLike, R.drawable.icon_unlike);
            }


            @Override
            public void onClickComment(int posFeed, View view) {
                Feed feed = feedsAdapter.getFeeds().get(posFeed);
                LikeCommentShareUtil.handleClickComment(HotActivity.this, feed, view);
            }

            @Override
            public void onClickMore(int posFeed, View view) {
                handleClickMore(posFeed, view);

            }
        });

        rvFeeds.addOnScrollListener(new HidingScrollListener2(mAppBarLayout));

    }

    public void initRefreshLayout() {
        mRefreshNewFeed.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshNewFeed.setProgressViewOffset(true, 100, 260);
        mRefreshNewFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handleRefreshNewFeed();
            }
        });

    }

    public void handleRefreshNewFeed() {

//        if (!isRefreshing) {
//            if (Utils.checkInternetAvailable()) {
//                refreshNewFeed();
//            } else {
//                Utils.toastShort(this, R.string.no_network);
//                mRefreshNewFeed.setRefreshing(false);
//            }
//        } else {
//            mRefreshNewFeed.setRefreshing(false);
//        }

        mRefreshNewFeed.setRefreshing(false);
    }

    public void refreshNewFeed() {

        isRefreshing = true;
        Call<FeedResponse> call = ConfigNetwork.serviceAPI.callRefreshNewFeed(getTagRefresh());
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

        Map<String, String> tagRefresh = new HashMap<>();
        String memberId = AccountUtil.getAccountId();
        if (memberId == null) {
            memberId = "";
        }

        String firstFeedId = feedsAdapter.getFirstFeedId() + "";
        tagRefresh.put(AppConstant.CURRENT_FEED_ID_TAG, firstFeedId);
        tagRefresh.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));
        tagRefresh.put(AppConstant.TYPE, mType + "");
        tagRefresh.put(AppConstant.MEMBER_ID, memberId);
        return tagRefresh;
    }

    public void addDataToFirstList(FeedResponse dataAdd) {

        if (dataAdd != null ) {
            ArrayList<Feed> feedAdds = dataAdd.getData();
            if (feedAdds != null) {

                if (feedAdds.size() == 0) {
                    Utils.toastShort(this, AppConstant.NO_DATA);
                } else {
                    if (feedsAdapter.totalItem() == 0) {
                        setVisibleErrorView(false);
                        setVisibleHotFeedList(true);
                    }
                    feedsAdapter.insertItems(feedAdds, 0);
                    layoutManager.smoothScrollToPosition(rvFeeds, null, 0);
                }

            } else if (feedsAdapter.totalItem() == 0) {
                setVisibleErrorView(true);
                setVisibleHotFeedList(false);
            }
        }
    }

    public void firstLoadHotFeed() {

        setVisibleProgress(true);
        setVisibleHotFeedList(false);
        setVisibleErrorView(false);
        Call<FeedResponse> call = ConfigNetwork.serviceAPI.callLoadHot(getTagRefresh());
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                setVisibleProgress(false);
                if (response.isSuccessful()) {
                    addDataToFirstList(response.body());
                } else {
                    setVisibleErrorView(true);
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                setVisibleProgress(false);
                setVisibleHotFeedList(false);
                setVisibleErrorView(true);
                Log.e(AppConstant.LOG_TAG, "loi");
            }
        });
    }

    public void setVisibleHotFeedList(boolean visible) {

        if (visible) {
            if (mRefreshNewFeed.getVisibility() != View.VISIBLE)
                mRefreshNewFeed.setVisibility(View.VISIBLE);
//            rvFeeds.setVisibility(View.VISIBLE);
        } else {
            if (mRefreshNewFeed.getVisibility() == View.VISIBLE)
                mRefreshNewFeed.setVisibility(View.GONE);
//            rvFeeds.setVisibility(View.GONE);
        }
    }

    public void setVisibleProgress(boolean visible) {

        if (visible) {
            if (pgbReload.getVisibility() != View.VISIBLE)
                pgbReload.setVisibility(View.VISIBLE);

        } else {
            if (pgbReload.getVisibility() == View.VISIBLE)
                pgbReload.setVisibility(View.GONE);
        }
    }

    public void setVisibleErrorView(boolean visible) {
        if (visible) {
            if (llErrorLoadFeed.getVisibility() != View.VISIBLE)
                llErrorLoadFeed.setVisibility(View.VISIBLE);
        } else {
            if (llErrorLoadFeed.getVisibility() == View.VISIBLE)
                llErrorLoadFeed.setVisibility(View.GONE);
        }
    }


    public void loadMoreHotFeed() {

        feedsAdapter.addProgressLoadMore();
        Call<FeedResponse> call = ConfigNetwork.serviceAPI.callLoadHot(getTagLoadMore());
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {

                feedsAdapter.removeProgressLoadMore();
                if (response.isSuccessful() && response.body() != null) {
                    feedsAdapter.insertLastItems(response.body().getData());
                }
                feedsAdapter.endLoadingMore();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                feedsAdapter.removeProgressLoadMore();
                feedsAdapter.endLoadingMore();
            }
        });
    }

    public Map<String, String> getTagLoadMore() {

        Map<String, String> tagLoadMore = new HashMap<>();
        String memberId = AccountUtil.getAccountId();
        if (memberId == null) {
            memberId = "";
        }

        String lastFeedId = String.valueOf(feedsAdapter.getLastFeedId());
        tagLoadMore.put(AppConstant.CURRENT_FEED_ID_TAG, lastFeedId);
        tagLoadMore.put(AppConstant.LIMIT_TAG, String.valueOf(AppConstant.DEFAULT_LIMIT));
        tagLoadMore.put(AppConstant.TYPE, mType + "");
        tagLoadMore.put(AppConstant.MEMBER_ID, memberId);
        return tagLoadMore;
    }

    public void handleClickVideo(int posFeed) {

        Feed feed = feedsAdapter.getFeeds().get(posFeed);
        Intent intent = new Intent(this, VideoActivity.class);
        Bundle data = new Bundle();
        data.putParcelable(AppConstant.FEED_TAG, feed);
        intent.putExtra(AppConstant.PACKAGE, data);
        startActivity(intent);
    }

    public void handleClickMore(int posFeed, View view) {

        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
        popup.show();
    }

//    public void visibleControl(int refreshVisibility, int errorVisibility) {
//        mRefreshNewFeed.setVisibility(refreshVisibility);
//        llErrorLoadFeed.setVisibility(errorVisibility);
//    }

    public void viewDetailImage(int posImage, Feed feed, ImageView[] views) {

        DetailImageView detailImageView = getDetailImageView();
        detailImageView.viewDetailImage(this.getSupportFragmentManager(), feed, posImage, views);
    }
//

    public DetailImageView getDetailImageView() {

        if (mDetailImageView == null) {

            mDetailImageView = new DetailImageView(rlViewImage, this);

        }
        return mDetailImageView;
    }

    @OnClick(R.id.btn_reload)
    public void onClickReload(View view) {

        if (Utils.checkInternetAvailable()) {
            firstLoadHotFeed();
        } else {
            Utils.toastShort(this, R.string.no_network);
        }
    }
}
