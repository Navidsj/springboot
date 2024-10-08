package com.example.restapi.service;


import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String addUser(User newUser) {

        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for(User user : users) {
            if(user.equals(newUser)) {
               return "User already exists";
            }
        }

        userRepository.save(newUser);
        return "User added";
    }

    public String deleteUser(long id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

}
