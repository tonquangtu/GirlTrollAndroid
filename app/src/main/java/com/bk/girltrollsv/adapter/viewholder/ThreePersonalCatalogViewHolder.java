package com.bk.girltrollsv.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.callback.OnPersonalCatalogItemClickListener;
import com.bk.girltrollsv.constant.PersonalCatalog;
import com.bk.girltrollsv.constant.PersonalCatalogItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by trung on 20/10/2016.
 */
public class ThreePersonalCatalogViewHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.ll_item_catalog_personal1)
    LinearLayout llItemCatalogPersonal1;

    @Bind(R.id.ll_item_catalog_personal2)
    LinearLayout llItemCatalogPersonal2;

    @Bind(R.id.ll_item_catalog_personal3)
    LinearLayout llItemCatalogPersonal3;

    @Bind(R.id.img_item_catalog_personal1)
    ImageView imgItemCatalogPersonal1;

    @Bind(R.id.img_item_catalog_personal2)
    ImageView imgItemCatalogPersonal2;

    @Bind(R.id.img_item_catalog_personal3)
    ImageView imgItemCatalogPersonal3;

    @Bind(R.id.txt_item_catalog_personal1)
    TextView txtItemCatalogPersonal1;

    @Bind(R.id.txt_item_catalog_personal2)
    TextView txtItemCatalogPersonal2;

    @Bind(R.id.txt_item_catalog_personal3)
    TextView txtItemCatalogPersonal3;

    OnPersonalCatalogItemClickListener mListener;



    public ThreePersonalCatalogViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        initListen();
    }

    public void populate(PersonalCatalogItem personalCatalogItem){

        ArrayList<PersonalCatalog> personalCatalogList = personalCatalogItem.getList();

        PersonalCatalog personalCatalog = personalCatalogList.get(0);
        txtItemCatalogPersonal1.setText(personalCatalog.getCatalogName());
        imgItemCatalogPersonal1.setImageResource(personalCatalog.getCatalogImage());


        personalCatalog = personalCatalogList.get(1);
        txtItemCatalogPersonal2.setText(personalCatalog.getCatalogName());
        imgItemCatalogPersonal2.setImageResource(personalCatalog.getCatalogImage());

        personalCatalog = personalCatalogList.get(2);
        txtItemCatalogPersonal3.setText(personalCatalog.getCatalogName());
        imgItemCatalogPersonal3.setImageResource(personalCatalog.getCatalogImage());


    }

    public void setListener(OnPersonalCatalogItemClickListener listener){

        this.mListener = listener;
    }

    public void initListen(){

        llItemCatalogPersonal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItemPersonalCatalog(txtItemCatalogPersonal1.getText().toString());
            }
        });

        llItemCatalogPersonal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItemPersonalCatalog(txtItemCatalogPersonal2.getText().toString());
            }
        });

        llItemCatalogPersonal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItemPersonalCatalog(txtItemCatalogPersonal3.getText().toString());
            }
        });
    }






}
