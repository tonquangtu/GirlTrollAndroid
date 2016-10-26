package com.bk.girltrollsv.util;

import android.os.AsyncTask;

import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.Member;

/**
 * Created by Dell on 08-Sep-16.
 */
public class AccountUtil {

    public static String getAccountId() {
        return SharedPrefUtils.getString(AppConstant.MY_ID, null);
    }

    public static String getUsername() {
        return SharedPrefUtils.getString(AppConstant.MY_USERNAME, null);
    }

    public static String getAvatarUrl() {
        return SharedPrefUtils.getString(AppConstant.MY_AVATAR_URL, null);
    }

    public static String getGmail() {
        return SharedPrefUtils.getString(AppConstant.MY_GMAIL, null);
    }

    public static int getRank() {
        return SharedPrefUtils.getInt(AppConstant.MY_RANK, 0);
    }

    public static int getLike() {
        return SharedPrefUtils.getInt(AppConstant.MY_LIKE, 0);
    }

    public static int getTotalImage() {
        return SharedPrefUtils.getInt(AppConstant.MY_TOTAL_IMAGE, 0);
    }

    public static int getActive() {
        return SharedPrefUtils.getInt(AppConstant.MY_ACTIVE, 0);
    }


    public synchronized static void saveInfoAccount(final Member member) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SharedPrefUtils.putString(AppConstant.MY_ID, member.getMemberId());
                SharedPrefUtils.putString(AppConstant.MY_USERNAME, member.getUsername());
                SharedPrefUtils.putString(AppConstant.MY_AVATAR_URL, member.getAvatarUrl());
                SharedPrefUtils.putString(AppConstant.MY_GMAIL, member.getGmail());
                SharedPrefUtils.putInt(AppConstant.MY_RANK, member.getRank());
                SharedPrefUtils.putInt(AppConstant.MY_LIKE, member.getLike());
                SharedPrefUtils.putInt(AppConstant.MY_TOTAL_IMAGE, member.getTotalImage());
                SharedPrefUtils.putInt(AppConstant.MY_ACTIVE, member.getActive());
                return null;
            }
        }.execute();
    }


}
