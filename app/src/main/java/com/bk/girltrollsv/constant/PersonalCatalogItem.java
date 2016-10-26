package com.bk.girltrollsv.constant;

import com.bk.girltrollsv.R;

import java.util.ArrayList;

/**
 * Created by trung on 20/10/2016.
 */
public class PersonalCatalogItem {

    private ArrayList<PersonalCatalog> list;
    private int typeItem;

    public ArrayList<PersonalCatalog> getList() {
        return list;
    }

    public void setList(ArrayList<PersonalCatalog> list) {
        this.list = list;
    }

    public int getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(int type) {
        this.typeItem = type;
    }

    public PersonalCatalogItem(ArrayList<PersonalCatalog> list, int typeItem) {
        this.list = list;
        this.typeItem = typeItem;
    }


}
