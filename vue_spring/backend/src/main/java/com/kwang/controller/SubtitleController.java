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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.kwang.dto.BasicResponse;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.jwt.service.JwtService;
import com.kwang.service.SubtitleService;

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
public class SubtitleController {
	
	@Autowired
	SubtitleService subtitleService;
	
	@Autowired
	JwtService jwtService;

	@GetMapping("/api/subtitle/selectAll")
	@ApiOperation(value = "서버가 가지고 있는 자막 모두 출력")
	public Object selectSubtitle(@RequestParam(required = true) final int input ) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		
		if(input != 3816){
			result.status = true;
            result.data = "허가되지 않은 사용자 입니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.OK);

			return response;
		}
		List<SubtitleFileInfo> data = subtitleService.findAll();

        
        if (data != null) {
            System.out.println(data.size() + "data 출력 성공");
            result.status = true;
            result.data = data.size() + "개의 데이터 출력 성공";
			result.object = data;
			
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "관리자 번호를 입력했지만 출력이 실패하였습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			System.out.println("관리자 번호를 입력했지만 출력이 실패하였습니다.");
        }
        return response;
	}

	@GetMapping("/api/subtitle/search")
	@ApiOperation(value = "해당 단어를 제목에 포함하는 subtitle 정보 출력")
	public Object selectSubtitle(@RequestParam(required = true) final String keyword ) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		
		List<SubtitleFileInfo> data = subtitleService.findFilesByKeyword(keyword);

        
        if (data != null) {
            System.out.println(data.size() + "data 출력 성공");
            result.status = true;
            result.data = data.size() + "개의 데이터 출력 성공";
			result.object = data;
			
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "출력이 실패하였습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			System.out.println("출력이 실패하였습니다.");
        }
        return response;
	}

	@GetMapping("/api/subtitle/mylist")
	@ApiOperation(value = "해당 id를 제목에 포함하는 subtitle 정보 출력")
	public Object selectMylist(HttpServletRequest req) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		
		int userid = (int) (long) jwtService.getUserInfo(req).get("userid");
		List<SubtitleFileInfo> data = subtitleService.findFilesByUserid(userid);
        
        if (data != null) {
            System.out.println(data.size() + "data 출력 성공");
            result.status = true;
            result.data = data.size() + "개의 데이터 출력 성공";
			result.object = data;
			
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "출력이 실패하였습니다. 토큰정보가 없거나 삭제된 유저일 가능성이 있습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			System.out.println("출력이 실패하였습니다.");
        }
        return response;
	}

	@GetMapping("/api/subtitle/showtrans")
	@ApiOperation(value = "자막 출력")
	public Object modifySubtitle(@RequestParam(required = true) final int subid ) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		
		List<Transcript> data = subtitleService.findSubtitleBySubid(subid);
        
        if (data != null) {
            System.out.println(data.size() + "data 출력 성공");
            result.status = true;
            result.data = data.size() + "개의 데이터 출력 성공";
			result.object = data;
			
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "번역결과를 불러오지 못했습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			System.out.println("출력이 실패하였습니다.");
        }
        return response;
	}

	@PostMapping("/api/subtitle/modify")
	@ApiOperation(value = "자막 수정")
	public Object modifySubtitle(@Valid @RequestBody List<Transcript> translist) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		int data = subtitleService.modifyTranscript(translist);

        if (data != 0) {
            System.out.println(data + "data 출력 성공");
            result.status = true;
            result.data = data + "개의 데이터 출력 성공";
			result.object = data;
			
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
			result.status = false;
			result.data = "자막 수정이 실패하였습니다. 혹은 변경사항이 없습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			System.out.println("자막 수정이 실패하였습니다. 혹은 변경사항이 없습니다.");
        }
        return response;
	}
}