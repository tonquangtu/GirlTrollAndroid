package com.bk.girltrollsv.model.dataserver;

import com.bk.girltrollsv.model.Feed;

import java.util.ArrayList;

/**
 * Created by Dell on 21-Aug-16.
 */
public class FeedResponse extends MyResponse{

    private ArrayList<Feed> data;

    private Paging paging;

    public Paging getPaging() {
        return paging;
    }

    public ArrayList<Feed> getData() {
        return data;
    }
}
