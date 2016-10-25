package com.bk.girltrollsv.callback;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Dell on 15-Sep-16.
 */
public class HidingScrollListener2 extends RecyclerView.OnScrollListener {

    View mViewScroll;

    public HidingScrollListener2(View view) {
        this.mViewScroll = view;
    }

    // Keeps track of the overall vertical offset in the list
    int verticalOffset;

    // Determines the scroll UP/DOWN direction
    boolean scrollingUp;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (scrollingUp) {
                if (verticalOffset > mViewScroll.getHeight()) {
                    toolbarAnimateHide();
                } else {
                    toolbarAnimateShow(verticalOffset);
                }
            } else {
                if (mViewScroll.getTranslationY() < mViewScroll.getHeight() * -0.6 && verticalOffset > mViewScroll.getHeight()) {
                    toolbarAnimateHide();
                } else {
                    toolbarAnimateShow(verticalOffset);
                }
            }
        }
    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        verticalOffset += dy;
        scrollingUp = dy > 0;
        int toolbarYOffset = (int) (dy - mViewScroll.getTranslationY());
        mViewScroll.animate().cancel();
        if (scrollingUp) {
            if (toolbarYOffset < mViewScroll.getHeight()) {
                mViewScroll.setTranslationY(-toolbarYOffset);
            } else {
                mViewScroll.setTranslationY(-mViewScroll.getHeight());
            }
        } else {
            if (toolbarYOffset < 0) {
                mViewScroll.setTranslationY(0);
            } else {
                mViewScroll.setTranslationY(-toolbarYOffset);
            }
        }
    }


    private void toolbarAnimateShow(final int verticalOffset) {
        mViewScroll.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180);
    }

    private void toolbarAnimateHide() {
        mViewScroll.animate()
                .translationY(-mViewScroll.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180);
    }

}
