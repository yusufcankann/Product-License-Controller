package com.validation.service;

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

    @GrpcClient("grpc-product-info-service")
    ProductInfoServiceGrpc.ProductInfoServiceStub asynchronousClient;


    public Product registerProduct(Product product){
        Product p = synchronousClient.registerProduct(product);

        if(Objects.isNull(p)){
            log.error("product could not registered regId: {}",product.getRegistrationId());
            return null;
        }

        if(p.getRegistrationId().equals(product.getRegistrationId())) {
            log.info("Product succesfull added to inventoryService...");
        }
        return p;
    }

    public List<Product> registerProducts(List<Product> productList){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Product> response = new ArrayList<>();

        StreamObserver<Product> responseObserver = asynchronousClient.registerProducts(new StreamObserver<Product>() {
            @Override
            public void onNext(Product product) {
                response.add(product);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error on while adding products ",throwable);
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        productList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
        boolean await = false;
        try {
            await = countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.info("latch interrupted!",e);
        }
        return await ? response : Collections.emptyList();
    }

    public Product deleteProductByRegistrationIdAndBrand(String registrationId, String brand){
        Product request = Product.newBuilder().setRegistrationId(registrationId).setBrand(brand).build();
        Product response = synchronousClient.deleteProductByRegistrationIdAndBrand(request);
        log.info("Product removed by registrationId and brand reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }

    public Product getProductByRegistrationIdAndBrand(String registrationId, String brand){
        Product request = Product.newBuilder().setRegistrationId(registrationId).setBrand(brand).build();
        Product response = synchronousClient.getProductByRegistrationIdAndBrand(request);
        log.info("Product obtained by registrationId and brand reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }

    public List<Product> getProductsByBrand(String brand){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Product> response = new ArrayList<>();
        Product request = Product.newBuilder().setBrand(brand).build();
        asynchronousClient.getProductsByBrand(request,new StreamObserver<Product>() {
            @Override
            public void onNext(Product product) {
                response.add(product);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error on while getting products ",throwable);
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = false;
        try {
            await = countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.info("latch interrupted!",e);
        }
        return await ? response : Collections.emptyList();
    }

    public List<Product> getProductsByBrandAndProductName(String brand,String name){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Product> response = new ArrayList<>();
        Product request = Product.newBuilder().setBrand(brand).setProductName(name).build();
        asynchronousClient.getProductsByBrandAndProductName(request,new StreamObserver<Product>() {
            @Override
            public void onNext(Product book) {
                response.add(book);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error on while getting products ",throwable);
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = false;
        try {
            await = countDownLatch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.info("latch interrupted!",e);
        }
        return await ? response : Collections.emptyList();
    }

    public Product getProductByRegistrationIdAndBrandAndName(String registrationId, String brand,String name){
        Product request = Product.newBuilder().setRegistrationId(registrationId).setBrand(brand).setProductName(name).build();
        Product response = synchronousClient.getProductByRegistrationIdAndBrandAndName(request);
        log.info("Product obtained by registrationId and brand and name reqId {} brand {} name {}",
                response.getRegistrationId(),response.getBrand(),response.getProductName());
        return response;
    }


}
