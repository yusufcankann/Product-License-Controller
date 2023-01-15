package com.validation.service;

import com.validation.model.CompanyEntity;
import com.validation.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public Optional<CompanyEntity> saveCompany(CompanyEntity company) throws Exception {
        Optional<CompanyEntity> c = getCompanyByNameAndSector(company.getName(),company.getSector());
        if(c.isPresent()){
           log.error("Company already exist!");
           return Optional.empty();
        }

        return Optional.of(companyRepository.save(company));
    }

    @Override
    public Optional<CompanyEntity> getCompany(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<CompanyEntity> getCompaniesByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public List<CompanyEntity> getCompaniesBySector(String sector) {
        return companyRepository.findBySector(sector);
    }

    @Override
    public Optional<CompanyEntity> getCompanyByNameAndSector(String name, String sector) {
        return companyRepository.findByNameAndSector(name,sector);
    }

    @Override
    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<String> getAllCompanyNames() {

        List<CompanyEntity> companies = companyRepository.findAll();

        Set<String> names = new HashSet<>();

        for(CompanyEntity c:companies){
            names.add(c.getName());
        }

        return new ArrayList<>(names);
    }

    @Override
    public void removeCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void updateCompany(Long id, String name, String sector) {
        Optional<CompanyEntity> c = companyRepository.findById(id);

        if(!c.isPresent()){
            log.error("Product does not exist!");
            return;
        }

        CompanyEntity company = c.get();

        if(name!=null && name.length()>0 && !Objects.equals(company.getName(),name)){
            company.setName(name);
        }

        if(sector!=null && sector.length()>0 && !Objects.equals(company.getSector(),sector)){
            company.setSector(sector);
        }
    }
}
