package com.example.restapi.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
public class User {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, age);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private int age;

}
