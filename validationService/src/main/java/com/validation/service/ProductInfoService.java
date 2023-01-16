package com.validation.service;

import com.validation.service.Product;
import com.validation.service.ProductInfoServiceGrpc;
import com.validation.service.ValidationRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ProductInfoService {

    @GrpcClient("grpc-product-info-service")
    ProductInfoServiceGrpc.ProductInfoServiceBlockingStub synchronousClient;

//    @GrpcClient("grpc-product-info-service")
//    ProductInfoServiceGrpc.ProductInfoServiceStub asynchronousClient;


    public Product validateProduct(ValidationRequest request){
        Product response = synchronousClient.validateProducts(request);
        log.info("Product obtained with validationRequest reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }


}
