package com.validation.service;

import com.validation.inventoryservice.ValidationRequest;
import com.validation.module.ProductEntity;
import com.validation.module.ProductionSiteEntity;
import com.validation.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;


    @Override
    public ProductEntity addProduct(ProductEntity product) {
        log.info("saving new product to the database brand:{}",product.getBrand());

        if (checkProductExist(product)){
            log.info("Product already exist, brand:{}",product.getBrand());
            return null;
        }

        ProductEntity newProduct = ProductEntity.builder().
                registrationId(product.getRegistrationId()).
                productName(product.getProductName()).
                brand(product.getBrand()).
                productionDate(product.getProductionDate()).
                expireDate(product.getExpireDate()).
                isExpandable(product.getIsExpandable()).
                productionSite(product.getProductionSite()).
                creationTime(product.getCreationTime()).build();
        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public void addProducts(List<ProductEntity> products) {
        for(ProductEntity product : products){
            addProduct(product);
        }
    }

    @Override
    public void deleteProductByRegistrationIdAndBrand(String registrationId,String brand) {
        productRepository.deleteByRegistrationIdAndBrand(registrationId,brand);
    }

    @Override
    public List<ProductEntity> getProductsByBrand(String brand) {
        log.info("geting products from the database brand:{}",brand);
        return productRepository.findProductsByBrand(brand);
    }

    @Override
    public ProductEntity getProductByBrandAndProductName(String brand, String name) {
        log.info("geting products from the database brand {} productionName {}",brand,name);
        Optional<ProductEntity> product = productRepository.findProductByBrandAndProductName(brand,name);
        return product.orElseGet(() -> createNonValidProduct("", brand, name));
    }

    @Override
    public ProductEntity getProductByRegistrationIdAndBrandAndName(String registrationId, String brand, String name) {
        log.info("geting product from the database registrationId {} brand {} name {}",registrationId,brand,name);
        Optional<ProductEntity> product = productRepository.findByRegistrationIdAndBrandAndName(registrationId,brand,name);
        ProductEntity retVal = product.orElseGet(() -> createNonValidProduct(registrationId, brand, name));
        return retVal;
    }

    @Override
    public ProductEntity getProductByRegistrationIdAndBrand(String registrationId, String brand) {
        log.info("geting product from the database registrationId {} brand {}",registrationId,brand);
        Optional<ProductEntity> product = productRepository.findByRegistrationIdAndBrand(registrationId,brand);
        ProductEntity retVal = product.orElseGet(() -> createNonValidProduct(registrationId, brand,""));
        return retVal;
    }

    @Override
    public List<ProductEntity> getProductByValidationObjectList(List<ValidationRequest> products) {

        List<ProductEntity> responseList = new ArrayList<>();

        for(ValidationRequest validationRequest : products){
            log.info("geting product from the database with validationRequest object registrationId {} brand {}",
                    validationRequest.getBrand(),validationRequest.getRegistrationId());

            Optional<ProductEntity> product = productRepository.findByRegistrationIdAndBrand(
                    validationRequest.getRegistrationId(),validationRequest.getBrand());

            ProductEntity retVal = product.orElseGet(() -> createNonValidProduct(validationRequest.getRegistrationId(),
                    validationRequest.getBrand(),""));

            responseList.add(retVal);

        }
        return responseList;
    }

    private boolean checkProductExist(ProductEntity product) {
        List<ProductEntity> products = getProductsByBrand(product.getBrand());

        for(ProductEntity p : products){
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

    private ProductEntity createNonValidProduct(String registrationId,String brand,String name){
        LocalDate junkDate = LocalDate.now();
        ProductEntity product = new ProductEntity("",registrationId,name,brand,
                junkDate,junkDate,false,
                new ProductionSiteEntity("","",""),junkDate,false);
        return product;
    }

}