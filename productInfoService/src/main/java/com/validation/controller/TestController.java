//THIS CONTROLLER CREATED ONLY FOR TEST PURPOSES.

package com.validation.controller;

import com.google.protobuf.Timestamp;
import com.validation.service.InventoryInfoService;
import com.validation.service.Product;
import com.validation.service.ProductionSite;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@RestController
@RequestMapping("/api/reservation")
@AllArgsConstructor
public class TestController {

    final InventoryInfoService inventoryInfoService;
    public static final int MS_TO_S = 1000;

    @GetMapping(value = "/user/reservations")
    public int test() {
//        List<Product> products = new ArrayList<>();
//        products.add(produceProduct("1","1","1","nike"));
//        products.add(produceProduct("2","2","2","adidas"));
//        products.add(produceProduct("3","3","3","ulker"));
//        inventoryInfoService.validateProduct();
        return 0;
    }

    public Product produceProduct(String registrationId,String productId, String productName,String brand){

        LocalDate productionDate = LocalDate.now();
        Timestamp productionDateTimestamp = localDateToTimestamp(productionDate);

        ProductionSite productionSiteProto = ProductionSite.newBuilder().setCity("a").
                setCountry("b").setPostcode("c").build();

        return Product.newBuilder().setProductId("productId").setRegistrationId(registrationId).
                setProductName(productName).setBrand(brand).setProductionDate(productionDateTimestamp).
                setExpireDate(productionDateTimestamp).setIsExpandable(true).setProductionSite(productionSiteProto).
                setCreationTime(productionDateTimestamp).setIsValid(true).build();
    }

    private static LocalDate protoTimestampToLocalDate(Timestamp protoTimestamp) {
        return LocalDateTime.ofEpochSecond(protoTimestamp.getSeconds(),
                protoTimestamp.getNanos(), ZoneOffset.UTC).toLocalDate();
    }

    private Timestamp localDateToTimestamp(LocalDate date){
        long seconds = date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()/MS_TO_S;
        return Timestamp.newBuilder().setSeconds(seconds).build();
    }

}
