package com.validation.service;

import com.validation.model.CompanyEntity;
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
public class RegistrationServiceCompanyGrpcImpl extends RegistrationServiceCompanyGrpc.RegistrationServiceCompanyImplBase{

    private final CompanyService companyService;

    @Override
    public void getCompanies(Company request, StreamObserver<Company> responseObserver) {
        List<CompanyEntity> companyEntityList = companyService.getAllCompanies();
        List<Company> companyList = convertToCompany(companyEntityList);

        companyList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompaniesByName(Company request, StreamObserver<Company> responseObserver) {
        List<CompanyEntity> companyEntityList = companyService.getCompaniesByName(request.getName());
        List<Company> companyList = convertToCompany(companyEntityList);

        companyList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompaniesBySector(Company request, StreamObserver<Company> responseObserver) {
        List<CompanyEntity> companyEntityList = companyService.getCompaniesBySector(request.getSector());
        List<Company> companyList = convertToCompany(companyEntityList);

        companyList.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCompanyByNameAndSector(Company request, StreamObserver<Company> responseObserver) {
        Optional<CompanyEntity> companyEntity = companyService.getCompanyByNameAndSector(request.getName(),request.getSector());

        if(!companyEntity.isPresent()){
            log.error("Company does not exist");
            responseObserver.onNext(null);
            responseObserver.onCompleted();
        }

        responseObserver.onNext(companyEntity.get().toProto());
        responseObserver.onCompleted();
    }

    private static List<Company> convertToCompany(List<CompanyEntity> productList) {
        List<Company> companyList = new ArrayList<>();
        for(CompanyEntity c : productList){
            companyList.add(c.toProto());
        }
        return companyList;
    }

    @Override
    public void addCompany(Company request, StreamObserver<Company> responseObserver) {
        Optional<CompanyEntity> companyEntity;
        try {
            companyEntity = companyService.saveCompany(CompanyEntity.fromProto(request));
        } catch (Exception e) {
            log.error("Company could not saved!");
            responseObserver.onNext(null);
            responseObserver.onCompleted();
            return;
        }

        responseObserver.onNext(companyEntity.get().toProto());
        responseObserver.onCompleted();
    }

}
