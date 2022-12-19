package com.example.productvalidation.service;

import com.example.productvalidation.module.Product;
import com.example.productvalidation.module.ProductionSite;

import java.util.Date;
import java.util.List;

public interface ProductService {


    public Product addProduct(Product product);
    Product getProduct(String id);
    Product getProductWithName(String name);
    Product getProduct(String name,String brand);
    public List<Product> getProducts(String brand);
    public List<Product> getAllProducts();
    public void deleteProduct(String id);
//    public void updateProduct();
    public List<Product> findProductsByProductionSite(ProductionSite productionSite);
    public List<Product> getProductsByProductionDate(Date from, Date to);
    public List<Product> getProductsByExpireDate(Date from, Date to);
}
