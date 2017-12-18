package com.batata.dualDb.model.entity;

import com.batata.dualDb.model.BatataDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table//if class and table have the same name, do not need "name" property
public class Batata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column //if field and var have the same name, do not need "name" property
    @NotNull(message = "This batata should have a name.")
    private String name;
    @Column
    private String type;


    public Batata(){
    }

    public Batata(BatataDto dto) {
        if(dto != null) {
            this.name = dto.getName();
            this.type = dto.getType();
        }
    }

    public Batata(Integer id, BatataDto dto) {
        this.id = id;
        if(dto != null) {
            this.name = dto.getName();
            this.type = dto.getType();
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Batata{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public BatataDto dto(){
        BatataDto dto = new BatataDto(this.id);
        dto.setName(this.name);
        dto.setType(this.type);
        return dto;
    }

}
