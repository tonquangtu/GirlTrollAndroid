package com.bk.girltrollsv.adapter.customadapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.Catalog;

import java.util.ArrayList;

/**
 * Created by trung on 14/10/2016.
 */
public class ViewCatalogAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Catalog> mCatalogList;

    public ViewCatalogAdapter(Context context, ArrayList<Catalog> catalogList){

        this.mContext = context;
        this.mCatalogList = catalogList;
        this.mLayoutInflater = ((Activity)context).getLayoutInflater();

    }

    @Override
    public int getCount() {
        return mCatalogList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCatalogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_catalog_view, null);
            holder = new ViewHolder();
            holder.ivItemCatalog = (ImageView) convertView.findViewById(R.id.iv_item_catalog);
            holder.tvItemCatalog = (TextView) convertView.findViewById(R.id.tv_item_catalog);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Catalog catalog = this.mCatalogList.get(position);
        if (convertView == null){

            Log.d("trung", "null");
        }
        holder.tvItemCatalog.setText(catalog.getCatalogName());
        holder.ivItemCatalog.setImageResource(catalog.getCatalogImage());

        return convertView;
    }

    static class ViewHolder {
        ImageView ivItemCatalog;
        TextView tvItemCatalog;
    }
}
