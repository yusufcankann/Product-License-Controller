package com.validation.productinfoservice.controller;

import com.validation.productinfoservice.dto.ProductResponse;
import com.validation.productinfoservice.dto.ValidationRequest;
import com.validation.productinfoservice.service.ValidationService;
import lombok.AllArgsConstructor;
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
    List<ProductResponse> validateProduct(@RequestBody List<ValidationRequest> validateRequest){
        return validationService.validateProduct(validateRequest);
    }



}
