package com.example.restapi.controller;


import com.example.restapi.dtos.RegisterUserDto;
import com.example.restapi.model.User;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class AutomaticRegisteration{

    String baseUri = "http://localhost:8080";


    @GetMapping("/guest")
    public String automatic() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String guestUser = "{\n    \"email\":\"test@tset.com\",\n    \"password\":\"12345\",\n    \"fullname\":\"test\"\n\n}";

        HttpRequest signupRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUri + "/auth/signup"))
                .POST(HttpRequest.BodyPublishers.ofString(guestUser))
                .build();

        HttpResponse<String> signupResponse = client.send(signupRequest,HttpResponse.BodyHandlers.ofString());

        HttpRequest loginRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(baseUri + "/auth/login"))
                .POST(HttpRequest.BodyPublishers.ofString(guestUser))
                .build();

        HttpResponse<String> loginResponse = client.send(loginRequest,HttpResponse.BodyHandlers.ofString());

        System.out.println(loginResponse.statusCode());

        return loginResponse.body();
    }



}
