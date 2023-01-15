package com.validation.service;

import com.validation.module.ProductEntity;
import com.validation.service.InventoryServiceGrpc;
import com.validation.service.Product;
import com.validation.service.ValidationRequest;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceGrpcImpl extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductService productService;

    @Override
    public void addProduct(Product request, StreamObserver<Product> responseObserver) {
        ProductEntity addedProduct = productService.addProduct(ProductEntity.fromProto(request));
        if(Objects.isNull(addedProduct)){
            log.error("product could not added reg Id {}",request.getRegistrationId());
            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onNext(addedProduct.toProto());
        responseObserver.onCompleted();
        log.info("product added reg Id {}",request.getRegistrationId());
    }

    @Override
    public StreamObserver<Product> addProducts(StreamObserver<Product> responseObserver) {
        return new StreamObserver<Product>() {
            List<Product> returnList = new ArrayList<>();

            @Override
            public void onNext(Product product) {
                ProductEntity addedProduct = productService.addProduct(ProductEntity.fromProto(product));
                if(Objects.isNull(addedProduct)){
                    log.error("product could not added reg Id {}",product.getRegistrationId());
                    return;
                }
                returnList.add(product);
                log.info("product with name: {} regid: {} succesfully added to db",product.getProductName(),product.getRegistrationId());
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
                log.error("Something went wrong while adding multiple products.",throwable);
            }

            @Override
            public void onCompleted() {
                returnList.forEach(responseObserver::onNext);
                responseObserver.onCompleted();
                log.error("All products successfully added to database");
            }
        };
    }

    @Override
    public void getProductByValidationObject(ValidationRequest request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.getProductByValidationObject(request).toProto());
        responseObserver.onCompleted();
        log.info("product getted with reg obj {}",request.getRegistrationId());
    }

    @Override
    public void getProductByRegistrationIdAndBrand(Product request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.getProductByRegistrationIdAndBrand(request.getRegistrationId(),
                request.getBrand()).toProto());
        responseObserver.onCompleted();
        log.info("product getted by reg Id and brand {} {}",request.getRegistrationId(),request.getBrand());
    }

    @Override
    public void getProductByRegistrationIdAndBrandAndName(Product request, StreamObserver<Product> responseObserver) {
        responseObserver.onNext(productService.getProductByRegistrationIdAndBrandAndName(request.getRegistrationId(),
                request.getBrand(),request.getProductName()).toProto());
        responseObserver.onCompleted();
        log.info("product getted by reg Id and brand and name {} {} {}",request.getRegistrationId(),request.getBrand(),
                request.getProductName());
    }

    @Override
    public void getProductsByBrandAndProductName(Product request, StreamObserver<Product> responseObserver) {
        List<Product> products = new ArrayList<>();
        List<ProductEntity> entityList = productService.getProductsByBrandAndProductName(request.getBrand(),request.getProductName());
        entityList.stream().map(ProductEntity::toProto).forEach(products::add);
        products.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByBrand(Product request, StreamObserver<Product> responseObserver) {
        List<Product> products = new ArrayList<>();
        List<ProductEntity> entityList = productService.getProductsByBrand(request.getBrand());
        entityList.stream().map(ProductEntity::toProto).forEach(products::add);
        products.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteProductByRegistrationIdAndBrand(Product request, StreamObserver<Product> responseObserver) {

        ProductEntity product = productService.getProductByRegistrationIdAndBrand(request.getRegistrationId(),request.getBrand());
        productService.deleteProductByRegistrationIdAndBrand(request.getRegistrationId(),request.getBrand());
        responseObserver.onNext(product.toProto());
        responseObserver.onCompleted();
        log.info("product removed by reg Id and brand {} {}",request.getRegistrationId(),request.getBrand());
    }

}
