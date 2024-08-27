package com.example.restapi.repository;

import com.example.restapi.model.Advertise;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {

    @Query(value = "SELECT id FROM advertise WHERE ST_DWithin(ST_Transform(ST_SetSRID(location, 4326), 3857),ST_Transform(ST_SetSRID(:geom, 4326), 3857), 1000)",nativeQuery = true)
    ArrayList<Integer> findNearby(Geometry geom);

}
