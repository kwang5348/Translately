package com.kwang.controller.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.kwang.dao.UserDao;
import com.kwang.dto.BasicResponse;
import com.kwang.dto.InputData;
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

@CrossOrigin(origins = { "http://localhost:3000",
						"http://i3a511.p.ssafy.io/" })
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
            result.data = "success";
            result.object = user;
            System.out.println(user.getEmail() + " 로그인에 성공하였습니다.");
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "fail";
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
		boolean isExist = userService.findUserByEmail(request.getEmail());

        ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		if(isExist){
			result.status = false;
			result.data = "이미 가입되어 있는 이메일입니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		} else {
			UserData user = userService.join(request);
			result.status = false;
			result.data = "회원가입에 성공하였습니다.";
			result.object = user;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}

        return response;
	}

	// @RequestMapping(value = "/deleteuser/{id}", method = RequestMethod.GET)
	// public String delete(@PathVariable String id, HttpSession session) {
	// 	System.out.println("id:" + id);
	// 	int successCnt = service.deleteMember(id);
	// 	MemberInfo dto = (MemberInfo)session.getAttribute("userinfo");
	// 	System.out.println(dto.getUser_id());
	// 	if(!dto.getUser_id().equals("admin")) {
	// 		session.invalidate();
	// 	}
	// 	return "redirect:/";
	// }

	// @RequestMapping(value = "/modify", method = RequestMethod.POST)
	// public String modify(@ModelAttribute MemberInfo dto, HttpSession session) {//@ModelAttribute 생략 가능 : 파라미터 여러개를 한 번에 VO로 받기
	// 	int successCnt = service.modify(dto);
	// 	if(successCnt > 0) {
	// 		MemberInfo result = service.getMember(dto.getUser_id());
	// 		session.setAttribute("userinfo", result);
	// 	}
	// 	return "redirect:/";//jsp 호출
	// }


}






