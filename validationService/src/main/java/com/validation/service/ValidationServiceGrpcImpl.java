package com.validation.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class ValidationServiceGrpcImpl extends ValidationServiceGrpc.ValidationServiceImplBase{

    private final ProductInfoService productInfoService;

    @Override
    public void validateProduct(ValidationRequest request, StreamObserver<Product> responseObserver) {
        Product product = productInfoService.validateProduct(request);
        responseObserver.onNext(product);
        responseObserver.onCompleted();
        log.info("product validated with reg obj {} isValid {}",request.getRegistrationId(),product.getIsValid());
    }
}


