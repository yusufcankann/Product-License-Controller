package com.validation.service;

import com.validation.module.ProductEntity;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    ProductEntity addProduct(ProductEntity product);
    public void addProducts(List<ProductEntity> products);
    void deleteProductByRegistrationIdAndBrand(String registrationId,String brand);
    List<ProductEntity> getProductsByBrand(String brand);
    List<ProductEntity> getProductsByBrandAndProductName(String brand, String name);
    ProductEntity getProductByRegistrationIdAndBrandAndName(String registrationId,String brand,String name);
    ProductEntity getProductByRegistrationIdAndBrand(String registrationId,String brand);
    ProductEntity getProductByValidationObject(ValidationRequest products);
}
