package org.product.controller;

import lombok.AllArgsConstructor;
import org.product.dto.ProductResponse;
import org.product.dto.ValidationRequest;
import org.product.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="api/productInfo")
public class ProductInfoController {

    public ValidationService validationService;

    @GetMapping(value = "productInfo/validateProduct")
    @ResponseStatus(HttpStatus.OK)
    List<ProductResponse> validateProduct(@RequestBody ValidationRequest validateRequest){
        return validationService.validateProduct(validateRequest);
    }



}
