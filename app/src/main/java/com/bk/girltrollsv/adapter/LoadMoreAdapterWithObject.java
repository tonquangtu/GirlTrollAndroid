package com.bk.girltrollsv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.girltrollsv.adapter.viewholder.DefaultLoadMoreViewHolder;
import com.bk.girltrollsv.util.DebugLog;

import java.util.List;

/**
 * Created by hex0r on 10/9/15.
 */
public abstract class LoadMoreAdapterWithObject<D> extends RecyclerView.Adapter {
    private final Context context;
    public int loadMoreViewType;

    public LoadMoreAdapterWithObject(Context context) {
        super();
        this.context = context;
    }

    public int getDefaultItemCount() {
        return 0;
    }
    public abstract boolean hasMoreData();

    @Override
    public int getItemCount() {
        return getDefaultItemCount() + (hasMoreData() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMoreData() && position == getDefaultItemCount()) {
            return loadMoreViewType;
        }

        int viewType = getDefaultItemViewType(position);

        if (viewType < 0) {
            throw new RuntimeException("viewType must be > 0");
        }

        if (viewType >= loadMoreViewType) {
            loadMoreViewType = viewType + 1;
        }

        return viewType;
    }

    /**
     *
     * @param position position in list
     * @return integer >= 0
     */
    protected int getDefaultItemViewType(int position) { return 0; }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (onDefaultCreateViewHolder(parent, viewType) == null) {
            DebugLog.e("onDefaultCreateViewHolder null");
        }
        return viewType == loadMoreViewType ? getLoadMoreViewHolder(parent) : onDefaultCreateViewHolder(parent, viewType);
    }

    protected RecyclerView.ViewHolder onDefaultCreateViewHolder(ViewGroup parent, int viewType) { return null; }

    protected RecyclerView.ViewHolder getLoadMoreViewHolder(ViewGroup parent) {
        //TODO: Use default holder
        final View view = LayoutInflater.from(context).inflate(DefaultLoadMoreViewHolder.LAYOUT_ID, parent, false);
        return new DefaultLoadMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (loadMoreViewType == getItemViewType(position)) {
            onBindLoadMoreViewHolder(holder, position);
        } else {
            onDefaultBindViewHolder(holder, position);
        }
    }

    protected void onDefaultBindViewHolder(RecyclerView.ViewHolder holder, int position) {}

    protected RecyclerView.ViewHolder onCreateLoadMoreViewHolder(ViewGroup parent) { return null; }

    protected void onBindLoadMoreViewHolder(RecyclerView.ViewHolder holder, int position) {}

    public abstract void loadMore(List<D> moreList);
}
