package com.bk.girltrollsv.ui.activity;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.bk.girltrollsv.R;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edit_username)
    EditText editUsername;

    @Bind(R.id.edit_password)
    EditText editPassword;

    @Bind(R.id.btn_login_normal)
    AppCompatButton acBtnLoginNormal;

    @Bind(R.id.btn_facebook_login)
    AppCompatButton acBtnFacebookLogin;

    @Bind(R.id.btn_sign_up)
    Button btnSignUp;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        setSupportActionBar(toolbar);

    }

    @Override
    public void initData() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case android.R.id.home :
                finish();
                break;
        }
        return true;
    }


}
