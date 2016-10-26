package com.bk.girltrollsv.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Member;
import com.bk.girltrollsv.model.dataserver.DataMember;
import com.bk.girltrollsv.model.dataserver.LoginResponse;
import com.bk.girltrollsv.networkconfig.ConfigNetwork;
import com.bk.girltrollsv.util.AccountUtil;
import com.bk.girltrollsv.util.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;

    @Bind(R.id.edit_username)
    AppCompatEditText editUsername;

    @Bind(R.id.edit_password)
    AppCompatEditText editPassword;

    @Bind(R.id.btn_login_normal)
    AppCompatButton acBtnLoginNormal;

    CallbackManager mCallbackManager;
    ProgressDialog mProgressDialog;

    int mFlag = 0;

    Intent mIntent;

    boolean mIsSuccessLogin = false;


    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

//        initToolbar();

        initFacebookLogin();

        initProgressDialog();
    }

    @Override
    public void initData() {

    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        mIntent = intent;

        Bundle bundle = mIntent.getBundleExtra(AppConstant.PACKAGE);
        if (bundle != null) {
            mFlag = bundle.getInt(AppConstant.FLAG_LOGIN_FINISH);
        }


    }

//    public void initToolbar() {
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(R.string.text_login_normal);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//    }

    public void initFacebookLogin() {

        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookLoginSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                onFinishLogin(false);
            }

            @Override
            public void onError(FacebookException error) {
                onFinishLogin(false);
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        switch (id) {
//
//            case android.R.id.home :
//                finish();
//                break;
//        }
//        return true;
//    }

    @OnClick(R.id.btn_login_normal)
    public void onClickNormalLogin(View view){
        handleNormalLogin();
    }


    @OnClick(R.id.btn_facebook_login)
    public void onClickFacebookLogin(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_friends"));
    }

    @OnClick(R.id.btn_sign_up)
    public void onClickSignUp(View view) {

        if (Profile.getCurrentProfile() != null) {
            LoginManager.getInstance().logOut();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        mIsSuccessLogin = false;
        mIntent.putExtra(AppConstant.IS_LOGIN_TAG, mIsSuccessLogin);
        setResult(AppConstant.RESULT_CODE_LOGIN, mIntent);
        super.onBackPressed();
    }

    public void handleFacebookLoginSuccess(LoginResult loginResult) {

        mProgressDialog.show();
        if (Profile.getCurrentProfile() != null) {
            confirmFacebookLoginRemote(Profile.getCurrentProfile());
        } else {
            new ProfileTracker() {

                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    this.stopTracking();
                    if (currentProfile != null) {
                        confirmFacebookLoginRemote(currentProfile);
                    } else {
                        onFinishLogin(false);
                    }
                }
            };
        }
    }

    public void initProgressDialog() {

        mProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(0);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(getResources().getString(R.string.title_logining));
    }

    public void confirmFacebookLoginRemote(Profile profile) {

        String facebookId = profile.getId() + "";
        final String username = profile.getName() + "";
        final String avatarUrl = profile.getProfilePictureUri(60, 60).toString();
        final String gmail = "";

        final Map<String, String> dataLogin = new HashMap<>();
        dataLogin.put(AppConstant.FACEBOOK_ID_TAG, facebookId);
        dataLogin.put(AppConstant.USERNAME_TAG, username);
        dataLogin.put(AppConstant.GMAIL_TAG, gmail);
        dataLogin.put(AppConstant.AVATAR_URL_TAG, avatarUrl);

        Call<LoginResponse> call = ConfigNetwork.serviceAPI.callFacebookLogin(dataLogin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response != null && response.isSuccessful()
                        && response.body() != null) {

                    DataMember result = response.body().getData();
                    Member member = new Member(result.getMemberId(), username, avatarUrl, gmail,
                            result.getRank(), result.getLike(), result.getTotalImage(), result.getActive());
                    AccountUtil.saveInfoAccount(member);
                    onFinishLogin(true);
                } else {
                    onFinishLogin(false);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onFinishLogin(false);
            }
        });
    }

    public void handleNormalLogin() {

        String email = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.equals("")){

            Utils.toastShort(LoginActivity.this, R.string.email_not_enter);
            editUsername.requestFocus();
        }
        else if (!checkEmail(email)){

            Utils.toastShort(LoginActivity.this, R.string.email_enter_false);
            editUsername.requestFocus();
        }
        else if(password.equals("")){

            Utils.toastShort(LoginActivity.this, R.string.password_not_enter);
            editPassword.requestFocus();
        }
        else {

            mProgressDialog.show();
            confirmNormalLoginRemote(email, password);
        }
    }

    private boolean checkEmail(String email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void confirmNormalLoginRemote(final String email, String password) {

        final Map<String, String> dataLogin = new HashMap<>();
        dataLogin.put(AppConstant.GMAIL_TAG, email);
        dataLogin.put(AppConstant.PASSWORD_TAG, password);

        Call<LoginResponse> call = ConfigNetwork.getServerAPI().callNormalLogin(dataLogin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response != null && response.isSuccessful() && response.body() != null) {
                    LoginResponse body = response.body();

                    if (body.getSuccess() == AppConstant.SUCCESS) {
                        DataMember result = body.getData();
                        Member member = new Member(result.getMemberId(), result.getUsername(), result.getAvatarUrl(), email,
                                result.getRank(), result.getLike(), result.getTotalImage(), result.getActive());
                        AccountUtil.saveInfoAccount(member);
                        onFinishLogin(true);

                    } else {
                        String message = body.getMessage();
                        Utils.toastShort(LoginActivity.this, message);
                        onFinishLogin(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onFinishLogin(false);
            }
        });
    }

    public void onFinishLogin(boolean loginSuccess) {

        mProgressDialog.dismiss();
        if (!loginSuccess) {
            Utils.toastShort(LoginActivity.this, R.string.login_fail);
        } else {
            mIsSuccessLogin = true;
            mIntent.putExtra(AppConstant.IS_LOGIN_TAG, mIsSuccessLogin);
            setResult(AppConstant.RESULT_CODE_LOGIN, mIntent);
            Utils.toastShort(LoginActivity.this, R.string.login_success);
            if (mFlag == AppConstant.FINISH_WHEN_COMPLETE) {
                this.finish();
            }
        }
    }


}
