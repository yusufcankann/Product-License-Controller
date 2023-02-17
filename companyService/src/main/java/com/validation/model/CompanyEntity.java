package com.validation.model;

import com.validation.service.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    private Long id;
    private String name;
    private String sector;

    public Company toProto(){
        return Company.newBuilder().setName(name).setSector(sector).build();
    }

    public static CompanyEntity fromProto(Company company){
        if(Objects.isNull(company)){
            return null;
        }

        CompanyEntity entity = new CompanyEntity();
        entity.setName(company.getName());
        entity.setSector(company.getSector());
        return entity;
    }
}
