package com.bk.girltrollsv.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.girltrollsv.adapter.viewholder.DefaultLoadMoreViewHolder;

/**
 * Created by Dell on 18-Aug-16.
 */
public abstract class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_LOAD_MORE_TYPE = 0;
    public static final int ITEM_DEFAULT = 1;

    private Context context;

    public LoadMoreAdapter(Context context) {
       this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (isLoadMoreItem(position)) {
            return ITEM_LOAD_MORE_TYPE;
        } else {
            return ITEM_DEFAULT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_LOAD_MORE_TYPE) {
            return getLoadMoreViewHolder(parent);

        } else {
            return onDefaultCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DefaultLoadMoreViewHolder) {
            onBindLoadMoreViewHolder(holder, position);

        } else {
            onBindDefaultViewHolder(holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return totalItem();
    }

    public RecyclerView.ViewHolder getLoadMoreViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(DefaultLoadMoreViewHolder.LAYOUT_ID, parent, false);
        return new DefaultLoadMoreViewHolder(view);
    }

    protected void onBindLoadMoreViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract boolean isLoadMoreItem(int position);

    public abstract RecyclerView.ViewHolder onDefaultCreateViewHolder(ViewGroup parent, int viewType);

    public abstract int totalItem();
}
