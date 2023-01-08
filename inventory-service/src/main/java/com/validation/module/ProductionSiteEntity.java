package com.validation.module;

import com.validation.inventoryservice.ProductionSite;
import lombok.Data;

/*
This class created for production site for products.
 */
@Data
public class ProductionSiteEntity {

    private String country;
    private String city;
    private String postcode;

    public ProductionSiteEntity(String country, String city, String postcode) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof ProductionSiteEntity)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ProductionSiteEntity site = (ProductionSiteEntity) o;

        // Compare the data members and return accordingly
        return site.getCity().equals(this.city)
                && site.getPostcode().equals(this.postcode)
                && site.getCountry().equals(this.country);
    }

    public ProductionSite toProto(){
        return ProductionSite.newBuilder().setCity(city).setCountry(country).setPostcode(postcode).build();
    }

    public static ProductionSiteEntity fromProto(ProductionSite productionSite){
        return new ProductionSiteEntity(productionSite.getCountry(),productionSite.getCity(),productionSite.getPostcode());
    }
}