package com.bk.girltrollsv.model.dataserver;

import com.bk.girltrollsv.model.EventBase;

import java.util.ArrayList;

/**
 * Created by Dell on 22-Aug-16.
 */
public class EventCatalogResponse extends MyResponse{

    private ArrayList<EventBase> data;

    public ArrayList<EventBase> getData() {
        return data;
    }
}
