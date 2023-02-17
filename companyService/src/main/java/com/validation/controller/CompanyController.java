package com.validation.controller;

import com.validation.model.CompanyEntity;
import com.validation.service.Company;
import com.validation.service.CompanyService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    @Autowired
    private final CompanyService companyService;

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getCompanies")
    public List<CompanyEntity> getCompanies(){
        List<Company> companies = companyService.getCompanies();
        List<CompanyEntity> entities=new ArrayList<>();
        companies.forEach((c)-> entities.add(CompanyEntity.fromProto(c)));
        return entities;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getCompanies", params = "sector")
    public List<CompanyEntity> getCompaniesBySector(String sector){
        List<Company> companies = companyService.getCompaniesBySector(sector);
        List<CompanyEntity> entities=new ArrayList<>();
        companies.forEach((c)-> entities.add(CompanyEntity.fromProto(c)));
        return entities;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getCompanies", params = "name")
    public List<CompanyEntity> getCompaniesByName(String name){
        List<Company> companies = companyService.getCompaniesByName(name);
        List<CompanyEntity> entities=new ArrayList<>();
        companies.forEach((c)-> entities.add(CompanyEntity.fromProto(c)));
        return entities;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @GetMapping(value = "getCompany", params = {"name","sector"})
    public Optional<CompanyEntity> getCompanyByNameAndSector(String name, String sector){
        Optional<Company> company = companyService.getCompanyByNameAndSector(name,sector);
        if(company.isPresent()) {
            return Optional.ofNullable(CompanyEntity.fromProto(company.get()));
        }
        return Optional.empty();

    }

    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
    @PostMapping(value = "add")
    public Optional<CompanyEntity> addCompany(@RequestBody CompanyEntity company){
        return Optional.ofNullable(CompanyEntity.fromProto(companyService.addCompany(company.toProto()).get()));
    }

}
