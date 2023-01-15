package com.validation.service;

import com.validation.model.CompanyEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Optional<CompanyEntity> saveCompany(CompanyEntity company) throws Exception;
    Optional<CompanyEntity> getCompany(Long id);
    List<CompanyEntity> getCompaniesByName(String name);
    List<CompanyEntity> getCompaniesBySector(String sector);
    Optional<CompanyEntity> getCompanyByNameAndSector(String name, String sector);
    List<CompanyEntity> getAllCompanies();
    List<String> getAllCompanyNames();
    void removeCompany(Long id);
    void updateCompany(Long id,String name,String sector);

}
