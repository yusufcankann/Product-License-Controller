package com.validation.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceGrpcImpl extends CompanyServiceGrpc.CompanyServiceImplBase{

    private final CompanyService companyService;

    @Override
    public void getCompanies(Company request, StreamObserver<Company> responseObserver) {
        List<Company> companyList = companyService.getCompanies();
        companyList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompaniesByName(Company request, StreamObserver<Company> responseObserver) {
        List<Company> companyList = companyService.getCompaniesByName(request.getName());
        companyList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompaniesBySector(Company request, StreamObserver<Company> responseObserver) {
        List<Company> companyList = companyService.getCompaniesBySector(request.getSector());
        companyList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompanyByNameAndSector(Company request, StreamObserver<Company> responseObserver) {
        Optional<Company> company = companyService.getCompanyByNameAndSector(request.getName(),request.getSector());

        if(!company.isPresent()){
            log.error("Company does not exist");
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        }

        responseObserver.onNext(company.get());
        responseObserver.onCompleted();
    }

    @Override
    public void addCompany(Company request, StreamObserver<Company> responseObserver) {
        Optional<Company> company;
        try {
            company = companyService.addCompany(request);
        } catch (Exception e) {
            log.error("Company could not saved! exception: ",e);
            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        responseObserver.onNext(company.get());
        responseObserver.onCompleted();
    }

}
