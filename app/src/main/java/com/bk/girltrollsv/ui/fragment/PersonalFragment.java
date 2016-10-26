package com.bk.girltrollsv.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.adapter.customadapter.PagerMainAdapter;
import com.bk.girltrollsv.adapter.customadapter.PersonalCatalogAdapter;
import com.bk.girltrollsv.callback.ConfirmDialogListener;
import com.bk.girltrollsv.callback.OnClickTabListener;
import com.bk.girltrollsv.callback.OnLoginCompletedListener;
import com.bk.girltrollsv.callback.OnPersonalCatalogItemClickListener;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.constant.PersonalCatalog;
import com.bk.girltrollsv.constant.PersonalCatalogItem;
import com.bk.girltrollsv.constant.PersonalCatalogItemList;
import com.bk.girltrollsv.dialog.ConfirmDialogFragment;
import com.bk.girltrollsv.ui.activity.LoginActivity;
import com.bk.girltrollsv.ui.activity.MainActivity;
import com.bk.girltrollsv.util.AccountUtil;
import com.bk.girltrollsv.util.SpaceItem;
import com.bk.girltrollsv.util.networkutil.LoadUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 18-Aug-16.
 */
public class PersonalFragment extends BaseFragment {

    @Bind(R.id.rv_catalog_personal)
    RecyclerView rvCatalogPersonal;

    @Bind(R.id.toolbar_tv_title_center)
    TextView tvTitle;

    @Bind(R.id.ll_login_catalog_personal)
    LinearLayout llLoginCatalogPersonal;

    @Bind(R.id.img_login_catalog_personal)
    CircleImageView cirImgLoginCatalogPersonal;

    @Bind((R.id.txt_login_catalog_personal))
    TextView txtLoginCatalogPersonal;

    Activity mActivity;

    PersonalCatalogAdapter mPersonalCatalogAdapter;

    boolean mIsLogin = false;


    public static PersonalFragment newInstance() {

        return new PersonalFragment();
    }

    @Override
    protected void initView() {
        mActivity = getActivity();
        tvTitle.setText(getResources().getString(R.string.personal_title));

//        ((MainActivity) mActivity).setOnClickTabListener(new OnClickTabListener() {
//            @Override
//            public void onClickTab(int position) {
//                if (position == PagerMainAdapter.PERSONAL_POS){
//                    setImageNameMemberLogin();
//                }
//            }
//        });

        ((MainActivity) mActivity).setmOnLoginCompletedListener(new OnLoginCompletedListener() {
            @Override
            public void onLoginCompleted(boolean isLogin) {
                if (isLogin) {

                    setImageNameMemberLogin();
                }
            }
        });

        initRv();

        setImageNameMemberLogin();
    }

    public void initRv() {

        int spaceBetweenItems = 15;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItem spaceItem = new SpaceItem(spaceBetweenItems, SpaceItem.VERTICAL);

        rvCatalogPersonal.setLayoutManager(layoutManager);
        rvCatalogPersonal.addItemDecoration(spaceItem);

        ArrayList<PersonalCatalogItem> personalCatalogItemList = PersonalCatalogItemList.getListPersonalCatalogItem();

        mPersonalCatalogAdapter = new PersonalCatalogAdapter(mActivity, personalCatalogItemList);
        rvCatalogPersonal.setAdapter(mPersonalCatalogAdapter);

        mPersonalCatalogAdapter.setOnItemClickListener(new OnPersonalCatalogItemClickListener() {
            @Override
            public void onClickItemPersonalCatalog(String namePersonalCatalog) {
                switch (namePersonalCatalog) {

                    case PersonalCatalogItemList.LOG_OUT:
                        logOutPersonal();
                        break;
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @OnClick(R.id.ll_login_catalog_personal)
    public void onClickLLLoginCatalogPersonal(View view) {

        if (!mIsLogin) {

            Bundle data = new Bundle();
            data.putInt(AppConstant.FLAG_LOGIN_FINISH, AppConstant.FINISH_WHEN_COMPLETE);
            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.putExtra(AppConstant.PACKAGE, data);
            //startActivity(intent);
            startActivityForResult(intent, AppConstant.REQUEST_CODE_PERSONAL_FRAGMENT);
        }
    }

    public void setImageNameMemberLogin() {

        if (AccountUtil.getAccountId() == null) {

            cirImgLoginCatalogPersonal.setImageResource(R.drawable.icon_login_catalog_personal);
            txtLoginCatalogPersonal.setText(getResources().getString(R.string.catalog_login_personal));
        } else {

            addLogOutItem();
            setImageAvatarMemberLogin(AccountUtil.getAvatarUrl());
            setNameMemberLogin();
        }
    }

    public void setImageAvatarMemberLogin(String urlAvatarMember) {
        int height = (int) mActivity.getResources().getDimension(R.dimen.height_image_login_catalog_personal);
        int width = (int) mActivity.getResources().getDimension(R.dimen.width_image_login_catalog_personal);
        LoadUtil.loadAvatar(urlAvatarMember, cirImgLoginCatalogPersonal, width, height);
    }

    public void setNameMemberLogin() {

        String name = AccountUtil.getUsername();
        if (name == null) {
            txtLoginCatalogPersonal.setText("");
        } else {
            txtLoginCatalogPersonal.setText(name);
        }

    }

    public void addLogOutItem() {

        mIsLogin = true;

        PersonalCatalog personalLogOut = new PersonalCatalog(PersonalCatalogItemList.LOG_OUT, PersonalCatalogItemList.ICON_LOG_OUT);
        ArrayList<PersonalCatalog> list = new ArrayList<PersonalCatalog>();
        list.add(personalLogOut);

        PersonalCatalogItem personalLogOutItem = new PersonalCatalogItem(list, 1);
        mPersonalCatalogAdapter.addLogOutPersonal(personalLogOutItem);
    }

    private void confirmLaunchingLogout() {

        String title = mActivity.getString(R.string.confirm_logout);
        String message = mActivity.getString(R.string.message_confirm_logout);
        ConfirmDialogFragment confirmDialog = ConfirmDialogFragment.newInstance(title, message);
        confirmDialog.setPositiveText(R.string.text_logout);

        confirmDialog.setListener(new ConfirmDialogListener() {
            @Override
            public void onPositivePress(DialogInterface dialog, int which) {

                mIsLogin = false;
                mPersonalCatalogAdapter.removeLogOutPersonal();
                cirImgLoginCatalogPersonal.setImageResource(R.drawable.icon_login_catalog_personal);
                txtLoginCatalogPersonal.setText(getResources().getString(R.string.catalog_login_personal));
            }

            @Override
            public void onNegativePress(DialogInterface dialog, int which) {

            }
        });

        confirmDialog.show(mActivity.getFragmentManager(), ConfirmDialogFragment.class.getSimpleName());
    }

    public void logOutPersonal() {

        confirmLaunchingLogout();
    }

}
