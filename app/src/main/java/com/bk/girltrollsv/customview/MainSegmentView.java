package com.bk.girltrollsv.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Dell on 18-Aug-16.
 */
public class MainSegmentView extends LinearLayout implements View.OnClickListener {

    public int[] mIcons;

    public int[] mPressIcons;

    public ImageView[] mChildImageViews;

    int numberTab;

    int mSelectedTab = 0;

    ViewGroup mSelectedView;


    ViewGroup[] tabs;

    OnSegmentViewSelectedListener mSegmentSelectedListener;


    public MainSegmentView(Context context) {
        super(context);
    }

    public MainSegmentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public MainSegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onFinishInflate() {

        numberTab = getChildCount();


        if (numberTab > 0) {

            // collect children declared in XML
            tabs = new ViewGroup[numberTab];
            mChildImageViews = new ImageView[numberTab];
            int index = numberTab;

            for (int i = index - 1; i >= 0; i--) {

                ViewGroup container = (ViewGroup) getChildAt(i);
                ImageView imgView = (ImageView) container.getChildAt(0);
                tabs[i] = container;
                mChildImageViews[i] = imgView;

                container.setTag(i);
                imgView.setTag(i);

                container.setOnClickListener(this);
            }

            if (mSelectedTab >= 0) {
                tabs[mSelectedTab].setSelected(true);
                mSelectedView = tabs[mSelectedTab];

                setImagePressIcon(mSelectedTab);
            }
        }
        super.onFinishInflate();
    }

    @Override
    public void onClick(View v) {

        int tag = Integer.parseInt(v.getTag().toString());
        int preTag = -1;
        if (mSelectedView != v) {

            if (mSelectedView != null) {
                mSelectedView.setSelected(false);
                preTag = Integer.parseInt(mSelectedView.getTag().toString());
                setImageIcon(preTag);
            }

            mSelectedView = (ViewGroup) v;
            mSelectedTab = tag;
            setImagePressIcon(tag);
            mSelectedView.setSelected(true);
        }

        if (mSegmentSelectedListener != null) {
            mSegmentSelectedListener.onSegmentViewSelected(preTag, tag);
        }
    }

    public void setSelectedTab(int position) {

        if (position < 0) {
            position = 0;

        } else if (position >= tabs.length) {
            position = tabs.length - 1;
        }

        int preTag = -1;
        if (mSelectedView != tabs[position]) {

            if (mSelectedView != null) {
                mSelectedView.setSelected(false);
                preTag = Integer.parseInt(mSelectedView.getTag().toString());
                setImageIcon(preTag);

            }

            tabs[position].setSelected(true);
            mSelectedView = tabs[position];
            mSelectedTab = position;
            setImagePressIcon(position);
        }

        if (mSegmentSelectedListener != null) {
            mSegmentSelectedListener.onSegmentViewSelected(preTag, position);
        }

    }


    public void setIcons(int[] initIcons) {
        this.mIcons = initIcons;
    }

    public void setPressIcons(int[] initPressIcons) {
        this.mPressIcons = initPressIcons;
    }

    public interface OnSegmentViewSelectedListener {
        void onSegmentViewSelected(int prePosition, int currPosition);
    }

    public void setOnSegmentViewSelectedListener(OnSegmentViewSelectedListener listener) {
        this.mSegmentSelectedListener = listener;
    }
    public int getSelectedTab() {
        return mSelectedTab;
    }

    private void displayImage(int imgPos, int resId) {

        if(mChildImageViews != null && mChildImageViews.length > imgPos) {
            mChildImageViews[imgPos].setImageResource(resId);
        }
    }

    private void setImageIcon(int pos) {

        if(mIcons != null && mIcons.length > pos) {
            displayImage(pos, mIcons[pos]);
        }
    }

    private void setImagePressIcon(int pos) {

        if(mPressIcons != null && mPressIcons.length > pos) {
            displayImage(pos, mPressIcons[pos]);
        }
    }
}
