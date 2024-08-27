package com.example.restapi.service;

import com.example.restapi.model.Advertise;
import com.example.restapi.repository.AdvertiseRepository;
import org.apache.coyote.BadRequestException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class AdvertiseService {

    AdvertiseRepository advertiseRepository;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertiseService that = (AdvertiseService) o;
        return Objects.equals(advertiseRepository, that.advertiseRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(advertiseRepository);
    }

    public AdvertiseService(AdvertiseRepository advertiseRepository) {
        this.advertiseRepository = advertiseRepository;
    }

    public ArrayList<Advertise> getAdvertises(int id){
        ArrayList<Advertise> advertises = (ArrayList<Advertise>) advertiseRepository.findAll();
        ArrayList<Advertise> wantedAdvertises = new ArrayList<>();
        advertises.stream()
                .filter(advertise -> advertise
                .getUserId() == id)
                .forEach(wantedAdvertises::add);
        return wantedAdvertises;
    }

    public String addAdvertise(Advertise newAdvertise){
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(newAdvertise.getLatitude(),newAdvertise.getLatitude(), newAdvertise.getLongitude());


        Geometry point = geometryFactory.createPoint(coordinate);
        newAdvertise.setLocation(point);
        advertiseRepository.save(newAdvertise);
        return "Advertise added";
    }

    public String deleteAdvertise(long advertiseId){
        advertiseRepository.deleteById(advertiseId);
        return "Advertise deleted";
    }

    public String updateAdvertise(long advertiseId, Advertise newAdvertise){

        Advertise advertise = advertiseRepository.findById(advertiseId).get();

        advertise.setTitle(newAdvertise.getTitle());
        advertise.setDescription(newAdvertise.getDescription());

        advertiseRepository.save(advertise);

        return "Advertise updated";
    }

    public Advertise getAdvertise(int advertiseId) throws BadRequestException {
        return  advertiseRepository
                .findById((long) advertiseId)
                .orElseThrow(() -> new BadRequestException("Advertise not found!"));
    }


    public ArrayList<Integer> getNearbyAdvertise(int advertiseId) throws BadRequestException {

        GeometryFactory geometryFactory = new GeometryFactory();

        Geometry geom = geometryFactory.createPoint(new Coordinate(getAdvertise(advertiseId).getLatitude(),getAdvertise(advertiseId).getLongitude()));
        ArrayList<Integer> nearbyAdvertiseId = advertiseRepository.findNearby(geom);

        return nearbyAdvertiseId;
    }

    public long getDistance(int advertiseId1, int advertiseId2) throws BadRequestException {
        Geometry location1 = getAdvertise(advertiseId1).getLocation();
        Geometry location2 = getAdvertise(advertiseId2).getLocation();
        return (long) location1.distance(location2);
    }
}
