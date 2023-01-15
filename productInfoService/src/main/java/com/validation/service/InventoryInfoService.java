package com.validation.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Slf4j
@Service
public class InventoryInfoService {

    @GrpcClient("grpc-inventory-service")
    InventoryServiceGrpc.InventoryServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-inventory-service")
    InventoryServiceGrpc.InventoryServiceStub asynchronousClient;

    public Product addProduct(Product product){
        Product p = synchronousClient.addProduct(product);

        if(Objects.isNull(p)){
            log.error("product could not registered regId: {}",product.getRegistrationId());
            return null;
        }

        if(p.getRegistrationId().equals(product.getRegistrationId())) {
            log.info("Product succesfull added to inventoryService...");
        }
        return p;
    }

    public List<Product> addProducts(List<Product> productList){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Product> response = new ArrayList<>();

        StreamObserver<Product> responseObserver = asynchronousClient.addProducts(new StreamObserver<Product>() {
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

    public Product getProductByValidationObject(ValidationRequest valid){
        Product response = synchronousClient.getProductByValidationObject(valid);
        log.info("Product obtained with validationRequest reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }

    public Product getProductByRegistrationIdAndBrand(String registrationId, String brand){
        Product request = Product.newBuilder().setRegistrationId(registrationId).setBrand(brand).build();
        Product response = synchronousClient.getProductByRegistrationIdAndBrand(request);
        log.info("Product obtained by registrationId and brand reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }

    public Product getProductByRegistrationIdAndBrandAndName(String registrationId, String brand,String name){
        Product request = Product.newBuilder().setRegistrationId(registrationId).setBrand(brand).setProductName(name).build();
        Product response = synchronousClient.getProductByRegistrationIdAndBrandAndName(request);
        log.info("Product obtained by registrationId and brand and name reqId {} brand {} name {}",
                response.getRegistrationId(),response.getBrand(),response.getProductName());
        return response;
    }

    public List<Product> getProductsByBrandAndProductName(String brand,String name){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Product> response = new ArrayList<>();
        Product request = Product.newBuilder().setBrand(brand).setProductName(name).build();
        asynchronousClient.getProductsByBrandAndProductName(request,new StreamObserver<Product>() {
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

    public Product deleteProductByRegistrationIdAndBrand(String registrationId, String brand){
        Product request = Product.newBuilder().setRegistrationId(registrationId).setBrand(brand).build();
        Product response = synchronousClient.deleteProductByRegistrationIdAndBrand(request);
        log.info("Product removed by registrationId and brand reqId {} brand {}",response.getRegistrationId(),response.getBrand());
        return response;
    }

    public boolean validateProduct(ValidationRequest request){
        Product product = getProductByValidationObject(request);
        return product.getIsValid();
    }

}




















































































































//
//
//
//
//
//
//
//package com.validation.service;
//
//        import io.grpc.ManagedChannel;
//        import io.grpc.ManagedChannelBuilder;
//        import io.grpc.stub.StreamObserver;
//        import lombok.extern.slf4j.Slf4j;
//        import net.devh.boot.grpc.client.inject.GrpcClient;
//        import org.springframework.stereotype.Service;
//
//
//@Slf4j
//@Service
//public class InventoryInfoService {
//
//    //    @GrpcClient("grpc-inventory-service")
//    InventoryServiceGrpc.InventoryServiceBlockingStub synchronousClient;
//
//    //    @GrpcClient("grpc-inventory-service")
//    InventoryServiceGrpc.InventoryServiceStub asynchronousClient;
//
//
//    public void validateProduct(){
//
//
//        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
//
//
//
//        synchronousClient = InventoryServiceGrpc.newBlockingStub(managedChannel);
//        asynchronousClient = InventoryServiceGrpc.newStub(managedChannel);
//        asynchronousClient.addProduct(Product.newBuilder().setProductId("123").build(), new StreamObserver<Product>() {
//
//            @Override
//            public void onNext(Product product) {
//                System.out.println(product.getProductId()+" "+product.getProductName()+"_____");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("error: "+throwable);
//                System.out.println(throwable.getCause());
//                System.out.println(throwable.getMessage());
//                System.out.println(throwable.getLocalizedMessage());
//                System.out.println(throwable.getStackTrace());
//
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("complete");
//            }
//
//        });

//            @Override
//            public void onCompleted() {
//                System.out.println("complete");
//            }
//

//        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@123");
//        asynchronousClient.addProduct(Product.newBuilder().setProductId("123").build(), new StreamObserver<Product>() {
//
//            @Override
//            public void onNext(Product product) {
//                System.out.println(product.getProductId()+" "+product.getProductName()+"_____");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("error: "+throwable);
//                System.out.println(throwable.getCause());
//                System.out.println(throwable.getMessage());
//                System.out.println(throwable.getLocalizedMessage());
//                System.out.println(throwable.getStackTrace());
//
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("complete");
//            }
//
//        });
//    }
//
//}





























//package com.validation.service;
//
//import com.validation.productinfoservice.*;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.stub.StreamObserver;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Collections;
//import java.util.List;
//
//
////@GrpcService
////@RequiredArgsConstructor
////@Transactional
//@Slf4j
//public class InventoryInfoService {
//
//    ManagedChannel channelInventoryService = ManagedChannelBuilder.forAddress("localhost",8082).usePlaintext().build();
//
//
//    public void validateProduct(ValidationRequest validationRequest){
//
//        List<Product> validatedProducts = Collections.emptyList();
//
//        InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub =
//                InventoryServiceGrpc.newBlockingStub(channelInventoryService);
//
//        Product product = inventoryServiceBlockingStub.getProductByValidationObject(validationRequest);
//
//        System.out.println(product);
//
////        StreamObserver<ValidationRequest> validateObserver = inventoryServiceBlockingStub.getProductByValidationObject(new StreamObserver<>() {
////
////            @Override
////            public void onNext(Product product) {
////                if(product.getIsValid()){
////                    validatedProducts.add(product);
////                }
////            }
////
////            @Override
////            public void onError(Throwable throwable) {
////                log.error("Something went wrong while validating multiple products.",throwable);
////            }
////
////            @Override
////            public void onCompleted() {
////                log.error("Products successfully validated.");
////            }
////        });
////
////
////        //Send request
////        validateObserver.onNext(ValidationRequest.newBuilder().setBrand(validationRequest.getBrand()).
////                setRegistrationId(validationRequest.getRegistrationId()).build());
////
////        validateObserver.onCompleted();
//    }
//
//}
