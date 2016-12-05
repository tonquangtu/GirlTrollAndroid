package com.bk.girltrollsv.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.ViewCatalogAdapter;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.constant.Catalog;
import com.bk.girltrollsv.constant.CatalogList;
import com.bk.girltrollsv.ui.activity.HotActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by trung on 13/10/2016.
 */
public class CatalogFragment extends BaseFragment {

    @Bind(R.id.gvCatalog)
    GridView mGVCatalog;

    @Bind(R.id.toolbar_tv_title_center)
    TextView tvTitle;

    Activity mActivity;

    public static CatalogFragment newInstance(){

        CatalogFragment catalogFragment = new CatalogFragment();

        return catalogFragment;
    }

    @Override
    protected void initView() {

        mActivity = getActivity();

        tvTitle.setText(getResources().getString(R.string.menu));

        ArrayList<Catalog> catalogList = CatalogList.getListCatalog();

        mGVCatalog.setAdapter(new ViewCatalogAdapter(mActivity, catalogList));

        // Khi người dùng click vào các GridItem
        mGVCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = mGVCatalog.getItemAtPosition(position);
                Catalog catalog = (Catalog) o;

                switch (position) {

                    case CatalogList.HOT_PHOTO_POS :
                        handleViewHotPhoto();
                        break;
                    case CatalogList.HOT_VIDEO_POS:
                        handleViewHotVideo();
                        break;
                }
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catalog;
    }

    public void handleViewHotPhoto() {

        Intent hotPhotoIntent = new Intent(mActivity, HotActivity.class);
        hotPhotoIntent.putExtra(AppConstant.TYPE_VIEW_HOT, AppConstant.HOT_PHOTO);
        mActivity.startActivity(hotPhotoIntent);
    }

    public void handleViewHotVideo() {
        Intent hotPhotoIntent = new Intent(mActivity, HotActivity.class);
        hotPhotoIntent.putExtra(AppConstant.TYPE_VIEW_HOT, AppConstant.HOT_VIDEO);
        mActivity.startActivity(hotPhotoIntent);
    }
}
