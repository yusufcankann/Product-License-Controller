package com.validation.productValidation.service;

import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.module.Product;
import com.validation.productValidation.dto.ProductResponse;
import com.validation.productValidation.module.ProductionSite;
import com.validation.productValidation.repository.ProductRepository;
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
    public Product addProduct(ProductRequest product) {
        log.info("saving new product to the database brand:{}",product.getBrand());

        if (checkProductExist(product)){
            log.info("Product already exist, brand:{}",product.getBrand());
            return null;
        }

        Product newProduct = Product.builder().productName(product.getProductName()).
                brand(product.getBrand()).productionDate(product.getProductionDate()).
                expireDate(product.getExpireDate()).
                isExpandible(product.getIsExpandible()).
                productionSite(product.getProductionSite()).
                creationTime(product.getCreationTime()).build();
        productRepository.save(newProduct);
        return newProduct;
    }

    private boolean checkProductExist(ProductRequest product) {
        List<ProductResponse> products = getAllProducts();

        for(ProductResponse p : products){
            if(p.getProductName().equals(product.getProductName())){
                log.info("Product already exist! {}",p.getProductName());
                return true;
            }
        }
        return false;
    }

    @Override
    public void addProducts(List<ProductRequest> products) {
        for(ProductRequest product : products){
            addProduct(product);
        }
    }

    @Override
    public ProductResponse getProduct(String id) {
        log.info("geting product from the database id:{}",id);
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new IllegalStateException("product is not exist");
        }
        else{
            return mapToProductResponse(product.get());
        }
    }

    @Override
    public ProductResponse getProductWithName(String name) {
        log.info("geting product from the database name:{}",name);
        Optional<Product> product = productRepository.findProductByProductName(name);
        if(product.isEmpty()){
            throw new IllegalStateException("product is not exist");
        }
        else{
            return mapToProductResponse(product.get());
        }
    }

    @Override
    public ProductResponse getProduct(String name, String brand) {
        log.info("geting product from the database name:{} brand {}",name,brand);
        Optional<Product> product = productRepository.findProductByProductNameAndBrand(name,brand);
        if(product.isEmpty()){
            throw new IllegalStateException("product is not exist");
        }
        else{
            return mapToProductResponse(product.get());
        }
    }

    // TODO
    //  test edilecek
    @Override
    public List<ProductResponse> getProducts(String brand) {
        log.info("geting products from the database brand:{}",brand);
        List<Product> products = productRepository.findProductsByBrand(brand);
        List<ProductResponse> productResponses = getProductResponses(products);
        if(products.isEmpty()){
            throw new IllegalStateException("products is not exist");
        }
        else{
            return productResponses;
        }
    }

    private List<ProductResponse> getProductResponses(List<Product> products) {
        List<ProductResponse> productResponses = products.stream().map(this::mapToProductResponse).toList();
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = getProductResponses(products);
        return productResponses;
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> findProductsByProductionSite(ProductionSite productionSite){
        List<ProductResponse> result = new ArrayList<>();
        List<ProductResponse> l = getAllProducts();
        for(ProductResponse p : l){
            if(p.getProductionSite().equals(productionSite)) {
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public List<ProductResponse> getProductsByProductionDate(Date from, Date to){
        List<Product> products = productRepository.findByProductionDateBetween(from,to);
        List<ProductResponse> productResponses = getProductResponses(products);
        return productResponses;
    }

    @Override
    public List<ProductResponse> getProductsByExpireDate(Date from, Date to){
        List<Product> products = productRepository.findByExpireDateBetween(from,to);
        List<ProductResponse> productResponses = getProductResponses(products);
        return productResponses;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().productId(product.getProductId()).productName(product.getProductName()).
                brand(product.getBrand()).productionDate(product.getProductionDate()).
                expireDate(product.getExpireDate()).
                isExpandible(product.getIsExpandible()).
                productionSite(product.getProductionSite()).
                creationTime(product.getCreationTime()).build();
    }
}
