package com.validation.productValidation.dto;

import com.validation.productValidation.module.ProductionSite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String productId;
    private String productName;
    private String brand;
    private Date productionDate;
    private Date expireDate;
    private Boolean isExpandible;
    private ProductionSite productionSite;
    private Date creationTime;
}
