package de.ostfalia.gruppe5.rest;

import org.junit.Before;

public class ProductLineIT extends BasicIT<ProductLineProxy, String, String> {
    @Before
    public void init() {
        this.setProxyType(ProductLineProxy.class);
        this.setTestId("Vintage Cars");
        this.setPrimaryKey("productLine");
        this.setTestEntity("{\"productLine\":" + this.getPrimaryKeyToken() + "," +
                "\"textDescription\":" + this.getUpdateToken() + "," +
                "\"htmlDescription\":null," +
                "\"image\":null}");
        this.setUpdateKeyword("textDescription");
        this.setIdType(String.class);
        this.setUpdateType(String.class);
        this.setUpdateBase("100");
        this.setUpdateGoal("0");
    }
}
