package com.example.productvalidation;

import com.example.productvalidation.module.Product;
import com.example.productvalidation.module.ProductionSite;
import com.example.productvalidation.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class ProductValidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductValidationApplication.class, args);
	}

//	Written for test purposes.
//	 TODO remove
//	@Bean
//	CommandLineRunner runner(ProductRepository repository, MongoTemplate mongoTemplate){
//		return args -> {
//			String expireDate= "12-12-2024";
//			String productDate= "19-11-2060";
//			//Instantiating the SimpleDateFormat class
//			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//			//Parsing the given String to Date object
//			Date expDate = formatter.parse(expireDate);
//			Date prodDate = formatter.parse(productDate);
//
//			ProductionSite productionSite = new ProductionSite("Germany","Istanbul","34000");
//			Product product = new Product("TestProduct", "Adadsda", prodDate,expDate, false, productionSite, new Date());
//
////			Query query = new Query();
////			query.addCriteria(Criteria.where("brand").is("Nike"));
////
////
////			List<Product> products = mongoTemplate.find(query,Product.class);
////			if(products.size()>1){
////				throw new IllegalStateException("found many same brands");
////			}
////
////			if(products.isEmpty()){
//			repository.insert(product);
//			product = new Product("TestProduct1", "Nike", prodDate,expDate, true, productionSite, new Date());
//			repository.insert(product);
////			}
////			else{
////				System.out.println(product.getBrand() + " already exist");
////
////			}
//
////			String testProductDate= "19-11-2061";
////			SimpleDateFormat testFormatter = new SimpleDateFormat("dd-MM-yyyy");
////			Date testDate = formatter.parse(testProductDate);
////
////			List<Product> list = repository.findByProductionDateBetween(new Date(),testDate);
////
////			for(Product p : list){
////				System.out.println(p.getBrand()+" "+p.getProductionDate());
////			}
//
//
////			List<Product> list = repository.findProductsByProductionSite(productionSite);
////				System.out.println(list.size()+"@@ ");
////			for(Product p : list){
////				System.out.println(p.getBrand()+" "+p.getProductionDate()+ " " + productionSite.getCity());
////			}
//		};
//	}
}
