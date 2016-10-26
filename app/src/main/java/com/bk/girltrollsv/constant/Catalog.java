package com.bk.girltrollsv.constant;

/**
 * Created by trung on 14/10/2016.
 */
public class Catalog {

    private String catalogName;
    private int catalogImage;

    public Catalog(String catalogName, int catalogImage){

        this.catalogName = catalogName;
        this.catalogImage = catalogImage;

    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public int getCatalogImage() {
        return catalogImage;
    }

    public void setCatalogImage(int catalogImage) {
        this.catalogImage = catalogImage;
    }

    @Override
    public String toString()  {
        return this.catalogName;
    }
}
