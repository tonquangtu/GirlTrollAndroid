package com.bk.girltrollsv.constant;

import java.util.ArrayList;

/**
 * Created by trung on 14/10/2016.
 */
public class CatalogList {

    public static String TOP_NGUOI_DANG = "Top người đăng";

    public static String ICON_TOP_NGUOI_DANG = "icon_catalog_top_nguoi_dang";

    public static String GIRL_SHOOL = "Gái các trường";

    public static String ICON_GIRL_SHOOL = "icon_catalog_girl_school";

    public static String HOT = "Top bài đăng";

    public static String ICON_HOT = "icon_catalog_hot";

    public static String THONG_BAO = "Thông báo";

    public static String ICON_THONG_BAO = "icon_catalog_notification";

    public static String CHE_ANH = "Chế ảnh";

    public static String ICON_CHE_ANH = "icon_catalog_troll";

    public static String HOT_VIDEO = "Hot video";

    public static String ICON_HOT_VIDEO = "icon_catalog_hot_video";

    public static ArrayList<Catalog> getListCatalog(){

        ArrayList<Catalog> list = new ArrayList<Catalog>();
        Catalog hot = new Catalog(HOT, ICON_HOT);
        Catalog hotVideo = new Catalog(HOT_VIDEO, ICON_HOT_VIDEO);
        Catalog troll = new Catalog(CHE_ANH, ICON_CHE_ANH);
        Catalog girlSchool = new Catalog(GIRL_SHOOL, ICON_GIRL_SHOOL);
        Catalog top = new Catalog(TOP_NGUOI_DANG, ICON_TOP_NGUOI_DANG);
        Catalog notification = new Catalog(THONG_BAO, ICON_THONG_BAO);

        list.add(hot);
        list.add(hotVideo);
        list.add(troll);
        list.add(girlSchool);
        list.add(top);
        list.add(notification);

        return list;
    }


}
