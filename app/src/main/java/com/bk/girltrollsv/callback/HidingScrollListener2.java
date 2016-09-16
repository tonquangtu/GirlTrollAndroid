package com.bk.girltrollsv.callback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.LinearInterpolator;

/**
 * Created by Dell on 15-Sep-16.
 */
public class HidingScrollListener2 extends RecyclerView.OnScrollListener {

    Toolbar tToolbar;
    int TOOLBAR_ELEVATION = 2;

    public HidingScrollListener2(Toolbar toolbar) {
        this.tToolbar = toolbar;
    }

    // Keeps track of the overall vertical offset in the list
    int verticalOffset;

    // Determines the scroll UP/DOWN direction
    boolean scrollingUp;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (scrollingUp) {
                if (verticalOffset > tToolbar.getHeight()) {
                    toolbarAnimateHide();
                } else {
                    toolbarAnimateShow(verticalOffset);
                }
            } else {
                if (tToolbar.getTranslationY() < tToolbar.getHeight() * -0.6 && verticalOffset > tToolbar.getHeight()) {
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
        int toolbarYOffset = (int) (dy - tToolbar.getTranslationY());
        tToolbar.animate().cancel();
        if (scrollingUp) {
            if (toolbarYOffset < tToolbar.getHeight()) {
                if (verticalOffset > tToolbar.getHeight()) {
                    toolbarSetElevation(TOOLBAR_ELEVATION);
                }
                tToolbar.setTranslationY(-toolbarYOffset);
            } else {
                toolbarSetElevation(0);
                tToolbar.setTranslationY(-tToolbar.getHeight());
            }
        } else {
            if (toolbarYOffset < 0) {
                if (verticalOffset <= 0) {
                    toolbarSetElevation(0);
                }
                tToolbar.setTranslationY(0);
            } else {
                if (verticalOffset > tToolbar.getHeight()) {
                    toolbarSetElevation(TOOLBAR_ELEVATION);
                }
                tToolbar.setTranslationY(-toolbarYOffset);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toolbarSetElevation(float elevation) {
        // setElevation() only works on Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tToolbar.setElevation(elevation);
        }
    }

    private void toolbarAnimateShow(final int verticalOffset) {
        tToolbar.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbarSetElevation(verticalOffset == 0 ? 0 : TOOLBAR_ELEVATION);
                    }
                });
    }

    private void toolbarAnimateHide() {
        tToolbar.animate()
                .translationY(-tToolbar.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
    }


}
