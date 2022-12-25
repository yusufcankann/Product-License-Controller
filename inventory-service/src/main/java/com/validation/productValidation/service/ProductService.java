package com.validation.productValidation.service;

import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.dto.ValidationRequest;
import com.validation.productValidation.module.Product;
import com.validation.productValidation.dto.ProductResponse;

import java.util.Date;
import java.util.List;

public interface ProductService {

    Product addProduct(ProductRequest product);
    void addProducts(List<ProductRequest> products);
    void deleteProductByRegistrationIdAndBrand(String registrationId,String brand);
    List<ProductResponse> getProductsByBrand(String brand);
    List<ProductResponse> getProductByBrandAndProductName(String brand, String name);
    List<ProductResponse> getProductsByBrandAndProductionDate(String brand,Date from, Date to);
    List<ProductResponse> getProductsByBrandAndExpireDate(String brand,Date from, Date to);
    ProductResponse getProductByRegistrationIdAndBrandAndName(String registrationId,String brand,String name);
    ProductResponse getProductByRegistrationIdAndBrand(String registrationId,String brand);
    List<ProductResponse> getProductByValidationObjectList(List<ValidationRequest> products);

}
