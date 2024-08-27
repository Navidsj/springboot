package com.example.restapi.model;


import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.awt.*;

@Entity
@Table(name = "advertise")
@Data
public class Advertise{

    @Id
    @SequenceGenerator(name = "AdvertiseIdSeqGenerator", allocationSize = 1, sequenceName = "AdvertiseIdSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdvertiseIdSeqGenerator")
    private int id;


    @Column
    int userId;
    @Column
    String title;
    @Column
    String description;
    @Column
    Geometry location;
    @Transient
    long latitude;
    @Transient
    long longitude;


}
