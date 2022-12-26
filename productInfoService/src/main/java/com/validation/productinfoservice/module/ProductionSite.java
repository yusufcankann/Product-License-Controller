package com.validation.productinfoservice.module;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;


/*
This class created for production site for products.
 */
@Data
public class ProductionSite {

    private String country;
    private String city;
    private String postcode;

    @JsonCreator
    public ProductionSite(@JsonProperty("country") String country, @JsonProperty("city") String city, @JsonProperty("postcode") String postcode) {
        //this(country, city, postcode);
        this.country = country;
        this.city = city;
        this.postcode = postcode;
    }

//    public ProductionSite(String country, String city, String postcode) {
//        this.country = country;
//        this.city = city;
//        this.postcode = postcode;
//    }

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