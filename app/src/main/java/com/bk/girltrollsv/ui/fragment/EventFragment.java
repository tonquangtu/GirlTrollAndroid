package com.bk.girltrollsv.ui.fragment;


import com.bk.girltrollsv.R;

/**
 * Created by Dell on 18-Aug-16.
 */
public class EventFragment extends BaseFragment{


    public static EventFragment newInstance() {
        return new EventFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_event;
    }
}
