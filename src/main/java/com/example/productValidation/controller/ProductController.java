package com.example.productValidation.controller;

import com.example.productValidation.module.Product;
import com.example.productValidation.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<List<Product>> getProduct(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping(value = "user/product", params = "id")
    public ResponseEntity<Product> getProductWithId(@RequestParam(required = true) String id ){
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @GetMapping(value = "user/product", params = {"brand","productName"})
    public ResponseEntity<Product> getProductWithBrandProductName(@RequestParam(required = true) String brand,
                                                                     @RequestParam(required = true) String productName){
            return ResponseEntity.ok().body(productService.getProduct(productName,brand));
    }

    @GetMapping(value = "/user/product/productiondate", params = {"start","end"})
    public ResponseEntity<List<Product>> getProductWithProductionDate(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                             @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        return ResponseEntity.ok().body(productService.getProductsByProductionDate(start,end));
    }

    @GetMapping(value = "/user/product/expiredate", params = {"start","end"})
    public ResponseEntity<List<Product>> getProductWithExpireDate(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                                      @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        return ResponseEntity.ok().body(productService.getProductsByExpireDate(start,end));
    }

    @PostMapping("/save")
    public void addNewProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @PostMapping("/saveProducts")
    public void addProducts(@RequestBody List<Product> products) {
        productService.addProducts(products);
    }

    @DeleteMapping(path="delete/{productId}")
    public void deleteStudent(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
    }

}
