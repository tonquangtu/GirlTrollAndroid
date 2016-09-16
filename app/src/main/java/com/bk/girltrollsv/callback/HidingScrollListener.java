package com.bk.girltrollsv.callback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Dell on 15-Sep-16.
 */
public class HidingScrollListener extends RecyclerView.OnScrollListener {

    public static final int HIDE_THRESHOLD = 10;
    public static final int SHOW_THRESHOLD = 70;

    private int mToolbarOffset = 0;
    private int mToolContainerHeight;
    private boolean mControlsVisible = true;
    private int mTotalScrolledDistance;
    Toolbar toolbar;
    Context context;

    public HidingScrollListener(Context context, Toolbar toolbar) {

        this.context = context;
        this.mToolContainerHeight = toolbar.getLayoutParams().height;
        this.toolbar = toolbar;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        clipToolbarOffset();
        onMoved(mToolbarOffset);

        if ((mToolbarOffset < mToolContainerHeight && dy > 0) || (mToolbarOffset > 0 && dy < 0)) {
            mToolbarOffset += dy;
        }
        mTotalScrolledDistance += dy;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (mTotalScrolledDistance < mToolContainerHeight) {
                setVisible();
            } else {
                if (mControlsVisible) {
                    if (mToolbarOffset > HIDE_THRESHOLD) {
                        setInvisible();
                    } else {
                        setVisible();
                    }
                } else {
                    if ((mToolContainerHeight - mToolbarOffset) > SHOW_THRESHOLD) {
                        setVisible();
                    } else {
                        setInvisible();
                    }
                }
            }
        }
    }

    private void clipToolbarOffset() {
        if (mToolbarOffset > mToolContainerHeight) {
            mToolbarOffset = mToolContainerHeight;
        } else if (mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }


    public void setVisible() {

        if (mToolbarOffset > 0) {
            onShow();
            mToolbarOffset = 0;
        }
        mControlsVisible = true;
    }

    private void setInvisible() {
        if (mToolbarOffset < mToolContainerHeight) {
            onHide();
            mToolbarOffset = mToolContainerHeight;
        }
        mControlsVisible = false;
    }

    public void onMoved(int distance) {

        toolbar.setTranslationY(-distance);
    }

    public void onShow() {

        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void onHide() {
        toolbar.animate().translationY(-mToolContainerHeight).setInterpolator(new AccelerateInterpolator(2)).start();

    }

}
