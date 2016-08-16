package com.bk.girltrollsv.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bk.girltrollsv.R;
import com.facebook.FacebookSdk;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash);
    }
}
