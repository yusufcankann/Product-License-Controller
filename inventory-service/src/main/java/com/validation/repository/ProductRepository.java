package com.validation.repository;

import com.validation.module.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity,String> {

    List<ProductEntity> findProductsByBrand(String brand);
    Optional<ProductEntity> findProductByBrandAndProductName(String brand, String productName);
//    List<ProductEntity> findByBrandAndProductionDateBetween(String brand, LocalDate from, LocalDate to);
//    List<ProductEntity> findByBrandAndExpireDateBetween(String brand,LocalDate from, LocalDate to);
    Optional<ProductEntity> findByRegistrationIdAndBrandAndProductName(String registrationId,String brand,String name);
    Optional<ProductEntity> findByRegistrationIdAndBrand(String registrationId,String brand);
    void deleteByRegistrationIdAndBrand(String registrationId,String brand);

// alternative
//    @Query("{'productionDate':{$gt:?0,$lt:?1}}")
//    public List<Product> (Date startDate, Date endDate);

}