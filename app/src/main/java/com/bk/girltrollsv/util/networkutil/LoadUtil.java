package com.bk.girltrollsv.util.networkutil;

import android.widget.ImageView;

import com.bk.girltrollsv.BaseApplication;
import com.bk.girltrollsv.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 17-Aug-16.
 */
public class LoadUtil {

    public static void loadImage(String url, ImageView img) {

        if(url != null && url.length() > 0) {

            Picasso.with(BaseApplication.getContext())
                    .load(url)
                    .into(img);

        }
    }

    public static void loadImageResize(String url, ImageView  img, int width, int height) {

        if(url != null && url.length() > 0) {

            Picasso.with(BaseApplication.getContext())
                    .load(url)
                    .resize(width, height)
                    .onlyScaleDown()
                    .centerCrop()
                    .placeholder(R.drawable.place_holder1)
                    .into(img);

        }
    }
}
