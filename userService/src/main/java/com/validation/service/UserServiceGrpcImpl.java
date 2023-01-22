package com.validation.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase{
    private final ValidationService validationService;

    @Override
    public void validateProduct(ValidationRequest request, StreamObserver<Product> responseObserver) {
        Product product = validationService.validateProduct(request);
        responseObserver.onNext(product);
        responseObserver.onCompleted();
        log.info("product validated with reg obj {} isValid {}",request.getRegistrationId(),product.getIsValid());
    }
}
