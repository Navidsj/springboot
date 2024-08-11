package com.example.restapi.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "advertise")
@Data
public class Advertise{

    @Column
    int userId;
    @Column
    String title;
    @Column
    String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


}
