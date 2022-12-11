package com.example.productvalidation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Product {

    @Id
    private String productId;
    private String productName;
    private String brand;
    private Date productionDate;
    private Date expireDate;
    private Boolean isExpandible;
    private ProductionSite productionSite;
    private Date creationTime;

    public Product(String productName, String brand, Date productionDate, Date expireDate, Boolean isExpandible,
                   ProductionSite productionSite, Date creationTime) {
        this.productName = productName;
        this.brand = brand;
        this.productionDate = productionDate;
        this.expireDate = expireDate;
        this.isExpandible = isExpandible;
        this.productionSite = productionSite;
        this.creationTime = creationTime;
    }
}
