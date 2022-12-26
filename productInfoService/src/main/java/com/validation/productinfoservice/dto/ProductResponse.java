package com.validation.productinfoservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.validation.productValidation.module.ProductionSite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
public class ProductResponse {
    private String registrationId;
    private String productId;
    private String productName;
    private String brand;
    private Date productionDate;
    private Date expireDate;
    private Boolean isExpandible;
    private ProductionSite productionSite;
    private Date creationTime;
    private boolean isValid;

    @JsonCreator
    public ProductResponse(@JsonProperty("registrationId") String registrationId, @JsonProperty("productId") String productId,
                           @JsonProperty("productName") String productName, @JsonProperty("brand") String brand,
                           @JsonProperty("productionDate") Date productionDate, @JsonProperty("expireDate") Date expireDate,
                           @JsonProperty("isExpandible") Boolean isExpandible,@JsonProperty("productionSite") ProductionSite productionSite,
                           @JsonProperty("creationTime") Date creationTime,@JsonProperty("isValid") boolean isValid) {
        this.registrationId = registrationId;
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.productionDate = productionDate;
        this.expireDate = expireDate;
        this.isExpandible = isExpandible;
        this.productionSite = productionSite;
        this.creationTime = creationTime;
        this.isValid = isValid;
    }
}
