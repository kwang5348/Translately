package com.kwang.jwt.service;

import java.util.Date;
import java.util.Map;

import com.kwang.dto.UserData;

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
                .claim("UserData", user).claim("second", "더 담고 싶은거 있어?");

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
        System.out.println("여기까지 ㄱㅊ");
        System.out.println(Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt));
        System.out.println("여기 괜찮");
        try {
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        } catch (Exception e) {
            System.out.println("여기서 터지나?");
            throw new RuntimeException();
        }

        return claims.getBody();
    }
}