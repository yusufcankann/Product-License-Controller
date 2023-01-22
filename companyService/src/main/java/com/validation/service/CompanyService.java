package com.validation.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CompanyService {

    @GrpcClient("grpc-product-info-service")
    RegistrationServiceCompanyGrpc.RegistrationServiceCompanyBlockingStub synchronousClient;

    @GrpcClient("grpc-product-info-service")
    RegistrationServiceCompanyGrpc.RegistrationServiceCompanyStub asynchronousClient;

    public List<Company> getCompanies(){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Company> response = new ArrayList<>();
        Company request = Company.newBuilder().build();
        asynchronousClient.getCompanies(request,new StreamObserver<Company>() {
            @Override
            public void onNext(Company company) {
                response.add(company);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error on while getting company ",throwable);
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

    public List<Company> getCompaniesBySector(String sector){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Company> response = new ArrayList<>();
        Company request = Company.newBuilder().setSector(sector).build();
        asynchronousClient.getCompaniesBySector(request,new StreamObserver<Company>() {
            @Override
            public void onNext(Company company) {
                response.add(company);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error on while getting company ",throwable);
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

    public List<Company> getCompaniesByName(String name){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Company> response = new ArrayList<>();
        Company request = Company.newBuilder().setName(name).build();
        asynchronousClient.getCompaniesByName(request,new StreamObserver<Company>() {
            @Override
            public void onNext(Company company) {
                response.add(company);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error on while getting company ",throwable);
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

    public Optional<Company> getCompanyByNameAndSector(String name, String sector){
        Company request = Company.newBuilder().setName(name).setSector(sector).build();
        Company response = synchronousClient.getCompanyByNameAndSector(request);
        log.info("Company obtained by name and sector name {} sector {}",
                response.getName(),response.getSector());
        return Optional.of(response);
    }

    public Optional<Company> addCompany(Company company){
        Company c = synchronousClient.addCompany(company);

        if(Objects.isNull(c)){
            log.error("Company could not registered name: {} sector {}",company.getName(),company.getSector());
            return Optional.empty();
        }

        if(c.getName().equals(company.getName()) && c.getSector().equals(company.getSector())) {
            log.info("Product succesfull added to inventoryService...");
        }
        return Optional.of(c);
    }
}
