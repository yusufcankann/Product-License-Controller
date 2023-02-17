package com.validation.controller;

import com.validation.model.ProductEntity;
import com.validation.model.RequestEntity;
import com.validation.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class ValidationController {

    @Autowired
    private final ValidationService validationService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping(value = "validateProduct")
    public ProductEntity validateProduct(@RequestBody RequestEntity request){
        return ProductEntity.fromProto(validationService.validateProduct(request.toProto()));
    }

}
