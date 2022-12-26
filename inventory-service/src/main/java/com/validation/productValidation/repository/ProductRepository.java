package com.validation.productValidation.repository;

import com.validation.productValidation.module.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    List<Product> findProductsByBrand(String brand);
    List<Product> findProductsByBrandAndProductName(String brand, String productName);
    List<Product> findByBrandAndProductionDateBetween(String brand,Date from, Date to);
    List<Product> findByBrandAndExpireDateBetween(String brand,Date from, Date to);
    Optional<Product> findByRegistrationIdAndBrandAndProductName(String registrationId,String brand,String name);
    Optional<Product> findByRegistrationIdAndBrand(String registrationId,String brand);
    void deleteByRegistrationIdAndBrand(String registrationId,String brand);

// alternative
//    @Query("{'productionDate':{$gt:?0,$lt:?1}}")
//    public List<Product> (Date startDate, Date endDate);

}
