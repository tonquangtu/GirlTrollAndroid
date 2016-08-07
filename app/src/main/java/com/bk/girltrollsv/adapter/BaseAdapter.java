package com.bk.girltrollsv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.bk.girltrollsv.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hex0r on 10/9/15.
 */
public abstract class BaseAdapter<D> extends LoadMoreAdapterWithObject<D> {

    public static final int THRESHOLD = 200;
    public List<D> dataSet;
    protected Context context;
    public int addMoreItem = 0;

    public void setAddMoreItem(int addMoreItem) {
        this.addMoreItem = addMoreItem;
    }

    public BaseAdapter(Context context) {
        this(context, null);
    }

    public BaseAdapter(Context context, List<D> initData) {
        super(context);

        this.dataSet = new ArrayList<>();
        this.context = context;
        if (initData != null && initData.size() > 0) {
            loadInitialDataSet(initData);
        }
    }

    public void loadInitialDataSet(final List<D> initialDataSet) {
        addMoreData(initialDataSet);
    }

    public void loadInitialDataSetTest(final List<D> initialDataSet) {
        addMoreDataTest(initialDataSet);
    }

    @Override
    public int getDefaultItemCount() {
        return dataSet == null ? 0 : dataSet.size() + addMoreItem;
    }

    @Override
    public void loadMore(final List<D> moreList) {

        final int currentSize = dataSet.size();
        final long start = System.currentTimeMillis();

        dataSet.addAll(moreList);
        if (dataSet.size() != currentSize) {
            if (System.currentTimeMillis() - start > THRESHOLD) { //likely to have loaded in background
                notifyItemRangeInserted(currentSize + 1, dataSet.size() - currentSize);
            } else {
                Utils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemRangeInserted(currentSize + 1, dataSet.size() - currentSize);
                    }
                }, THRESHOLD);
            }
        }
    }

    public void addMoreData(List<D> moreItems) {
        int startPosition = getDefaultItemCount();
        this.dataSet.addAll(moreItems);
        this.notifyItemRangeInserted(startPosition, moreItems.size());
    }

    public void addMoreDataTest(List<D> moreItems) {
        int startPosition = getDefaultItemCount();
        this.dataSet = moreItems;
        this.notifyItemRangeInserted(startPosition, moreItems.size());
    }

    public void enableItemMore(int offset, List<D> moreItems) {
        dataSet.addAll(moreItems);
        final int currentSize = dataSet.size();
        if (currentSize < offset) {
            addMoreItem = 0;
        } else {
            addMoreItem = 1;
        }
        notifyDataSetChanged();
    }

    public boolean isShowLoadMore = false;

    public void addMarginBottom(int offset, List<D> moreItems) {
        dataSet.addAll(moreItems);
        final int currentSize = dataSet.size();
        addMoreItem = 1;
        if (currentSize < offset) {
            isShowLoadMore = true;
        } else {
            isShowLoadMore = false;
        }
        notifyDataSetChanged();
    }

    public void addMarginBottomTese(int offset, List<D> moreItems) {
        dataSet = moreItems;
        final int currentSize = dataSet.size();
        addMoreItem = 1;
        if (currentSize < offset) {
            isShowLoadMore = true;
        } else {
            isShowLoadMore = false;
        }
        notifyDataSetChanged();
    }
    public int getListSize() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void addMoreDataNoAnim(List<D> moreItems) {
        this.dataSet.addAll(moreItems);
        this.notifyDataSetChanged();
    }

    public void refreshData() {
        this.dataSet.clear();
        addMoreItem = 0;
        this.notifyDataSetChanged();
    }

    public void addItem(D item, int position) {
        this.dataSet.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        this.dataSet.remove(position);
        notifyItemRemoved(position);
    }

    public List<D> getDataSet() {
        if (dataSet == null) {
            return new ArrayList<>();
        }
        return dataSet;
    }

    @Override
    protected RecyclerView.ViewHolder onDefaultCreateViewHolder(ViewGroup parent, int viewType) {
        return onActualCreateViewHolder(parent, viewType);
    }

    @Override
    protected void onDefaultBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onActualBindViewHolder(holder, position);
    }

    protected abstract void onActualBindViewHolder(RecyclerView.ViewHolder holder, int position);

    protected abstract RecyclerView.ViewHolder onActualCreateViewHolder(ViewGroup parent, int viewType);

    public D getItem(int position) {
        return dataSet.get(position);
    }

    public boolean isEmpty() {
        return getDefaultItemCount() == 0;
    }
}
