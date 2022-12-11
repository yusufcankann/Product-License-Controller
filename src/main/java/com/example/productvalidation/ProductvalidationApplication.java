package com.example.productvalidation;

import com.example.productvalidation.entity.Product;
import com.example.productvalidation.entity.ProductionSite;
import com.example.productvalidation.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class ProductvalidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductvalidationApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductRepository repository){
		return args -> {

			String expireDate= "12-12-2024";
			String productDate= "12-11-2022";
			//Instantiating the SimpleDateFormat class
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			//Parsing the given String to Date object
			Date expDate = formatter.parse(expireDate);
			Date prodDate = formatter.parse(productDate);

			ProductionSite productionSite = new ProductionSite("Turkey","Istanbul","34000");
			Product product = new Product("TestProduct", "Nike", prodDate,expDate, false, productionSite, new Date());

			repository.insert(product);
		};
	}
}
