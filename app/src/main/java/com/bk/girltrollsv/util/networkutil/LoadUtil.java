package com.bk.girltrollsv.util.networkutil;

import android.util.Log;
import android.widget.ImageView;

import com.bk.girltrollsv.BaseApplication;
import com.bk.girltrollsv.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by Dell on 17-Aug-16.
 */
public class LoadUtil {

    final static int [] PLACE_HOLDER = new int [] {
            R.drawable.place_holder_feed_1,
            R.drawable.place_holder_feed_2,
            R.drawable.place_holder_feed_3,
            R.drawable.place_holder_feed_4
    };

    public static void loadImage(String url, ImageView img) {

        if(url != null && url.length() > 0) {

            Picasso.with(BaseApplication.getContext())
                    .load(url)
                    .into(img);

        }
    }

    public static void loadImageResize(String url, ImageView  img, int width, int height) {

        Log.e("tuton", url);
        int randIndex = new Random().nextInt(PLACE_HOLDER.length);
        if(url != null && url.length() > 0) {

            Picasso.with(BaseApplication.getContext())
                    .load(url)
                    .resize(width, height)
                    .onlyScaleDown()
                    .centerCrop()
                    .placeholder(PLACE_HOLDER[randIndex])
                    .into(img);

        }
    }
}
