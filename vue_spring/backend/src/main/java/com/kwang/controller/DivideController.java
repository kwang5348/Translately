package com.kwang.controller;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import javax.validation.Valid;

import com.kwang.dto.BasicResponse;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.service.SubtitleService;
import com.kwang.service.VideoTranslateService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
// (origins = { "http://localhost:3000",
// "http://i3a511.p.ssafy.io" })
// http://i3a511.p.ssafy.io/api/account/join

@RestController
@Controller
public class DivideController {

	private static final String SERVER_LOCATION = "/home/ubuntu/resources";

	@Autowired
	VideoTranslateService videoService;
	
	@GetMapping("/api/wav/divide")
	@ApiOperation(value = "요청 영상 분할")
	public Object selectSubtitle(@RequestParam(required = true) final String fileName,
	@RequestParam(required = true) final String start,
	@RequestParam(required = true) final String target) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		int checkError = 0;
		

        // if (checkError == 0) {
        //     System.out.println("data 출력 성공");
        //     result.status = true;
        //     result.data = "개의 데이터 출력 성공";
		// 	result.object = data;
			
        //     response = new ResponseEntity<>(result, HttpStatus.OK);
        // } else {
		// 	result.status = false;
		// 	result.data = "관리자 번호를 입력했지만 출력이 실패하였습니다.";
		// 	result.object = null;
		// 	response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		// 	System.out.println("관리자 번호를 입력했지만 출력이 실패하였습니다.");
        // }
        return response;
	}

}