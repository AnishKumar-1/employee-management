package com.management.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RoleModel {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;


    public RoleModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleModel(){};

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
  
    

}
