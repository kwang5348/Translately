package com.kwang.controller;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.kwang.dto.BasicResponse;
import com.kwang.dto.BuildTranslateResult;
import com.kwang.dto.ParseResultSet;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.jwt.service.JwtService;
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
	// private static final String SERVER_LOCATION = "src/main/resources";
	private static final String VTT_DIR = "/vtt/";
	private static final String JPG_DIR = "/jpg/";
	private static final String WAV_DIR = "/wav/";
	private static final String TEMP_WAV_DIR = "/wav/temp/";
	private static final String MP4_DIR = "/mp4/";

	private static final String VTT_EX = ".vtt";
	private static final String JPG_EX = ".jpg";
	private static final String WAV_EX = ".wav";
	private static final String MP4_EX = ".mp4";
	@Autowired
	private VideoTranslateService videoService;

	@Autowired
	private JwtService JwtService;

	@PostMapping("/api/wav/analysis")
	@ApiOperation(value = "요청 영상 분석")
	public Object selectSubtitle(@Valid @RequestBody SubtitleFileInfo fileInfo, HttpServletRequest req) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		final String languageTag = "_" + fileInfo.getStart_sub_code() + "_" + fileInfo.getTarget_sub_code();
		int userid = (int) (long) JwtService.getUserInfo(req).get("userid");
		fileInfo.setUserid(userid);
		// int subid = videoService.saveFileInfo(fileInfo);
		int duration = 0;
		// if (subid == 0) {
		// System.out.println("analysis request 파일 저장 실패");
		// result.status = true;
		// result.data = "analysis request 파일 저장 실패";
		// result.object = fileInfo;
		// return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		// }

		System.out.println("translateStart");
		System.out.println("변환 전 파일 경로 " + SERVER_LOCATION + MP4_DIR + fileInfo.getSubtitle_file() + MP4_EX);
		try {
			videoService.convertToAudio(fileInfo.getSubtitle_file(), languageTag );
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("ffmpeg 변환이 실패하였습니다.");
			result.status = true;
			result.data = "ffmpeg 변환이 실패하였습니다.";
			result.object = fileInfo;
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		System.out.println("변환 후 파일 경로 " + SERVER_LOCATION + WAV_DIR + fileInfo.getSubtitle_file() + languageTag + WAV_EX);
		System.out.println("ffmpeg 작업 종료");

		try {
			duration = videoService.getDurationFromMp4(fileInfo.getSubtitle_file() + languageTag);
			fileInfo.setDuration(duration);
			System.out.println(duration);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ffprove 파일 정보를 읽어오는데 실패하였습니다.");
			result.status = true;
			result.data = "ffprove 파일 정보를 읽어오는데 실패하였습니다.";
			result.object = fileInfo;
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		int wavCount = duration / 50;
		result.status = true;
		result.data = (wavCount+1) + "개의 파일분할이 가능합니다.";
		result.object = fileInfo;

		// for (int i = 0; i <= wavCount; i++) {
		// 	try {
		// 		int parseTime = 50;
		// 		if(wavCount == i){
		// 			parseTime = duration - 50 * wavCount;
		// 		}
		// 		videoService.convertToSubAudio(fileInfo.getSubtitle_file(), i, parseTime);
		// 	} catch (Exception e) {
		// 		e.printStackTrace();
		// 		System.out.println(i + " 번째 파일분할에 실패하였습니다.");
		// 		result.status = true;
		// 		result.data = i + " 번째 파일분할에 실패하였습니다.";
		// 		result.object = fileInfo;
		// 		return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		// 	}
		// }

        return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/api/wav/subTranslate")
	public Object translately(@Valid @RequestBody BuildTranslateResult resultSet, HttpServletRequest req) {

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		final String languageTag = "_" + resultSet.getFileInfo().getStart_sub_code() + "_" + resultSet.getFileInfo().getTarget_sub_code();
		final String rawFilePath = SERVER_LOCATION + MP4_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag + MP4_EX;
		final String subFilePath = SERVER_LOCATION + TEMP_WAV_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag + resultSet.getBuildId() + WAV_EX;
		final String vttFilePath = SERVER_LOCATION + VTT_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag + VTT_EX;
		final String wavFilePath = SERVER_LOCATION + WAV_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag + WAV_EX;
		final String jpgFilePath = SERVER_LOCATION + JPG_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag + JPG_EX;
		List<Transcript> tranList = null;
		System.out.println(resultSet);
		int parseTime = 50;
		if(resultSet.getFinalBuild() == resultSet.getBuildId()){
			parseTime = resultSet.getFileInfo().getDuration() % parseTime;
		}

		if(resultSet.getFinalBuild() < resultSet.getBuildId()){
			result.status = false;
			result.data = "분할파일 index 가 잘못되었습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		}

		System.out.println("변환 전 파일 경로 " + wavFilePath);
		try {
			System.out.println("translateStart");
			videoService.convertToSubAudio(resultSet.getFileInfo().getSubtitle_file(), resultSet.getBuildId(), parseTime, languageTag);

			System.out.println("ffmpeg 작업 종료");
			
			tranList = videoService.translateLocalFile(subFilePath, resultSet.getFileInfo().getStart_sub_code(), resultSet.getFileInfo().getTarget_sub_code());
			System.out.println("translateEnd");
		} catch (Exception e) {
			result.status = false;
			result.data = "translate failed";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		}
		try {
			System.out.println("papago translate start");
			tranList = videoService.papagoTranslate(tranList, resultSet.getFileInfo().getStart_sub_code(), resultSet.getFileInfo().getTarget_sub_code());
			System.out.println("papago translate end");
		} catch (Exception e) {
			result.status = false;
			result.data = "papago Fail";
			result.object = null;

			e.printStackTrace();

			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		ParseResultSet parsedResult = new ParseResultSet(resultSet.getVttResult(), resultSet.getTranscript());
		boolean vttSuccess = false;
		try {
			vttSuccess = true;
			System.out.println("parse Start");
			parsedResult = videoService.parseTranslateResult(parsedResult, tranList, subFilePath, resultSet.getBuildId());
			System.out.println("parse End");
		} catch (Exception e) {
			result.status = false;
			result.data = "parse fail";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);

			return response;
		}
		
		
		System.out.println("vttFilePath : " + vttFilePath);
		try {
			System.out.println("ConverToSrt Start");
			videoService.converToSrtFile_(parsedResult.getParsedResult(), vttFilePath);
			System.out.println("ConverToSrt End");
			vttSuccess = true;
		} catch (IOException e) {
			result.status = false;
			result.data = "vtt convert Fail";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);

			return response;
		}
		resultSet.setTranscript(parsedResult.getTranlist());
		resultSet.setVttResult(parsedResult.getParsedResult());
		if (vttSuccess) {
			result.status = true;
			result.data = "success";
			result.object = resultSet;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return response;
	}
}