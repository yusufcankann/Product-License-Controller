package com.validation.service;

import com.validation.service.Product;
import com.validation.service.ValidationRequest;
import com.validation.service.ValidationServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationService {

    @GrpcClient("grpc-validation-service")
    ValidationServiceGrpc.ValidationServiceBlockingStub synchronousClient;

    public Product validateProduct(ValidationRequest request){
        Product response = synchronousClient.validateProduct(request);
        log.info("Product obtained with validationRequest reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }


}
