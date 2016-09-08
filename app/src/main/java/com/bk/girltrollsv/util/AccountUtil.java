package com.bk.girltrollsv.util;

import com.bk.girltrollsv.constant.AppConstant;

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




}
