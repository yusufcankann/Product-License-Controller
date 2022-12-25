package com.validation.productValidation.service;


import com.validation.productValidation.dto.ProductRequest;
import com.validation.productValidation.dto.ProductResponse;
import com.validation.productValidation.module.Product;
import com.validation.productValidation.module.ProductionSite;
import com.validation.productValidation.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.runtime.ObjectMethods;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
public class ProductServiceImplTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    @Autowired
    MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {

        Product product = generateProduct();
        ProductRequest req = generateProductRequest(product);
        String mappedReq = objectMapper.writeValueAsString(req);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mappedReq)).andExpect(status().isCreated());

        Assertions.assertTrue(productRepository.findAll().size()==1);
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
        Product product = new Product("1","TestProduct", "Nike", prodDate,expDate,
                false, productionSite, new Date());
        return product;
    }

    private ProductRequest generateProductRequest(Product product){
        ProductRequest productReq = new ProductRequest(product.getProductName(),product.getBrand(),
                product.getProductionDate(),product.getExpireDate(),product.getIsExpandible(),
                product.getProductionSite(),product.getCreationTime());
        return productReq;
    }


    /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    /** ALTERNATIVE EXAMPLE OF TESTING **/

    /*
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void shouldProductSavedSuccesfully(){
        Product product = generateProduct();
        ProductRequest request = generateProductRequest(product);

        //Mockito.when(prouductRepository.findProductByProductName("Nike")).thenReturn(Optional.empty());

        Mockito.when(productRepository.save(any(Product.class))).thenAnswer(
                invocationOnMock -> invocationOnMock.getArgument(0));

        Product savedProduct = productService.addProduct(request);
        Assert.notNull(savedProduct);
        Mockito.verify(productRepository).save(any(Product.class));
    }

     */
    /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/


}
