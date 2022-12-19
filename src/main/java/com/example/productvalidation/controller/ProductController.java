package com.example.productvalidation.controller;

import com.example.productvalidation.module.Product;
import com.example.productvalidation.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ResponseEntity.ok().body(productService.getProduct(brand,productName));
    }

    @GetMapping(value = "/user/product/productiondate", params = {"start","end"})
    public ResponseEntity<List<Product>> getProductWithProductionDate(@RequestParam(required = true) Date start,
                                                             @RequestParam(required = true) Date end){
        return ResponseEntity.ok().body(productService.getProductsByProductionDate(start,end));
    }

    @GetMapping(value = "/user/product/expiredate", params = {"start","end"})
    public ResponseEntity<List<Product>> getProductWithExpireDate(@RequestParam(required = true) Date start,
                                                                      @RequestParam(required = true) Date end){
        return ResponseEntity.ok().body(productService.getProductsByExpireDate(start,end));
    }


    @PostMapping("/save")
    public void addNewProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping(path="{productId}")
    public void deleteStudent(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
    }

}
