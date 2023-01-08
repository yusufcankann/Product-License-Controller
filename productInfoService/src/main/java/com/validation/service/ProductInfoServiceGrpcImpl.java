package com.validation.service;


import com.validation.productinfoservice.Product;
import com.validation.productinfoservice.ProductInfoServiceGrpc;
import com.validation.productinfoservice.ValidationRequest;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductInfoServiceGrpcImpl extends ProductInfoServiceGrpc.ProductInfoServiceImplBase {

    InventoryInfoService inventoryInfoService;

    @Override
    public StreamObserver<ValidationRequest> validateProducts(StreamObserver<Product> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(ValidationRequest product) {
                inventoryInfoService.validateProduct(product);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Something went wrong while validating multiple products.",throwable);
            }

            @Override
            public void onCompleted() {
                log.error("All products successfully validated to database");
            }
        };
    }




    //    rpc validateProducts (stream ValidationRequest) returns (Product);
//    rpc registerProducts (stream Product) returns (stream Product);
//    rpc deleteProduct(Product) returns (Product);
//    rpc getProductsByBrand(Product) returns (stream Product);
//    rpc getProductByBrandAndProductName(Product) returns (stream Product);
//    rpc getProductByRegistrationIdAndBrandAndName(Product) returns (Product);
//    rpc getProductByRegistrationIdAndBrand(Product) returns (Product);


}
