package com.example.productvalidation.entity;

import lombok.Data;


/*
This class created for production site for products.
 */
@Data
public class ProductionSite {

    private String country;
    private String city;
    private String postcode;

    public ProductionSite(String country, String city, String postcode) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
    }
}
