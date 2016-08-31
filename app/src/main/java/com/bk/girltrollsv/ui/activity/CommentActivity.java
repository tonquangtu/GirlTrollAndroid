package com.bk.girltrollsv.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bk.girltrollsv.R;
import com.bk.girltrollsv.constant.AppConstant;

import butterknife.Bind;

public class CommentActivity extends BaseActivity {

    @Bind(R.id.pb_load_comment)
    ProgressBar pbLoadComment;

    @Bind(R.id.ll_web_view)
    LinearLayout parentLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String APP_KEY ;

    private String BASE_DOMAIN ;

    private String PATH_URL;

    //private String title;

    private WebView webView, childView = null;


    @Override
    public int setContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    public void handleIntent(Intent intent) {

        Bundle bundle = intent.getBundleExtra(AppConstant.PACKAGE);
        PATH_URL = bundle.getString(AppConstant.FEED_ID_TAG);

        //title = bundle.getString(AppConstant.FEED_TITLE_TAG);
    }

    @Override
    public void initView() {

        initToolbar();

        initString();

        initProgressBar();

        initWebView();

        initParentLayout();

        initData();
    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.base_comment);

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void initData() {

        String html =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>Demo</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "<div id=\"fb-root\"></div>\n" +
                "<script>(function(d, s, id) {\n" +
                "  var js, fjs = d.getElementsByTagName(s)[0];\n" +
                "  if (d.getElementById(id)) return;\n" +
                "  js = d.createElement(s); js.id = id;\n" +
                "  js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.7&appId=" + APP_KEY + "\";\n" +
                "  fjs.parentNode.insertBefore(js, fjs);\n" +
                "}(document, 'script', 'facebook-jssdk'));</script>\n" +
                "\n" +
                "<div class=\"fb-comments\" data-href=\" " + BASE_DOMAIN + PATH_URL + "\" data-width=\"470\" data-num-posts=\"10\"></div>\n" +
                "\n" +
                "  </body>\n" +
                "</html>";
        webView.loadDataWithBaseURL(BASE_DOMAIN, html, "text/html", null, null);
    }

    public void initString(){
        APP_KEY = getResources().getString(R.string.facebook_app_id);
        BASE_DOMAIN = getResources().getString(R.string.bare_url);
    }

    public void initProgressBar(){

        pbLoadComment.setVisibility(View.VISIBLE);
    }

    public void initParentLayout(){

        parentLayout.addView(webView);
        parentLayout.setVisibility(View.GONE);
    }

    public void initWebView(){

        webView = new WebView(this);

        webView.setLayoutParams(getLayoutParams());

        webView.setWebViewClient(new FaceBookClient());
        webView.setWebChromeClient(new LoginFaceBookChromeClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
        clearCookies(this);

        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

    }

    private RelativeLayout.LayoutParams getLayoutParams() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    final class LoginFaceBookChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            childView = new WebView(getApplicationContext());
            childView.getSettings().setDomStorageEnabled(true);
            childView.getSettings().setJavaScriptEnabled(true);
            childView.getSettings().setSupportZoom(true);
            childView.getSettings().setBuiltInZoomControls(true);
            childView.setWebViewClient(new FaceBookClient());
            childView.setWebChromeClient(this);
            childView.setLayoutParams(getLayoutParams());

            childView.getSettings().setSupportMultipleWindows(true);
            parentLayout.addView(childView);
            childView.requestFocus();
            webView.setVisibility(View.GONE);

            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(childView);
            resultMsg.sendToTarget();
            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
            parentLayout.removeViewAt(parentLayout.getChildCount() -1);
            childView = null;
            webView.setVisibility(View.VISIBLE);
            webView.requestFocus();
        }


    }

    private boolean loadingFinished = true;
    private boolean redirect = false;

    private class FaceBookClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!loadingFinished) {
                redirect = true;
            }

            loadingFinished = false;

            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingFinished = false;
            pbLoadComment.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //pgbLoadComment.setVisibility(View.GONE);
            if(!redirect){
                loadingFinished = true;
            }

            if(loadingFinished && !redirect){
                //HIDE LOADING IT HAS FINISHED

            } else{
                redirect = false;
            }

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

//            Log.d("loadd", ((Integer)webView.getProgress()).toString() + " " + url.toString());
//            Log.d("string1", string1.toString());
//
            if (webView.getProgress() == 100){

                parentLayout.setVisibility(View.VISIBLE);
                pbLoadComment.setVisibility(View.GONE);

            }

        }
    }

    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d("TAG", "Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            Log.d("TAG", "Using clearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){

            finish();
        }

        return false;
    }
}
