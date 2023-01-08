package com.validation.service;

import com.google.protobuf.Timestamp;
import com.validation.productinfoservice.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


//@GrpcService
//@RequiredArgsConstructor
//@Transactional
@Slf4j
public class InventoryInfoService {

    ManagedChannel channelInventoryService = ManagedChannelBuilder.forAddress("localhost",8082).usePlaintext().build();


    public void validateProduct(ValidationRequest validationRequest){

        List<Product> validatedProducts = Collections.emptyList();

        InventoryServiceGrpc.InventoryServiceStub inventoryServiceBlockingStub =
                InventoryServiceGrpc.newStub(channelInventoryService);

        StreamObserver<ValidationRequest> validateObserver = inventoryServiceBlockingStub.getProductByValidationObjectList(new StreamObserver<>() {
            @Override
            public void onNext(Product product) {
                if(product.getIsValid()){
                    validatedProducts.add(product);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Something went wrong while validating multiple products.",throwable);
            }

            @Override
            public void onCompleted() {
                log.error("Products successfully validated.");
            }
        });


        //Send request
        validateObserver.onNext(ValidationRequest.newBuilder().setBrand(validationRequest.getBrand()).
                setRegistrationId(validationRequest.getRegistrationId()).build());

        validateObserver.onCompleted();
    }

}
