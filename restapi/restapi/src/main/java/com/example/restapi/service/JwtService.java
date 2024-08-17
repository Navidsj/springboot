package com.example.restapi.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretKey = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    @Getter
    private long jwtExpiration = 3600000;


    // T inja chiye va chejo kar mikone
    public <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        final  Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }


    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims,userDetails,jwtExpiration);
    }


    private String buildToken(Map<String,Object> exteraClaims,
                              UserDetails userDetails,
                              long expiration) {

        return Jwts.
                builder().
                setClaims(exteraClaims).
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis()+expiration) ).
                signWith(getSignInKey(), SignatureAlgorithm.HS256).
                compact();

    }

    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }


    private  Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }



    // ******************************* injaro hanooz nmifahmam
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
