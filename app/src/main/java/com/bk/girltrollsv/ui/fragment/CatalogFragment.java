package com.bk.girltrollsv.ui.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.ViewCatalogAdapter;
import com.bk.girltrollsv.constant.Catalog;
import com.bk.girltrollsv.constant.CatalogList;

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

    Activity mMainActivity;

    public static CatalogFragment newInstance(){

        CatalogFragment catalogFragment = new CatalogFragment();

        return catalogFragment;
    }

    @Override
    protected void initView() {

        mMainActivity = getActivity();

        tvTitle.setText(getResources().getString(R.string.menu));

        ArrayList<Catalog> catalogList = CatalogList.getListCatalog();

        mGVCatalog.setAdapter(new ViewCatalogAdapter(mMainActivity, catalogList));

        // Khi người dùng click vào các GridItem
        mGVCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = mGVCatalog.getItemAtPosition(position);
                Catalog catalog = (Catalog) o;
                Toast.makeText(mMainActivity, "Selected :"
                        + " " + catalog.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catalog;
    }
}
