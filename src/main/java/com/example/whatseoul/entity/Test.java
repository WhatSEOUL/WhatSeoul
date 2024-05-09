package com.example.whatseoul.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class Test {

    @Id
    public Long id;

    public String title;
}
