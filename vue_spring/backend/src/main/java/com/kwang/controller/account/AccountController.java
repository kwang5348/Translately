package com.kwang.controller.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.kwang.dao.UserDao;
import com.kwang.dto.BasicResponse;
import com.kwang.dto.UserData;
import com.kwang.service.UserService;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

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

@CrossOrigin
//(origins = { "http://localhost:3000",
//						"http://i3a511.p.ssafy.io" })
//http://i3a511.p.ssafy.io/api/account/join

@RestController
@Controller
public class AccountController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/api/login")
	@ApiOperation(value = "로그인")
	public ResponseEntity input_user(@RequestParam(required = true) final String uid,
	@RequestParam(required = true) final String password) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		result.status = false;
		System.out.println("start login.....");
		// boolean loginflag = false;
		if (uid.equals("test") & password.equals("1234")){
			result.status = true;
		}
		
		response = new ResponseEntity<>(result, HttpStatus.OK);
		System.out.println("end login.....");
		return response;
	} 

	@PostMapping("/api/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@Valid @RequestBody UserData request) {
		UserData user = userService.findUserByEmailAndPassword(request);

        ResponseEntity response = null;
        
		final BasicResponse result = new BasicResponse();
        if (user != null) {
            System.out.println("로그인 성공");
            result.status = true;
            result.data = "login success";
			result.object = user;
			System.out.println(user.toString());
            System.out.println(user.getEmail() + " 로그인에 성공하였습니다.");
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "login fail";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			System.out.println("로그인에 실패하였습니다.");
        }

        System.out.println("로그인 프로세스 완료");
        return response;
	}


	@PostMapping("/api/account/join")
	@ApiOperation(value = "회원가입")
	public Object signup(@Valid @RequestBody UserData request) {
		UserData user = userService.findUserByEmail(request.getEmail());

        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if(user != null){
			result.status = false;
			result.data = "이미 가입되어 있는 이메일입니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		} else {
			request.setToken("temp Token@@FDASIJFEWIAFAS");
			int successCnt = userService.join(request);
			if(successCnt != 0){
				result.status = true;
				result.data = "회원가입에 성공하였습니다.";
				result.object = successCnt;
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.status = false;
				result.data = "회원가입에 실패하였습니다.";
				result.object = successCnt;
				response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		}

        return response;
	}

	@GetMapping("/api/account/delete")
	@ApiOperation(value = "회원탈퇴")
	public Object signOut(@RequestParam(required = true) final String email){
		System.out.println("email:" + email);
		int successCnt = userService.deleteUserByEmail(email);

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if(email.equals("admin@translately.com")){
			result.status = false;
			result.data = "admin 계정은 삭제 불가능합니다.";
			result.object = email;
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}

		if(successCnt != 0){
			result.status = true;
			result.data = "회원탈퇴에 성공하였습니다.";
			result.object = email;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.status = false;
			result.data = "회원탈퇴에 실패하였습니다.";
			result.object = email;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;	
	}
	
	@PostMapping("/api/account/modify")
	@ApiOperation(value = "회원정보 수정")
	public Object modify(@Valid @RequestBody UserData request) {

        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		
		int successCnt = userService.modifyUser(request);
		if(successCnt != 0){
			result.status = true;
			result.data = "회원정보 수정에 성공하였습니다.";
			result.object = successCnt;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.status = false;
			result.data = "회원정보 수정에 실패하였습니다.";
			result.object = successCnt;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		

        return response;
	}


	@GetMapping("/api/account/findByEmail")
	@ApiOperation(value = "회원정보 조회")
	public Object findByEmail(@RequestParam(required = true) final String email){
		System.out.println("email:" + email);
		UserData user = userService.findUserByEmail(email);

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		if(user != null){
			result.status = true;
			result.data = "회원정보 조회에 성공하였습니다.";
			result.object = user;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.status = false;
			result.data = "회원정보 조회에 실패 하였습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;	
	}

	
}






