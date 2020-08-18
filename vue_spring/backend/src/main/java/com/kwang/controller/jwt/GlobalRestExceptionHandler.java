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
    @ExceptionHandler(value = {RuntimeException.class, MalformedJwtException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object internalServerError(Exception e){
        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		
        result.status = false;
        result.data = "msg";
        result.object = e.getMessage();
        response = new ResponseEntity<>(result, HttpStatus.OK);
	
		return response;	
    }
}