package com.kwang.controller.jwt;

import com.kwang.dto.BasicResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.MalformedJwtException;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object internalServerError(Exception e){
        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		
        result.status = false;
        result.data = "인증 토큰에 문제가 있습니다.";
        result.object = e.getMessage();
        response = new ResponseEntity<>(result, HttpStatus.OK);
	
		return response;	
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object loginTimeOutError(Exception e){
        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		
        result.status = false;
        result.data = "로그인 시간이 만료되었습니다\n 다시 로그인해주세요";
        result.object = e.getMessage();
        response = new ResponseEntity<>(result, HttpStatus.OK);
	
		return response;	
    }
}