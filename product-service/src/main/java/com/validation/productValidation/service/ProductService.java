package com.validation.productValidation.service;

import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.module.Product;
import com.validation.productValidation.dto.ProductResponse;
import com.validation.productValidation.module.ProductionSite;

import java.util.Date;
import java.util.List;

public interface ProductService {


    public Product addProduct(ProductRequest product);
    public void addProducts(List<ProductRequest> products);
    public ProductResponse getProduct(String id);
    public ProductResponse getProductWithName(String name);
    public ProductResponse getProduct(String name, String brand);
    public List<ProductResponse> getProducts(String brand);
    public List<ProductResponse> getAllProducts();
    public void deleteProduct(String id);
//    public void updateProduct();
    public List<ProductResponse> findProductsByProductionSite(ProductionSite productionSite);
    public List<ProductResponse> getProductsByProductionDate(Date from, Date to);
    public List<ProductResponse> getProductsByExpireDate(Date from, Date to);
}
