package com.bk.girltrollsv.constant;

/**
 * Created by trung on 14/10/2016.
 */
public class Catalog {

    private String catalogName;
    private String catalogImage;

    public Catalog(String catalogName, String catalogImage){

        this.catalogName = catalogName;
        this.catalogImage = catalogImage;

    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogImage() {
        return catalogImage;
    }

    public void setCatalogImage(String catalogImage) {
        this.catalogImage = catalogImage;
    }

    @Override
    public String toString()  {
        return this.catalogName;
    }
}
