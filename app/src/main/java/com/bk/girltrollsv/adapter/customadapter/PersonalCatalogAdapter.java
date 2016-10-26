package com.bk.girltrollsv.adapter.customadapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.viewholder.OnePersonalCatalogViewHolder;
import com.bk.girltrollsv.adapter.viewholder.ThreePersonalCatalogViewHolder;
import com.bk.girltrollsv.adapter.viewholder.TwoPersonalCatalogViewHolder;
import com.bk.girltrollsv.callback.OnPersonalCatalogItemClickListener;
import com.bk.girltrollsv.constant.PersonalCatalogItem;

import java.util.ArrayList;

/**
 * Created by trung on 20/10/2016.
 */
public class PersonalCatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_HAVE_ONE_CATALOG = 1;
    public static final int ITEM_HAVE_TWO_CATALOG = 2;
    public static final int ITEM_HAVE_THREE_CATALOG = 3;
    OnPersonalCatalogItemClickListener listener;

    Activity mActivity;
    ArrayList<PersonalCatalogItem> mPersonalCatalogItemList;

    public PersonalCatalogAdapter(Activity activity, ArrayList<PersonalCatalogItem> personalCatalogItemList){

        mActivity = activity;
        mPersonalCatalogItemList = personalCatalogItemList;
    }

    @Override
    public int getItemViewType(int position) {

        PersonalCatalogItem personalCatalogItem = mPersonalCatalogItemList.get(position);
        int typeItem = personalCatalogItem.getTypeItem();

        if(typeItem == 3){

            return ITEM_HAVE_THREE_CATALOG;
        }

        else if (typeItem == 2){

            return ITEM_HAVE_TWO_CATALOG;
        }
        else {

            return ITEM_HAVE_ONE_CATALOG;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);

        View view = null;

        if (viewType == ITEM_HAVE_THREE_CATALOG){

            view = layoutInflater.inflate(R.layout.three_item_catalog_personal,parent,false);
            ThreePersonalCatalogViewHolder holder = new ThreePersonalCatalogViewHolder(view);
            holder.setListener(listener);
            return holder;
        }
        else if (viewType == ITEM_HAVE_TWO_CATALOG) {

            view = layoutInflater.inflate(R.layout.two_item_catalog_personal,parent,false);
            TwoPersonalCatalogViewHolder holder = new TwoPersonalCatalogViewHolder(view);
            holder.setListener(listener);
            return holder;
        }
        else {

            view = layoutInflater.inflate(R.layout.one_item_catalog_personal,parent,false);
            OnePersonalCatalogViewHolder holder = new OnePersonalCatalogViewHolder(view);
            holder.setListener(listener);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PersonalCatalogItem personalCatalogItem = mPersonalCatalogItemList.get(position);

        if (holder instanceof ThreePersonalCatalogViewHolder){

            ((ThreePersonalCatalogViewHolder)holder).populate(personalCatalogItem);
        }
        else if (holder instanceof TwoPersonalCatalogViewHolder){

            ((TwoPersonalCatalogViewHolder)holder).populate(personalCatalogItem);
        }
        else {

            ((OnePersonalCatalogViewHolder)holder).populate(personalCatalogItem);
        }
    }

    public void setOnItemClickListener(OnPersonalCatalogItemClickListener listener){

        this.listener = listener;
    }

    public void addLogOutPersonal(PersonalCatalogItem personalLogOutItem){

        mPersonalCatalogItemList.add(personalLogOutItem);
        notifyItemInserted(mPersonalCatalogItemList.size() - 1);
    }

    public void removeLogOutPersonal(){

        mPersonalCatalogItemList.remove(mPersonalCatalogItemList.size() - 1);
        notifyItemRemoved(mPersonalCatalogItemList.size());
    }



    @Override
    public int getItemCount() {
        return mPersonalCatalogItemList.size();
    }
}
