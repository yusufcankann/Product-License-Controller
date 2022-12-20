package com.example.productValidation.service;

import com.example.productValidation.module.Product;
import com.example.productValidation.module.ProductionSite;
import com.example.productValidation.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        log.info("saving new product to the database id:{}",product.getProductId());

        List<Product> products = getAllProducts();

        for(Product p : products){
            if(p.getProductName().equals(product.getProductName())){
                log.info("Product already exist! {}",p.getProductName());
                return null;
            }
        }

        productRepository.save(product);
        return product;
    }

    @Override
    public void addProducts(List<Product> products) {
        for(Product product : products){
            addProduct(product);
        }
    }

    @Override
    public Product getProduct(String id) {
        log.info("geting product from the database id:{}",id);
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new IllegalStateException("product is not exist");
        }
        else{
            return product.get();
        }
    }

    @Override
    public Product getProductWithName(String name) {
        log.info("geting product from the database name:{}",name);
        Optional<Product> product = productRepository.findProductByProductName(name);
        if(product.isEmpty()){
            throw new IllegalStateException("product is not exist");
        }
        else{
            return product.get();
        }
    }

    @Override
    public Product getProduct(String name, String brand) {
        log.info("geting product from the database name:{} brand {}",name,brand);
        Optional<Product> product = productRepository.findProductByProductNameAndBrand(name,brand);
        if(product.isEmpty()){
            throw new IllegalStateException("product is not exist");
        }
        else{
            return product.get();
        }
    }

    // TODO
    //  test edilecek
    @Override
    public List<Product> getProducts(String brand) {
        log.info("geting products from the database brand:{}",brand);
        List<Product> products = productRepository.findProductsByBrand(brand);
        if(products.isEmpty()){
            throw new IllegalStateException("products is not exist");
        }
        else{
            return products;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductsByProductionSite(ProductionSite productionSite){
        List<Product> result = new ArrayList<>();
        List<Product> l = getAllProducts();
        for(Product p : l){
            if(p.getProductionSite().equals(productionSite)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public List<Product> getProductsByProductionDate(Date from, Date to){
        return productRepository.findByProductionDateBetween(from,to);
    }

    @Override
    public List<Product> getProductsByExpireDate(Date from, Date to){
        return productRepository.findByExpireDateBetween(from,to);
    }
}
