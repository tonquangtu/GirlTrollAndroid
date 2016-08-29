package com.bk.girltrollsv.callback;

import android.view.View;

/**
 * Created by Dell on 25-Aug-16.
 */
public interface FeedItemOnClickListener {

    void onClickImage(int posFeed, int posImage, View view);

    void onClickVideo(int posFeed, View view);

    void onClickLike(int posFeed, View view);

    void onClickComment(int posFeed, View view);

    void onClickShare(int posFeed, View view);

    void onClickMore(int posFeed, View view);
}