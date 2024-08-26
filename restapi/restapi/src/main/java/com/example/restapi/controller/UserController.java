package com.example.restapi.controller;

import com.example.restapi.model.User;
import com.example.restapi.service.RabbitMQProducer;
import com.example.restapi.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RabbitMQProducer rabbitMQProducer;


    @PostMapping("/hello")
    public ResponseEntity<String> home(@RequestBody String body) {
        System.out.println(body);
        rabbitMQProducer.sendMessage(body);

        return ResponseEntity.ok(body);
    }

    @PostMapping("/user")
    public String newUser(@RequestBody User user) {
        System.out.println(user);
        return userService.addUser(user);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

}
