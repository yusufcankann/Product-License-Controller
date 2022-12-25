package org.product.service;

import com.validation.productValidation.module.Product;
import lombok.RequiredArgsConstructor;
import org.product.dto.ProductResponse;
import org.product.dto.ValidationRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final WebClient webClient;

    public List<ProductResponse> validateProduct(ValidationRequest request){
        ProductResponse[] inventoryResponseArray = webClient.method(HttpMethod.GET)
                .uri("http://localhost:8080/api/inventory")
                .body(request,ValidationRequest.class)
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();

        return Arrays.asList(inventoryResponseArray);
    }





}
