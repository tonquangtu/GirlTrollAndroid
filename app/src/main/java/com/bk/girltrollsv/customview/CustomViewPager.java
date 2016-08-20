package com.bk.girltrollsv.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Dell on 16-Aug-16.
 */
public class CustomViewPager extends ViewPager {

    private boolean isSwipePage = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isSwipePage) {
            return super.onInterceptTouchEvent(ev);
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isSwipePage) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public void setSwipePage(boolean isSwipePage) {
        this.isSwipePage = isSwipePage;
    }
}
