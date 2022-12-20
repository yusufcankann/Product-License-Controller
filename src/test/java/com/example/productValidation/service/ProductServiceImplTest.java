package com.example.productValidation.service;


import com.example.productValidation.module.Product;
import com.example.productValidation.module.ProductionSite;
import com.example.productValidation.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void shouldProductSavedSuccesfully(){
        Product product = generateProduct();
        //Mockito.when(productRepository.findProductByProductName("Nike")).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(product)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Product savedProduct = productService.addProduct(product);
        Assert.notNull(savedProduct);
        Mockito.verify(productRepository).save(any(Product.class));
    }

    private Product generateProduct(){
        String expireDate= "12-12-2024";
        String productDate= "19-11-2060";
        //Instantiating the SimpleDateFormat class
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Parsing the given String to Date object
        Date expDate = null;
        Date prodDate = null;

        try {
            expDate = formatter.parse(expireDate);
            prodDate = formatter.parse(productDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        ProductionSite productionSite = new ProductionSite("Germany","Istanbul","34000");
        Product product = new Product("TestProduct", "Nike", prodDate,expDate, false, productionSite, new Date());
        return product;
    }


}
