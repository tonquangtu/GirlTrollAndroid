package com.bk.girltrollsv.adapter.customadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.baseadapter.LoadMoreAdapter;
import com.bk.girltrollsv.adapter.viewholder.FeedViewHolder;
import com.bk.girltrollsv.model.Feed;

import java.util.ArrayList;

/**
 * Created by Dell on 18-Aug-16.
 */
public class RVFeedsAdapter extends LoadMoreAdapter {

    ArrayList<Feed> feeds;

    Context context;

    public RVFeedsAdapter(Context context, ArrayList<Feed> initFeeds) {

        super(context);
        this.context = context;
        feeds = new ArrayList<>();
        if (initFeeds != null && initFeeds.size() > 0) {
            feeds.addAll(initFeeds);
        }
    }


    @Override
    public RecyclerView.ViewHolder onDefaultCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_view_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FeedViewHolder) {
            FeedViewHolder feedHolder = (FeedViewHolder) holder;

            if (feeds.get(position) != null) {
                feedHolder.populate(feeds.get(position));
            }
        }
    }

    @Override
    public boolean isLoadMoreItem(int position) {

        if (position == totalItem() - 1 && feeds.get(position) == null) {
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
}
