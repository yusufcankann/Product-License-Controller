package com.validation.productValidation.service;

import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.dto.ValidationRequest;
import com.validation.productValidation.module.Product;
import com.validation.productValidation.dto.ProductResponse;
import com.validation.productValidation.module.ProductionSite;
import com.validation.productValidation.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        Product newProduct = Product.builder().
                registrationId(product.getRegistrationId()).
                productName(product.getProductName()).
                brand(product.getBrand()).
                productionDate(product.getProductionDate()).
                expireDate(product.getExpireDate()).
                isExpandible(product.getIsExpandible()).
                productionSite(product.getProductionSite()).
                creationTime(product.getCreationTime()).build();
        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public void addProducts(List<ProductRequest> products) {
        for(ProductRequest product : products){
            addProduct(product);
        }
    }

    @Override
    public void deleteProductByRegistrationIdAndBrand(String registrationId,String brand) {
        productRepository.deleteByRegistrationIdAndBrand(registrationId,brand);
    }

    @Override
    public List<ProductResponse> getProductsByBrand(String brand) {
        log.info("geting products from the database brand:{}",brand);
        List<Product> products = productRepository.findProductsByBrand(brand);
        return getProductResponses(products);
    }

    @Override
    public List<ProductResponse> getProductByBrandAndProductName(String brand, String name) {
        log.info("geting products from the database brand {} productionName {}",brand,name);
        List<Product> products = productRepository.findProductsByBrandAndProductName(brand,name);
        return getProductResponses(products);
    }

    @Override
    public List<ProductResponse> getProductsByBrandAndProductionDate(String brand, Date from, Date to) {
        log.info("geting products from the database brand:{} prodDate from {} to {}",brand,from,to);
        List<Product> products = productRepository.findByBrandAndProductionDateBetween(brand,from,to);
        return getProductResponses(products);
    }

    @Override
    public List<ProductResponse> getProductsByBrandAndExpireDate(String brand, Date from, Date to) {
        log.info("geting products from the database brand:{} expDate from {} to {}",brand,from,to);
        List<Product> products = productRepository.findByBrandAndExpireDateBetween(brand,from,to);
        return getProductResponses(products);
    }

    @Override
    public ProductResponse getProductByRegistrationIdAndBrandAndName(String registrationId, String brand, String name) {
        log.info("geting product from the database registrationId {} brand {} name {}",registrationId,brand,name);
        Optional<Product> product = productRepository.findByRegistrationIdAndBrandAndProductName(registrationId,brand,name);
        Product retVal = product.orElseGet(() -> createNonValidProduct(registrationId, brand, name));
        return mapToProductResponse(retVal);
    }

    @Override
    public ProductResponse getProductByRegistrationIdAndBrand(String registrationId, String brand) {
        log.info("geting product from the database registrationId {} brand {}",registrationId,brand);
        Optional<Product> product = productRepository.findByRegistrationIdAndBrand(registrationId,brand);
        Product retVal = product.orElseGet(() -> createNonValidProduct(registrationId, brand,""));
        return mapToProductResponse(retVal);
    }

    @Override
    public List<ProductResponse> getProductByValidationObjectList(List<ValidationRequest> products) {

        List<ProductResponse> responseList = new ArrayList<>();

        for(ValidationRequest validationRequest : products){
            log.info("geting product from the database with validationRequest object registrationId {} brand {}",
                    validationRequest.getBrand(),validationRequest.getRegistrationId());

            Optional<Product> product = productRepository.findByRegistrationIdAndBrand(validationRequest.getRegistrationId(),
                    validationRequest.getBrand());

            Product retVal = product.orElseGet(() -> createNonValidProduct(validationRequest.getRegistrationId(),
                    validationRequest.getBrand(),""));

            responseList.add(mapToProductResponse(retVal));

        }
        return responseList;
    }

    private boolean checkProductExist(ProductRequest product) {
        List<ProductResponse> products = getProductsByBrand(product.getBrand());

        for(ProductResponse p : products){
            if(p.getProductName().equals(product.getProductName())){
                log.info("Product already exist! {}",p.getProductName());
                return true;
            }
            if(p.getRegistrationId().equals(product.getRegistrationId())){
                log.info("Product regId already exist! {}",p.getProductName());
                return true;
            }
        }
        return false;
    }

    private Product createNonValidProduct(String registrationId,String brand,String name){
        Date junkDate = new Date();
        Product product = new Product("",registrationId,name,brand,
                junkDate,junkDate,false,
                new ProductionSite("","",""),junkDate);
        return product;
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = ProductResponse.builder().productId(product.getProductId()).
                registrationId(product.getRegistrationId()).
                productName(product.getProductName()).
                brand(product.getBrand()).productionDate(product.getProductionDate()).
                expireDate(product.getExpireDate()).
                isExpandible(product.getIsExpandible()).
                productionSite(product.getProductionSite()).
                creationTime(product.getCreationTime()).build();

        if(Objects.equals(response.getProductId(), "")){
            response.setValid(false);
        }else{
            response.setValid(true);
        }
        return response;
    }

    private List<ProductResponse> getProductResponses(List<Product> products) {
        List<ProductResponse> productResponses = products.stream().map(this::mapToProductResponse).toList();
        return productResponses;
    }































//
//
//
//    private boolean checkProductExist(ProductRequest product) {
//        List<ProductResponse> products = getAllProducts();
//
//        for(ProductResponse p : products){
//            if(p.getProductName().equals(product.getProductName())){
//                log.info("Product already exist! {}",p.getProductName());
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    @Override
//    public ProductResponse getProduct(String id) {
//        log.info("geting product from the database id:{}",id);
//        Optional<Product> product = productRepository.findById(id);
//        if(product.isEmpty()){
//            throw new IllegalStateException("product is not exist");
//        }
//        else{
//            return mapToProductResponse(product.get());
//        }
//    }
//
//    @Override
//    public ProductResponse getProductWithName(String name) {
//        log.info("geting product from the database name:{}",name);
//        Optional<Product> product = productRepository.findProductByProductName(name);
//        if(product.isEmpty()){
//            throw new IllegalStateException("product is not exist");
//        }
//        else{
//            return mapToProductResponse(product.get());
//        }
//    }
//
//    @Override
//    public ProductResponse getProduct(String name, String brand) {
//        log.info("geting product from the database name:{} brand {}",name,brand);
//        Optional<Product> product = productRepository.findProductByProductNameAndBrand(name,brand);
//        if(product.isEmpty()){
//            throw new IllegalStateException("product is not exist");
//        }
//        else{
//            return mapToProductResponse(product.get());
//        }
//    }
//
//    @Override
//    public List<ProductResponse> getProductsWithRegIdAndBrandAndName(List<ProductRequest> products){
//        List<ProductResponse> responses = new ArrayList<>();
//        for(ProductRequest request : products){
//            log.info("geting products from the reg Id:{} brand {}",request.getRegistrationId(),request.getBrand());
//            Optional<Product> product = productRepository.findByRegistrationIdAndBrandAndName(
//                    request.getRegistrationId(),request.getBrand(),request.getProductName());
//
//            if(product.isEmpty()){
//                Product junkProduct = createNonValidProduct(request.getRegistrationId(),
//                        request.getBrand(),request.getProductName());
//                responses.add(mapToProductResponse(junkProduct));
//            }
//            else{
//                responses.add(mapToProductResponse(product.get()));
//            }
//        }
//        return responses;
//    }
//
//
//    @Override
//    public List<ProductResponse> getProducts(String brand) {
//        log.info("geting products from the database brand:{}",brand);
//        List<Product> products = productRepository.findProductsByBrand(brand);
//        List<ProductResponse> productResponses = getProductResponses(products);
//        if(products.isEmpty()){
//            throw new IllegalStateException("products is not exist");
//        }
//        else{
//            return productResponses;
//        }
//    }
//

//
//    @Override
//    public List<ProductResponse> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        List<ProductResponse> productResponses = getProductResponses(products);
//        return productResponses;
//    }
//

//
//    @Override
//    public List<ProductResponse> findProductsByProductionSite(ProductionSite productionSite){
//        List<ProductResponse> result = new ArrayList<>();
//        List<ProductResponse> l = getAllProducts();
//        for(ProductResponse p : l){
//            if(p.getProductionSite().equals(productionSite)) {
//                result.add(p);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public List<ProductResponse> getProductsByProductionDate(Date from, Date to){
//        List<Product> products = productRepository.findByProductionDateBetween(from,to);
//        List<ProductResponse> productResponses = getProductResponses(products);
//        return productResponses;
//    }
//
//    @Override
//    public List<ProductResponse> getProductsByExpireDate(Date from, Date to){
//        List<Product> products = productRepository.findByExpireDateBetween(from,to);
//        List<ProductResponse> productResponses = getProductResponses(products);
//        return productResponses;
//    }

}
