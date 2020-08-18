package com.kwang.jwt.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.kwang.dto.UserData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.internal.net.http.common.Log;

@Component

public class JwtService {
    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    public String create(final UserData user){
        //Log.trace("time: {}", expireMin);
        System.out.println("check expireMin : " + expireMin);
        final JwtBuilder builder = Jwts.builder();

        builder.setHeaderParam("typ", "JWT");

        builder.setSubject("로그인토큰")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .claim("UserData", user);

        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        final String jwt = builder.compact();
        System.out.println("토큰 발행 : " + jwt);
        return jwt;
    }

    public void checkValid(final String jwt){
        System.out.println("토큰점검 : " + jwt);
        try {
            Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> get(final String jwt){
        Jws<Claims> claims = null;
        System.out.println(Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt));
        try {
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return claims.getBody();
    }

    public JSONObject getUserInfo(HttpServletRequest req) {
        Object user = get(req.getHeader("jwt-auth-token")).get("UserData");
        Gson gson = new Gson();
        String mapToJsonString = gson.toJson(user, LinkedHashMap.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(mapToJsonString);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            jsonObj = null;
        }

        return jsonObj;
    }
}