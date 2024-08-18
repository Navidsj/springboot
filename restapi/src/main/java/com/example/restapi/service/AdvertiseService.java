package com.example.restapi.service;

import com.example.restapi.model.Advertise;
import com.example.restapi.repository.AdvertiseRepository;
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
        newAdvertise.setId(advertiseRepository.findAll().size()+1);
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

    public Advertise getAdvertise(long advertiseId){
        return  advertiseRepository.findById(advertiseId).get();
    }


}
