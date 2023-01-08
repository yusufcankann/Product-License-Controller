package com.validation.service;

import com.validation.inventoryservice.Product;
import com.validation.inventoryservice.InventoryServiceGrpc;
import com.validation.inventoryservice.ValidationRequest;
import com.validation.module.ProductEntity;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryServiceGrpcImpl extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductService productService;

    @Override
    public void addProduct(Product request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.addProduct(ProductEntity.fromProto(request)).toProto());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Product> addProducts(StreamObserver<Product> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(Product product) {
                productService.addProduct(ProductEntity.fromProto(product)).toProto();
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Something went wrong while adding multiple products.",throwable);
            }

            @Override
            public void onCompleted() {
                log.error("All products successfully added to database");
            }
        };
    }

    @Override
    public void deleteProductByRegistrationIdAndBrand(Product request, StreamObserver<Product> responseObserver) {
        productService.deleteProductByRegistrationIdAndBrand(request.getRegistrationId(),request.getBrand());
        responseObserver.onNext(Product.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByBrand(Product request, StreamObserver<Product> responseObserver) {
        List<ProductEntity> products = productService.getProductsByBrand(request.getBrand());
        products.forEach(e -> responseObserver.onNext(e.toProto()));
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByBrandAndProductName(Product request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.getProductByBrandAndProductName(request.getBrand(),
                request.getProductName()).toProto());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByRegistrationIdAndBrandAndName(Product request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.getProductByRegistrationIdAndBrandAndName(request.getRegistrationId(),
                request.getBrand(),request.getProductName()).toProto());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByRegistrationIdAndBrand(Product request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.getProductByRegistrationIdAndBrand(request.getRegistrationId(),
                request.getBrand()).toProto());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<ValidationRequest> getProductByValidationObjectList
            (StreamObserver<Product> responseObserver) {

        return new StreamObserver<>() {
            @Override
            public void onNext(ValidationRequest product) {
                productService.getProductByRegistrationIdAndBrand(product.getRegistrationId(),product.getBrand()).toProto();
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

}
