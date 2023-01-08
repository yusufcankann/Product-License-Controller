package com.validation.service;

import com.validation.inventoryservice.ValidationRequest;
import com.validation.module.ProductEntity;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    ProductEntity addProduct(ProductEntity product);
    public void addProducts(List<ProductEntity> products);
    void deleteProductByRegistrationIdAndBrand(String registrationId,String brand);
    List<ProductEntity> getProductsByBrand(String brand);
    ProductEntity getProductByBrandAndProductName(String brand, String name);
    ProductEntity getProductByRegistrationIdAndBrandAndName(String registrationId,String brand,String name);
    ProductEntity getProductByRegistrationIdAndBrand(String registrationId,String brand);
    List<ProductEntity> getProductByValidationObjectList(List<ValidationRequest> products);
}

