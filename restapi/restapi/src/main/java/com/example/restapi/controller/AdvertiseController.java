package com.example.restapi.controller;

import com.example.restapi.model.Advertise;
import com.example.restapi.service.AdvertiseService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Data
public class AdvertiseController{

    @Autowired
    AdvertiseService advertiseService;

    @PostMapping("/user/advertise")
    public String addAdvertise(@RequestBody Advertise advertise){
        return advertiseService.addAdvertise( advertise);
    }

    @GetMapping("/user/advertise/{advertiseId}")
    public Advertise getAdvertise(@PathVariable int advertiseId){
        return advertiseService.getAdvertise(advertiseId);
    }

    @DeleteMapping("/user/advertise/{advertiseId}")
    public String deleteAdvertise(@PathVariable int advertiseId)
    {
        return advertiseService.deleteAdvertise(advertiseId);
    }

    @PutMapping("/user/advertise/{advertiseId}")
    public String updateAdvertise(@RequestBody Advertise advertise,@PathVariable int advertiseId){
        System.out.println("Inja");
        return advertiseService.updateAdvertise(advertiseId, advertise);
    }

    @GetMapping("/user/{userId}/showadvertise")
    public ArrayList<Advertise> getShowAdvertise(@PathVariable int userId){
        return advertiseService.getAdvertises(userId);
    }

}
