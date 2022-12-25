package com.validation.productValidation.dto;

import com.validation.productValidation.module.ProductionSite;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String registrationId;
    private String productName;
    private String brand;
    private Date productionDate;
    private Date expireDate;
    private Boolean isExpandible;
    private ProductionSite productionSite;
    private Date creationTime;
}
