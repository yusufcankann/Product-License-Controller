package com.example.productvalidation.repository;

import com.example.productvalidation.module.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    public Optional<Product> findProductByProductId(String productId);
    public Optional<Product> findProductByProductName(String productName);
    public Optional<Product> findProductByProductNameAndBrand(String name, String brand);
    public List<Product> findProductsByBrand(String brand);
    public List<Product> findByProductionDateBetween(Date from, Date to);
    public List<Product> findByExpireDateBetween(Date from, Date to);
// alternative
//    @Query("{'productionDate':{$gt:?0,$lt:?1}}")
//    public List<Product> (Date startDate, Date endDate);

}
