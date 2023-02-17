package com.validation.controller;

import com.validation.model.ProductEntity;
import com.validation.service.Product;
import com.validation.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/company/product")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @PostMapping(value = "add")
    public Optional<ProductEntity> addProduct(@RequestBody ProductEntity product){
        return Optional.of(ProductEntity.fromProto(productService.registerProduct(product.toProto())));

    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @PostMapping(value = "addMultiple")
    public List<ProductEntity> registerProducts(@RequestBody List<ProductEntity> productList){
        List<Product> productProtoList = new ArrayList<>();
        productList.forEach(p -> productProtoList.add(p.toProto()));

        List<Product> returnProtoList = productService.registerProducts(productProtoList);
        List<ProductEntity> returnEntityList = new ArrayList<>();
        returnProtoList.forEach(p -> returnEntityList.add(ProductEntity.fromProto(p)));
        return returnEntityList;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @DeleteMapping(value = "delete", params = {"registrationId","brand"})
    public ProductEntity deleteProductByRegistrationIdAndBrand(@RequestParam(required = true) String registrationId,
                                                               @RequestParam(required = true) String brand){
        return ProductEntity.fromProto(productService.deleteProductByRegistrationIdAndBrand(registrationId,brand));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getProductByRegIdAndBrand", params = {"registrationId","brand"})
    public ProductEntity getProductByRegistrationIdAndBrand(@RequestParam(required = true) String registrationId,
                                                            @RequestParam(required = true) String brand){
        return ProductEntity.fromProto(productService.getProductByRegistrationIdAndBrand(registrationId,brand));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getProducts", params = {"brand"})
    public List<ProductEntity> getProductsByBrand(@RequestParam(required = true) String brand){
        List<Product> protoList = productService.getProductsByBrand(brand);
        List<ProductEntity> returnEntityList = new ArrayList<>();
        protoList.forEach(p -> returnEntityList.add(ProductEntity.fromProto(p)));
        return returnEntityList;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getProductsByBrandAndProductName", params = {"brand","name"})
    public List<ProductEntity> getProductsByBrandAndProductName(@RequestParam(required = true) String brand,
                                                          @RequestParam(required = true) String name){
        List<Product> protoList = productService.getProductsByBrandAndProductName(brand,name);
        List<ProductEntity> returnEntityList = new ArrayList<>();
        protoList.forEach(p -> returnEntityList.add(ProductEntity.fromProto(p)));
        return returnEntityList;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getProduct", params = {"registrationId","brand","name"})
    public ProductEntity getProductByRegistrationIdAndBrandAndName(@RequestBody String registrationId,
                                                                   @RequestBody String brand,
                                                                   @RequestBody String name){
        return ProductEntity.fromProto(productService.getProductByRegistrationIdAndBrandAndName(registrationId,brand,name));
    }

}
