package com.bk.girltrollsv.callback;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Dell on 25-Aug-16.
 */
public interface FeedItemOnClickListener {

    void onClickImage(int posFeed, int posImage, ImageView[] views);

    void onClickVideo(int posFeed, View view);

    void onClickLike(int posFeed, View viewLike, TextView txtNumLike);

    void onClickComment(int posFeed, View view);

    void onClickMore(int posFeed, View view);

//    void onClickShare(int posFeed, View view);

}
