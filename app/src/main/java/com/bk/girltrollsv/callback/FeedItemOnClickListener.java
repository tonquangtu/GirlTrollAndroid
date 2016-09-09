package com.bk.girltrollsv.callback;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Dell on 25-Aug-16.
 */
public interface FeedItemOnClickListener {

    void onClickImage(int posFeed, int posImage, View view);

    void onClickVideo(int posFeed, View view);

    void onClickLike(int posFeed, ImageButton imgBtnLike, TextView txtNumLike);

    void onClickComment(int posFeed, View view);

    void onClickMore(int posFeed, View view);

//    void onClickShare(int posFeed, View view);

}
