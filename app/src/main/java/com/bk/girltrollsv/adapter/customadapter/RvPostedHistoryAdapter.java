package com.bk.girltrollsv.adapter.customadapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.baseadapter.LoadMoreAdapter;
import com.bk.girltrollsv.adapter.viewholder.FourImagePostedHistoryVH;
import com.bk.girltrollsv.adapter.viewholder.OneImagePostedFeedVH;
import com.bk.girltrollsv.adapter.viewholder.PostedFeedViewHolder;
import com.bk.girltrollsv.adapter.viewholder.ThreeImagePostedFeedVH;
import com.bk.girltrollsv.adapter.viewholder.TwoImagePostedFeedVH;
import com.bk.girltrollsv.adapter.viewholder.VideoPostedFeedVH;
import com.bk.girltrollsv.callback.FeedItemOnClickListener;
import com.bk.girltrollsv.callback.OnLoadMoreListener;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.model.PostedFeed;

import java.util.ArrayList;

/**
 * Created by Dell on 02-Dec-16.
 */
public class RvPostedHistoryAdapter extends LoadMoreAdapter {

    ArrayList<PostedFeed> mPostedFeeds;

    Activity mActivity;

    RecyclerView mRecyclerView;

    OnLoadMoreListener onLoadMoreListener;

    FeedItemOnClickListener itemListener;

    boolean isLoadingMore = false;

    int visibleThreshold = 2;

    public static int DISTANCE_SWIPE = 40;

    public static final int TIME_THRESHOLD = 200;

    public static final int ITEM_VIDEO_FEED = 2;

    public static final int ITEM_HAVE_ONE_IMAGE = 3;

    public static final int ITEM_HAVE_TWO_IMAGE = 4;

    public static final int ITEM_HAVE_THREE_IMAGE = 5;

    public static final int ITEM_HAVE_FOUR_IMAGE = 6;

    public RvPostedHistoryAdapter(Activity activity, RecyclerView recyclerView, ArrayList<PostedFeed> initFeeds) {

        super(activity);
        this.mActivity = activity;
        this.mRecyclerView = recyclerView;
        mPostedFeeds = new ArrayList<>();
        if (initFeeds != null && initFeeds.size() > 0) {
            mPostedFeeds.addAll(initFeeds);
        }

        initLoadMoreEvent();
    }

    @Override
    public int getItemViewType(int position) {

        return isLoadMoreItem(position) ? ITEM_LOAD_MORE_TYPE : getNormalItemViewType(position);
    }

    public int getNormalItemViewType(int position) {

        ArrayList<ImageInfo> images = mPostedFeeds.get(position).getImages();
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

        } else if (mPostedFeeds.get(position).getVideo() != null) {
            return ITEM_VIDEO_FEED;

        } else {
            return ITEM_DEFAULT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onDefaultCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_uploading, parent, false);
        PostedFeedViewHolder holder;

        if (viewType == ITEM_HAVE_ONE_IMAGE) {
            holder = new OneImagePostedFeedVH(view, mActivity);

        } else if (viewType == ITEM_HAVE_TWO_IMAGE) {
            holder = new TwoImagePostedFeedVH(view, mActivity);

        } else if (viewType == ITEM_HAVE_THREE_IMAGE) {
            holder = new ThreeImagePostedFeedVH(view, mActivity);

        } else if (viewType == ITEM_HAVE_FOUR_IMAGE) {
            holder = new FourImagePostedHistoryVH(view, mActivity);

        } else if (viewType == ITEM_VIDEO_FEED) {
            holder = new VideoPostedFeedVH(view, mActivity);

        } else {
            holder = new PostedFeedViewHolder(view, mActivity);
        }
        holder.setListener(itemListener);
        return holder;
    }

    @Override
    public void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {

        PostedFeed feed = mPostedFeeds.get(position);
//        if (holder instanceof OneImagePostedFeedVH) {
//            ((OneImagePostedFeedVH) holder).populatePost(feed);
//
//        } else if (holder instanceof TwoImagePostedFeedVH) {
//            ((TwoImagePostedFeedVH) holder).populate(feed);
//
//        } else if (holder instanceof ThreeImagePostedFeedVH) {
//            ((ThreeImagePostedFeedVH) holder).populate(feed);
//
//        } else if (holder instanceof FourImagePostedHistoryVH) {
//            ((FourImagePostedHistoryVH) holder).populate(feed);
//
//        } else if (holder instanceof VideoPostedFeedVH) {
//            ((VideoPostedFeedVH) holder).populate(feed);
//
//        } else {
//            ((PostedFeedViewHolder) holder).populate(feed);
//        }
        ((PostedFeedViewHolder)holder).populatePost(feed);
    }

    @Override
    public boolean isLoadMoreItem(int position) {

        return mPostedFeeds.get(position) == null;
    }

    @Override
    public int totalItem() {
        return mPostedFeeds.size();
    }

    public ArrayList<PostedFeed> getFeeds() {
        return mPostedFeeds;
    }

    public void setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void endLoadingMore() {
        isLoadingMore = false;
    }

    public void addProgressLoadMore() {
        insertLastItem(null);
    }

    public void removeProgressLoadMore() {
        removeLastItem();
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


    public void insertLastItems(final ArrayList<PostedFeed> moreFeeds) {

        if (moreFeeds != null && moreFeeds.size() > 0) {
            final int currentSize = totalItem();
            mPostedFeeds.addAll(moreFeeds);
            notifyItemRangeInserted(currentSize, moreFeeds.size());
        }
    }

    public void insertLastItem(PostedFeed feed) {

        mPostedFeeds.add(feed);
        notifyItemInserted(totalItem() - 1);
    }


    public void removeLastItem() {

        mPostedFeeds.remove(totalItem() - 1);
        notifyItemRemoved(totalItem());
    }

    public void insertItems(ArrayList<PostedFeed> moreFeeds, int start) {

        if (moreFeeds != null && moreFeeds.size() > 0 && start >= 0 && start <= totalItem()) {
            mPostedFeeds.addAll(start, moreFeeds);
            notifyItemRangeInserted(start, moreFeeds.size());
        }
    }


    public void setItemListener(FeedItemOnClickListener itemListener) {
        this.itemListener = itemListener;
    }

    public int getLastFeedId() {

        int lastFeedId = -1;
        if (mPostedFeeds.size() > 0) {
            int size = mPostedFeeds.size();
            if (mPostedFeeds.get(size - 1) != null) {
                lastFeedId = mPostedFeeds.get(size - 1).getFeedId();
            } else if (size > 1) {
                lastFeedId = mPostedFeeds.get(size - 2).getFeedId();
            }
        }
        return lastFeedId;
    }


    public void updateUploadingPost(long postedCode, int status) {

        int indexPost = findPostByPostedCode(postedCode);
        if (indexPost != -1) {
            mPostedFeeds.get(indexPost).setUploadState(status);
            notifyItemChanged(indexPost);
        }
    }

    public int findPostByPostedCode(long postedCode) {

        int totalPost = totalItem();
        for (int i = 0; i < totalPost; i++) {
            if (postedCode == mPostedFeeds.get(i).getPostedCode()) {
                return i;
            }
        }
        return -1;
    }
}
