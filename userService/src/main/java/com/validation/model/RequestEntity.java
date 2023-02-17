package com.validation.model;

import com.validation.service.ValidationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {

    private String registrationId;
    private String brand;

    public ValidationRequest toProto(){
        return ValidationRequest.newBuilder().setRegistrationId(registrationId).setBrand(brand).build();
    }

    public static RequestEntity fromProto(ValidationRequest validationRequest){
        if(Objects.isNull(validationRequest)){
            return null;
        }

        RequestEntity entity = new RequestEntity();
        entity.setRegistrationId(validationRequest.getRegistrationId());
        entity.setBrand(validationRequest.getBrand());
        return entity;
    }
}
