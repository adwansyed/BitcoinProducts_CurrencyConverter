package com.mobiledevices.bitcoinproducts;

/**
 * Created by Onique on 2017-11-11.
 */

public class Product {

    // member variables
    private int PRODUCT_ID;
    private String name;
    private String description;
    private float PRICE;

    // constructor
    public Product(int PRODUCT_ID, String name, String description, float PRICE) {
        this.PRODUCT_ID = PRODUCT_ID;
        this.name = name;
        this.description = description;
        this.PRICE = PRICE;
    }

    // accessors
    public int getPRODUCT_ID() { return PRODUCT_ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getPRICE() { return PRICE; }

    // mutators
    public void setPRODUCT_ID(int PRODUCT_ID) { this.PRODUCT_ID = PRODUCT_ID; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPRICE(float PRICE) { this.PRICE = PRICE; }

}
