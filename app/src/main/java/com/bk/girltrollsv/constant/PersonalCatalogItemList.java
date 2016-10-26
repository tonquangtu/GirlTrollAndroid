package com.bk.girltrollsv.constant;

import com.bk.girltrollsv.R;

import java.util.ArrayList;

/**
 * Created by trung on 25/10/2016.
 */
public class PersonalCatalogItemList {

    public static final String COMMENT = "Xem các bài đã comment";

    public static final int ICON_COMMENT_PERSONAL = R.drawable.icon_comment_personal;

    public static final String CONTROL = "Thiết lập tài khoản";

    public static final int ICON_CONTROL_PERSONAL = R.drawable.icon_control_personal;

    public static final String PHONE = "Thông tin cá nhân";

    public static final int ICON_PHONE_PERSONAL = R.drawable.icon_phone_personal;

    public static final String Like = "Xem các bài đã like";

    public static final int ICON_LIKE_PERSONAL = R.drawable.icon_like_personal;

    public static final String FEED = "Xem các bài đã đăng";

    public static final int ICON_FEED_PERSONAL = R.drawable.icon_feed_personal;

    public static final String PHAN_THUONG = "Đổi quà";

    public static final int ICON_PHAN_THUONG_PERSONAL = R.drawable.icon_phan_thuong_personal;

    public static final String GOP_Y = "Góp ý";

    public static final int ICON_GOP_Y_PERSONAL = R.drawable.icon_gop_y_personal;

    public static final String RANKING = "Ranking";

    public static final int ICON_RANKING_PERSONAL = R.drawable.icon_ranking_personal;

    public static final String SETTING = "Cài đặt ứng dụng";

    public static final int ICON_SETTING_PERSONAL = R.drawable.icon_setting_personal;

    public static final String LOG_OUT = "Đăng xuất";

    public static final int ICON_LOG_OUT = R.drawable.icon_logout_personal;


    public static ArrayList<PersonalCatalogItem> getListPersonalCatalogItem(){

        //PersonalCatalog comment = new PersonalCatalog(COMMENT, ICON_COMMENT_PERSONAL);
        PersonalCatalog control = new PersonalCatalog(CONTROL, ICON_CONTROL_PERSONAL);
        PersonalCatalog phone = new PersonalCatalog(PHONE, ICON_PHONE_PERSONAL);
        PersonalCatalog like = new PersonalCatalog(Like, ICON_LIKE_PERSONAL);
        PersonalCatalog feed = new PersonalCatalog(FEED, ICON_FEED_PERSONAL);
        PersonalCatalog phanThuong = new PersonalCatalog(PHAN_THUONG, ICON_PHAN_THUONG_PERSONAL);
        PersonalCatalog gopY = new PersonalCatalog(GOP_Y, ICON_GOP_Y_PERSONAL);
        //PersonalCatalog ranking = new PersonalCatalog(RANKING, ICON_RANKING_PERSONAL);
        PersonalCatalog setting = new PersonalCatalog(SETTING, ICON_SETTING_PERSONAL);
        //PersonalCatalog logOut = new PersonalCatalog(LOG_OUT, ICON_LOG_OUT);

        ArrayList<PersonalCatalogItem> listItem = new ArrayList<PersonalCatalogItem>();

        ArrayList<PersonalCatalog> list = new ArrayList<PersonalCatalog>();
        list.add(feed);
        list.add(like);
        //list.add(comment);
        listItem.add(new PersonalCatalogItem(list, 2));

        list = new ArrayList<PersonalCatalog>();
        list.add(phanThuong);
        list.add(phone);
        listItem.add(new PersonalCatalogItem(list, 2));

        list = new ArrayList<PersonalCatalog>();
        list.add(gopY);
        list.add(control);
        list.add(setting);
        listItem.add(new PersonalCatalogItem(list, 3));

        //list.add(logOut);

        return listItem;
    }
}
