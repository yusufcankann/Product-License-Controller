package com.validation.model;

import com.validation.service.Company;
import lombok.*;

import javax.persistence.*;


@Entity
@Table//(name = "CompanyEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String sector;

    public Company toProto(){
        return Company.newBuilder().setName(name).setSector(sector).build();
    }

    public static CompanyEntity fromProto(Company company){
        CompanyEntity entity = new CompanyEntity();
        entity.setName(company.getName());
        entity.setSector(company.getSector());
        return entity;
    }
}
