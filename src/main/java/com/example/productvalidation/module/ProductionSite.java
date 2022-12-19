package com.example.productvalidation.module;

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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof ProductionSite)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ProductionSite site = (ProductionSite) o;

        // Compare the data members and return accordingly
        return site.getCity().equals(this.city)
                && site.getPostcode().equals(this.postcode)
                && site.getCountry().equals(this.country);
    }
}