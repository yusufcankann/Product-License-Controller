package com.validation.module;

import com.google.protobuf.Timestamp;
import com.validation.productinfoservice.Product;
import com.validation.productinfoservice.ProductionSite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    public static final int MS_TO_S = 1000;
    private String registrationId;
    private String productId;
    private String productName;
    private String brand;
    private LocalDate productionDate;
    private LocalDate expireDate;
    private Boolean isExpandable;
    private ProductionSiteEntity productionSite;
    private LocalDate creationTime;
    private boolean isValid;

    public Product toProto(){

        Timestamp productionDateTimestamp = localDateToTimestamp(productionDate);
        Timestamp expireDateTimestamp = localDateToTimestamp(expireDate);
        Timestamp creationTimeTimestamp = localDateToTimestamp(creationTime);

        ProductionSite productionSiteProto = ProductionSite.newBuilder().setCity(productionSite.getCity()).
                setCountry(productionSite.getCountry()).setPostcode(productionSite.getPostcode()).build();

        return Product.newBuilder().setProductId(productId).setRegistrationId(registrationId).
                setProductName(productName).setBrand(brand).setProductionDate(productionDateTimestamp).
                setExpireDate(expireDateTimestamp).setIsExpandable(isExpandable).setProductionSite(productionSiteProto).
                setCreationTime(creationTimeTimestamp).setIsValid(isValid).build();
    }

    public static ProductEntity fromProto(Product proto) {

        final LocalDate productionDate = protoTimestampToLocalDate(proto.getProductionDate());
        final LocalDate expireDate = protoTimestampToLocalDate(proto.getExpireDate());
        final LocalDate creationTime = protoTimestampToLocalDate(proto.getCreationTime());

        ProductEntity entry = new ProductEntity();
        entry.setRegistrationId(proto.getRegistrationId());
        entry.setProductId(proto.getProductId());
        entry.setProductName(proto.getProductName());
        entry.setBrand(proto.getBrand());
        entry.setProductionDate(productionDate);
        entry.setExpireDate(expireDate);
        entry.setIsExpandable(proto.getIsExpandable());
        entry.setProductionSite(ProductionSiteEntity.fromProto(proto.getProductionSite()));
        entry.setCreationTime(creationTime);
        entry.setValid(proto.getIsValid());

        return entry;
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

