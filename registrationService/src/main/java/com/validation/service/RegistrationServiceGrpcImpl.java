package com.validation.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceGrpcImpl extends RegistrationServiceGrpc.RegistrationServiceImplBase{

    private final ProductInfoService productInfoService;

    @Override
    public void registerProduct(Product request, StreamObserver<Product> responseObserver) {
        Product product = productInfoService.registerProduct(request);
        responseObserver.onNext(product);
        responseObserver.onCompleted();
        log.info("product registered with reg obj {} isValid {}",request.getRegistrationId(),product.getIsValid());
    }

    @Override
    public StreamObserver<Product> registerProducts(StreamObserver<Product> responseObserver) {
        return new StreamObserver<Product>() {
            public List<Product> returnList = new ArrayList<>();

            @Override
            public void onNext(Product product) {
                Product addedProduct = productInfoService.registerProduct(product);
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
    public void deleteProductByRegistrationIdAndBrand(Product request, StreamObserver<Product> responseObserver) {
        Product product = productInfoService.deleteProductByRegistrationIdAndBrand(request.getRegistrationId(),request.getBrand());
        responseObserver.onNext(product);
        responseObserver.onCompleted();
        log.info("product deleted with reg id {} brand {}",request.getRegistrationId(),product.getBrand());
    }

    @Override
    public void getProductsByBrandAndProductName(Product request, StreamObserver<Product> responseObserver) {
        List<Product> productList = productInfoService.getProductsByBrandAndProductName(request.getBrand(),request.getProductName());
        List<Product> products = new ArrayList<>(productList);
        products.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductsByBrand(Product request, StreamObserver<Product> responseObserver) {
        List<Product> productList = productInfoService.getProductsByBrand(request.getBrand());
        List<Product> products = new ArrayList<>(productList);
        products.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductByRegistrationIdAndBrandAndName(Product request, StreamObserver<Product> responseObserver) {
        Product product = productInfoService.getProductByRegistrationIdAndBrandAndName(request.getRegistrationId(),
                request.getBrand(),request.getProductName());
        responseObserver.onNext(product);
        responseObserver.onCompleted();
        log.info("product returned with regId {} brand {} Name {}",request.getRegistrationId(),request.getBrand(),
                product.getProductName());
    }

    @Override
    public void getProductByRegistrationIdAndBrand(Product request, StreamObserver<Product> responseObserver) {
        Product product = productInfoService.getProductByRegistrationIdAndBrand(request.getRegistrationId(),
                request.getBrand());
        responseObserver.onNext(product);
        responseObserver.onCompleted();
        log.info("product returned with regId {} brand {} ",request.getRegistrationId(),request.getBrand());
    }

}
