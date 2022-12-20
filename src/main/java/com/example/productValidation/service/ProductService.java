package com.example.productValidation.service;

import com.example.productValidation.module.Product;
import com.example.productValidation.module.ProductionSite;

import java.util.Date;
import java.util.List;

public interface ProductService {


    public Product addProduct(Product product);
    public void addProducts(List<Product> products);
    public Product getProduct(String id);
    public Product getProductWithName(String name);
    public Product getProduct(String name,String brand);
    public List<Product> getProducts(String brand);
    public List<Product> getAllProducts();
    public void deleteProduct(String id);
//    public void updateProduct();
    public List<Product> findProductsByProductionSite(ProductionSite productionSite);
    public List<Product> getProductsByProductionDate(Date from, Date to);
    public List<Product> getProductsByExpireDate(Date from, Date to);
}
