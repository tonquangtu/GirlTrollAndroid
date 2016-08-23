package com.bk.girltrollsv.adapter.customadapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.baseadapter.LoadMoreAdapter;
import com.bk.girltrollsv.adapter.viewholder.FeedViewHolder;
import com.bk.girltrollsv.adapter.viewholder.FourImageFeedViewHolder;
import com.bk.girltrollsv.adapter.viewholder.OneImageFeedViewHolder;
import com.bk.girltrollsv.adapter.viewholder.ThreeImageFeedViewHolder;
import com.bk.girltrollsv.adapter.viewholder.TwoImageFeedViewHolder;
import com.bk.girltrollsv.adapter.viewholder.VideoViewHolder;
import com.bk.girltrollsv.callback.OnLoadMoreListener;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.util.Utils;

import java.util.ArrayList;

/**
 * Created by Dell on 18-Aug-16.
 */
public class RVFeedsAdapter extends LoadMoreAdapter {

    ArrayList<Feed> feeds;

    Activity mActivity;

    RecyclerView mRecyclerView;

    OnLoadMoreListener onLoadMoreListener;

    boolean isLoadingMore = false;

    int visibleThreshold = 2;

    public static int DISTANCE_SWIPE = 40;

    public static final int TIME_THRESHOLD = 200;

    public static final int ITEM_VIDEO_FEED = 2;

    public static final int ITEM_HAVE_ONE_IMAGE = 3;

    public static final int ITEM_HAVE_TWO_IMAGE = 4;

    public static final int ITEM_HAVE_THREE_IMAGE = 5;

    public static final int ITEM_HAVE_FOUR_IMAGE = 6;

    public RVFeedsAdapter(Activity activity, RecyclerView recyclerView, ArrayList<Feed> initFeeds) {

        super(activity);
        this.mActivity = activity;
        this.mRecyclerView = recyclerView;
        feeds = new ArrayList<>();
        if (initFeeds != null && initFeeds.size() > 0) {
            feeds.addAll(initFeeds);
        }

        initLoadMoreEvent();
    }

    @Override
    public int getItemViewType(int position) {

        if (isLoadMoreItem(position)) {
            return ITEM_LOAD_MORE_TYPE;

        } else {

            ArrayList<ImageInfo> images = feeds.get(position).getImages();
            if (images != null && images.size() > 0) {
                int size = images.size();
                if (size == 1) {
                    return ITEM_HAVE_ONE_IMAGE;

                } else if (size == 2) {
                    return ITEM_HAVE_TWO_IMAGE;

                } else if (size == 3) {
                    return ITEM_HAVE_THREE_IMAGE;

                } else {
                    return ITEM_HAVE_FOUR_IMAGE;
                }

            } else if (feeds.get(position).getVideo() != null) {
                return ITEM_VIDEO_FEED;

            } else {
                return ITEM_DEFAULT;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onDefaultCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mActivity).inflate(R.layout.card_view_feed, parent, false);

        if (viewType == ITEM_HAVE_ONE_IMAGE) {
            return new OneImageFeedViewHolder(view, mActivity);

        } else if (viewType == ITEM_HAVE_TWO_IMAGE) {
            return new TwoImageFeedViewHolder(view, mActivity);

        } else if (viewType == ITEM_HAVE_THREE_IMAGE) {
            return new ThreeImageFeedViewHolder(view, mActivity);

        } else if (viewType == ITEM_HAVE_FOUR_IMAGE) {
            return new FourImageFeedViewHolder(view, mActivity);

        } else if (viewType == ITEM_VIDEO_FEED) {
            return new VideoViewHolder(view, mActivity);

        } else {
            return new FeedViewHolder(view, mActivity);
        }

    }

    @Override
    public void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {

        Feed feed = feeds.get(position);
        if (holder instanceof OneImageFeedViewHolder) {
            ((OneImageFeedViewHolder) holder).populate(feed);

        } else if (holder instanceof TwoImageFeedViewHolder) {
            ((TwoImageFeedViewHolder) holder).populate(feed);

        } else if (holder instanceof ThreeImageFeedViewHolder) {
            ((ThreeImageFeedViewHolder) holder).populate(feed);

        } else if (holder instanceof FourImageFeedViewHolder) {
            ((FourImageFeedViewHolder) holder).populate(feed);

        } else if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).populate(feed);

        } else {
            ((FeedViewHolder) holder).populate(feed);
        }
    }

    @Override
    public boolean isLoadMoreItem(int position) {

        if (feeds.get(position) == null) {
            return true;
        }
        return false;
    }

    @Override
    public int totalItem() {
        return feeds.size();
    }

    public ArrayList<Feed> getFeeds() {
        return feeds;
    }

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void endLoadingMore() {
        isLoadingMore = false;
    }

    public void initLoadMoreEvent() {

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoadingMore) {
                    isLoadingMore = true;
                    int totalItem = totalItem();
                    int posLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if ((posLastVisibleItem + visibleThreshold) >= totalItem
                            && dy > DISTANCE_SWIPE
                            && onLoadMoreListener != null) {

                        onLoadMoreListener.onLoadMore();
                    } else {
                        isLoadingMore = false;
                    }
                }
            }
        });
    }


    public void insertLastItems(final ArrayList<Feed> moreFeeds) {

        if (moreFeeds != null && moreFeeds.size() > 0) {
            final int currentSize = totalItem();
            long start = System.currentTimeMillis();

            feeds.addAll(moreFeeds);
            if (System.currentTimeMillis() - start > TIME_THRESHOLD) {
                notifyItemRangeInserted(currentSize, moreFeeds.size());

            } else {
                Utils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemRangeInserted(currentSize, moreFeeds.size());
                    }
                }, TIME_THRESHOLD);
            }
        }
    }

    public void insertLastItem(Feed feed) {

        feeds.add(feed);
        notifyItemInserted(totalItem() - 1);
    }


    public void removeLastItem() {

        feeds.remove(totalItem() - 1);
        notifyItemRemoved(totalItem());
    }


}
