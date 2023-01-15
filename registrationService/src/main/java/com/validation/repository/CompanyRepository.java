package com.validation.repository;

import com.validation.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    Optional<CompanyEntity> findById(Long id);
    List<CompanyEntity> findByName(String name);
    List<CompanyEntity> findBySector(String name);
    Optional<CompanyEntity> findByNameAndSector(String name, String sector);
}
