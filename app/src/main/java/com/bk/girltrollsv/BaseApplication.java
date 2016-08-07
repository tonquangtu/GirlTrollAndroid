package com.bk.girltrollsv;

import android.app.Application;

import com.bk.girltrollsv.util.SharedPrefUtils;


/**
 * Created by Hado on 30-Jul-16.
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static SharedPrefUtils sharedPrefUtils;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefUtils = new SharedPrefUtils(getApplicationContext());
    }

    public static SharedPrefUtils getSharedPreferences() {
        return sharedPrefUtils;
    }
}
