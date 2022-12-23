package com.validation.productValidation.controller;

import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.dto.ProductResponse;
import com.validation.productValidation.module.Product;
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

    @GetMapping(value = "user/products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProduct(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping(value = "user/product", params = "id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getProductWithId(@RequestParam(required = true) String id ){
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @GetMapping(value = "user/product", params = {"brand","productName"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getProductWithBrandProductName(@RequestParam(required = true) String brand,
                                                                  @RequestParam(required = true) String productName){
            return ResponseEntity.ok().body(productService.getProduct(productName,brand));
    }

    @GetMapping(value = "/user/product/productiondate", params = {"start","end"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductWithProductionDate(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                                      @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        return ResponseEntity.ok().body(productService.getProductsByProductionDate(start,end));
    }

    @GetMapping(value = "/user/product/expiredate", params = {"start","end"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getProductWithExpireDate(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                                  @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        return ResponseEntity.ok().body(productService.getProductsByExpireDate(start,end));
    }

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

    @DeleteMapping(path="delete/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
    }

}
