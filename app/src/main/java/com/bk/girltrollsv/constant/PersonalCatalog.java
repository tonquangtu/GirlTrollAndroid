package com.bk.girltrollsv.constant;

/**
 * Created by trung on 20/10/2016.
 */
public class PersonalCatalog {

    private String personalCatalogName;
    private int personalCatalogImage;

    public PersonalCatalog(String catalogName, int catalogImage){

        this.personalCatalogName = catalogName;
        this.personalCatalogImage = catalogImage;

    }

    public String getCatalogName() {
        return personalCatalogName;
    }

    public void setCatalogName(String catalogName) {
        this.personalCatalogName = catalogName;
    }

    public int getCatalogImage() {
        return personalCatalogImage;
    }

    public void setCatalogImage(int catalogImage) {
        this.personalCatalogImage = catalogImage;
    }

}
