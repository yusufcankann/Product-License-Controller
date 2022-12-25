package com.validation.productValidation.controller;

import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.dto.ProductResponse;
import com.validation.productValidation.dto.ValidationRequest;
import com.validation.productValidation.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProduct(@RequestBody ProductRequest product) {
        productService.addProduct(product);
    }

    @PostMapping("/saveProducts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProducts(@RequestBody List<ProductRequest> products) {
        productService.addProducts(products);
    }

    @DeleteMapping(path="delete/{brand}/{registrationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("brand") String brand,@PathVariable("registrationId") String registrationId){
        productService.deleteProductByRegistrationIdAndBrand(brand,registrationId);
    }

    @GetMapping(value = "inventory/product", params = {"brand"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductWithBrand(@RequestParam(required = true) String brand){
            return ResponseEntity.ok().body(productService.getProductsByBrand(brand));
    }

    @GetMapping(value = "inventory/product", params = {"brand","productName"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductWithBrandProductName(@RequestParam(required = true) String brand,
                                                                  @RequestParam(required = true) String productName){
            return ResponseEntity.ok().body(productService.getProductByBrandAndProductName(productName,brand));
    }

    @GetMapping("/inventory/products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductByValidationObjectList(@RequestBody List<ValidationRequest> products) {
        return ResponseEntity.ok().body(productService.getProductByValidationObjectList(products));
    }

    @GetMapping(value = "/inventory/product/productionDate", params = {"brand","start","end"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductsByBrandAndProductionDate(@RequestParam(required = true) String brand,
                                                                                     @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                                                     @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        return ResponseEntity.ok().body(productService.getProductsByBrandAndProductionDate(brand,start,end));
    }

    @GetMapping(value = "/inventory/product/expireDate", params = {"brand","start","end"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductsByBrandAndExpireDate(@RequestParam(required = true) String brand,
                                                                                     @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                                                     @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        return ResponseEntity.ok().body(productService.getProductsByBrandAndExpireDate(brand,start,end));
    }


    @GetMapping(value = "inventory/product", params = {"registrationId","brand","productName"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getProductByRegistrationIdAndBrandAndName(@RequestParam(required = true) String registrationId,
                                                                                           @RequestParam(required = true) String brand,
                                                                                @RequestParam(required = true) String productName){
        return ResponseEntity.ok().body(productService.getProductByRegistrationIdAndBrandAndName(registrationId,productName,brand));
    }

    @GetMapping(value = "inventory/product", params = {"registrationId","brand"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getProductByRegistrationIdAndBrand(@RequestParam(required = true) String registrationId,
                                                                                     @RequestParam(required = true) String brand){
        return ResponseEntity.ok().body(productService.getProductByRegistrationIdAndBrand(registrationId,brand));
    }
}
