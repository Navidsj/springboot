package com.example.restapi.controller;

import com.example.restapi.model.Advertise;
import com.example.restapi.service.AdvertiseService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Data
@RequestMapping("/user")
public class AdvertiseController{

    AdvertiseService advertiseService;

    @PostMapping("/advertise")
    public String addAdvertise(@RequestBody Advertise advertise){
        return advertiseService.addAdvertise( advertise);
    }

    @GetMapping("/advertise/{advertiseId}")
    public Advertise getAdvertise(@PathVariable int advertiseId){
        return advertiseService.getAdvertise(advertiseId);
    }

    @DeleteMapping("/advertise/{advertiseId}")
    public String deleteAdvertise(@PathVariable int advertiseId)
    {
        return advertiseService.deleteAdvertise(advertiseId);
    }

    @PutMapping("/advertise/{advertiseId}")
    public String updateAdvertise(@RequestBody Advertise advertise,@PathVariable int advertiseId){
        System.out.println("Inja");
        return advertiseService.updateAdvertise(advertiseId, advertise);
    }

    @GetMapping("/{userId}/showadvertise")
    public ArrayList<Advertise> getShowAdvertise(@PathVariable int userId){
        return advertiseService.getAdvertises(userId);
    }

}
