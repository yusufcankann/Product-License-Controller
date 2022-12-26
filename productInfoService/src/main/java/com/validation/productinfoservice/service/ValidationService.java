package com.validation.productinfoservice.service;

import com.validation.productinfoservice.dto.ProductResponse;
import com.validation.productinfoservice.dto.ValidationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final WebClient webClient;

    public List<ProductResponse> validateProduct(List<ValidationRequest> request){
        ProductResponse[] inventoryResponseArray = webClient.method(HttpMethod.GET)
                .uri("http://localhost:8082/api/product/inventory/validateProducts")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();

        return Arrays.asList(inventoryResponseArray);
    }





}
