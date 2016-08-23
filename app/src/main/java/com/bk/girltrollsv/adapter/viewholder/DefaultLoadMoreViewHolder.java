package com.bk.girltrollsv.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bk.girltrollsv.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by hex0r on 10/9/15.
 */
public class DefaultLoadMoreViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT_ID = R.layout.load_more;

    @Bind(R.id.pb_loader)
    ProgressBar pgLoader;


    public DefaultLoadMoreViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        pgLoader.setIndeterminate(true);
    }

}
