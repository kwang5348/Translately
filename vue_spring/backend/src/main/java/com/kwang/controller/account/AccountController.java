package com.kwang.controller.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.kwang.dto.BasicResponse;
import com.kwang.dto.InputData;
import com.kwang.dto.UserData;
import com.kwang.service.UserService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@Controller
public class AccountController {
	
	@Autowired
	UserService service;
	
	// @GetMapping("/api/login")
	// public ResponseEntity input_user(@RequestParam(required = true) final String uid,
	// @RequestParam(required = true) final String password) {
	// 	ResponseEntity response = null;
	// 	final BasicResponse result = new BasicResponse();
	// 	result.status = false;
	// 	// boolean loginflag = false;
	// 	List<UserData> list = service.input_user_from_service(uid, password);
	// 	if (uid.equals(list.get(0).getUid()) & password.equals(list.get(0).getPassword())){
	// 		result.status = true;
	// 	}
		
	// 	response = new ResponseEntity<>(result, HttpStatus.OK);
	// 	return response;
	// } 
	
	@GetMapping("/api/login")
	public ResponseEntity input_user(@RequestParam(required = true) final String uid,
	@RequestParam(required = true) final String password) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		result.status = false;
		// boolean loginflag = false;
		List<UserData> list = service.input_user_from_service(uid, password);
		if (uid.equals(list.get(0).getUid()) & password.equals(list.get(0).getPassword())){
			result.status = true;
		}
		
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	} 

}






