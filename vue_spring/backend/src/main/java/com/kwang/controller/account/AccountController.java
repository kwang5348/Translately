package com.kwang.controller.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.kwang.dao.UserDao;
import com.kwang.dto.BasicResponse;
import com.kwang.dto.UserData;
import com.kwang.jwt.service.JwtService;
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
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;

	@PostMapping("/api/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@Valid @RequestBody UserData request, HttpServletResponse res) {
		UserData user = userService.findUserByEmailAndPassword(request);

        ResponseEntity response = null;
        
		final BasicResponse result = new BasicResponse();
        if (user != null) {
			// System.out.println("로그인 성공");
			String token = jwtService.create(user);
			user.setToken(token);
			res.setHeader("jwt-auth-token", token);
            result.status = true;
            result.data = "login success";
			result.object = user;
			// System.out.println(user.toString());
            // System.out.println(user.getEmail() + " 로그인에 성공하였습니다.");
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "login fail";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			// System.out.println("로그인에 실패하였습니다.");
        }

        // System.out.println("로그인 프로세스 완료");
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
	public Object signOut(@RequestParam(required = true) final String email, HttpServletRequest req){
		// System.out.println("email:" + email);
		String userEmail = (String) jwtService.getUserInfo(req).get("email");
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if(!userEmail.equals(email)){
			result.status = false;
			result.data = "다른 회원의 아이디는 삭제할 수 없습니다.";
			result.object = email;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		int successCnt = userService.deleteUserByEmail(email);
		
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
		// System.out.println("email:" + email);
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

	@GetMapping("/api/account/info")
	@ApiOperation(value = "토큰으로 회원정보 조회")
	public Object getInfo(HttpServletRequest req) {

        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		// System.out.println(jwtService.get(req.getHeader("jwt-auth-token")).get("UserData"));
		try{
			result.object = jwtService.get(req.getHeader("jwt-auth-token")).get("UserData");
			result.status = true;
			result.data = "토큰 정보 조회에 성공하였습니다.";
			response = new ResponseEntity<>(result, HttpStatus.OK);

		} catch (RuntimeException e) {
			result.status = false;
			result.data = "토큰정보 조회에 실패 하였습니다.";
			result.object = e.getMessage();
			response = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

        return response;
	}

	@GetMapping("/api/account/remainTime")
	@ApiOperation(value = "현재 잔여시간 출력")
	public Object selectMypageSubtitle(HttpServletRequest req) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;

		int userid = (int) (long) jwtService.getUserInfo(req).get("userid");
		int remainTime = -1;
		
		try {
			remainTime = userService.getRemainTime(userid);
		} catch (Exception e) {
			result.status = false;
			result.data = "유저정보를 통해 잔여시간을 찾는데 실패하였습니다.";
			result.object = null;

			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}

		
		result.status = true;
		result.data = "잔여시간 출력에 성공하였습니다.";
		result.object = remainTime;
		response = new ResponseEntity<>(result, HttpStatus.OK);
			
		return response;
	}
	
}






