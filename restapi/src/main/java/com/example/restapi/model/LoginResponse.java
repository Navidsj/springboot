package com.example.restapi.model;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private long expiresIn;

}
