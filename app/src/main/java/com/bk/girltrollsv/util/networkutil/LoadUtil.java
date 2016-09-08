package com.bk.girltrollsv.util.networkutil;

import android.widget.ImageView;

import com.bk.girltrollsv.BaseApplication;
import com.bk.girltrollsv.R;
import com.bk.girltrollsv.util.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by Dell on 17-Aug-16.
 */
public class LoadUtil {

    final static int[] PLACE_HOLDER_XML = new int[]{
            R.drawable.place_holder_1,
            R.drawable.place_holder_2,
            R.drawable.place_holder_3,
            R.drawable.place_holder_4
    };

    final static int[] PLACE_HOLDER_IMAGE = new int[]{
            R.drawable.place_holder_feed_5,
            R.drawable.place_holder_feed_6,
            R.drawable.place_holder_feed_7,
            R.drawable.place_holder_feed_8
    };

    public static void loadImage(String url, ImageView img) {

        if (url != null && url.length() > 0) {

            Picasso.with(BaseApplication.getContext())
                    .load(url)
                    .into(img);

        }
    }

    public static void loadImageResize(String url, ImageView imgDes, int width, int height) {

        int rand = new Random().nextInt(PLACE_HOLDER_XML.length);
        loadImageResizeWithPlaceHolder(url, imgDes, PLACE_HOLDER_XML[rand], width, height);
    }

    public static void loadAvatar(String url, ImageView imgDes, int width, int height) {

        int rand = new Random().nextInt(PLACE_HOLDER_IMAGE.length);
        loadImageResizeWithPlaceHolder(url, imgDes, PLACE_HOLDER_IMAGE[rand], width, height);
    }

    private static void loadImageResizeWithPlaceHolder(String url, ImageView imgDes, int resIdPlaceHolder,
                                                       int width, int height) {
        if (StringUtil.isEmpty(url)) {
            url = null;
        }
        if (imgDes != null ) {
            Picasso.with(BaseApplication.getContext())
                    .load(url)
                    .resize(width, height)
                    .onlyScaleDown()
                    .centerCrop()
                    .placeholder(resIdPlaceHolder)
                    .into(imgDes);

        }
    }
}
