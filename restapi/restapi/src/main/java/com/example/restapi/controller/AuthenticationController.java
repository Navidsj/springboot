package com.example.restapi.controller;

import com.example.restapi.dtos.LoginUserDto;
import com.example.restapi.dtos.RegisterUserDto;
import com.example.restapi.model.LoginResponse;
import com.example.restapi.model.User;
import com.example.restapi.service.AuthenticationService;
import com.example.restapi.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final ObjectMapper jacksonObjectMapper;


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, ObjectMapper jacksonObjectMapper) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody String body) throws InterruptedException, JsonProcessingException {




        RegisterUserDto registerUserDto = jacksonObjectMapper.readValue(body,RegisterUserDto.class);


        RedissonClient redissonClient = Redisson.create();

        RLock lock = redissonClient.getLock("lock");

        boolean locked = lock.tryLock(10,5, TimeUnit.SECONDS);

        if(locked) {

            User registerdUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registerdUser.toString());
        }else{
            return ResponseEntity.ok("Try another time please");

        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody String body) throws JsonProcessingException {


        LoginUserDto loginUserDto = jacksonObjectMapper.readValue(body,LoginUserDto.class);
        User authonticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authonticatedUser);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(loginResponse);

    }



}