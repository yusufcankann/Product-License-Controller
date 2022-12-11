package com.example.productvalidation.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Product {
    @Id
    private String productId;
    @NonNull
    private String productName;
    @NonNull
    private String brand;
    @NonNull
    private Date productionDate;
    @NonNull
    private Date expireDate;
    @NonNull
    private Boolean isExpandible;
    @NonNull
    private ProductionSite productionSite;
    @NonNull
    private Date creationTime;
}
