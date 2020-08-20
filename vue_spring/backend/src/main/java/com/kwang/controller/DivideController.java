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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.kwang.dao.translateDao;
import com.kwang.dto.BasicResponse;
import com.kwang.dto.BuildTranslateResult;
import com.kwang.dto.ParseResultSet;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.jwt.service.JwtService;
import com.kwang.service.SubtitleService;
import com.kwang.service.UserService;
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
	private static final String TEMP_VTT_DIR = "/vtt/temp/";
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

	@Autowired
	private UserService userService;

	@Autowired
	private SubtitleService subtitleService;

	static boolean semapore;

	@GetMapping("api/wav/youtubeAble")
	@ApiOperation(value = "유튜브 유효성 체크")
	public Object youtubeCheck(@RequestParam(required = true) final String fileLink) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			map = videoService.checkYoutubeInfo(fileLink);
			if((int)map.get("exit") != 0){
				result.status = false;
				result.data = "유효하지 않은 유튜브 링크입니다.";
				result.object = map;
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				if(Integer.parseInt((String)map.get("fileLength")) > 900){
					result.status = false;
					result.data = "베타버전에서 15분 이상의 파일은 번역을 지원하지 않습니다.";
					result.object = map;
					return new ResponseEntity<>(result, HttpStatus.OK);
				}
				result.status = true;
				result.data = "유효한 유튜브 링크입니다.";
				result.object = map;
			}
		} catch (Exception e) {
			result.status = false;
			result.data = "유효하지 않은 유튜브 링크입니다.";
			result.object = map;
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);

	}


	@PostMapping("/api/wav/analysis")
	@ApiOperation(value = "요청 영상 분석")
	public Object selectSubtitle(@Valid @RequestBody SubtitleFileInfo fileInfo, HttpServletRequest req) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		final String languageTag = "_" + fileInfo.getStart_sub_code() + "_" + fileInfo.getTarget_sub_code();
		int userid = (int) (long) JwtService.getUserInfo(req).get("userid");
		int userRemainTime = userService.getRemainTime(userid);
		fileInfo.setUserid(userid);
		fileInfo.setSubtitle_file(fileInfo.getSubtitle_file().replace(" ", ""));
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
			videoService.convertToAudio(fileInfo.getSubtitle_file(), languageTag);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("ffmpeg 변환이 실패하였습니다.");
			result.status = false;
			result.data = "ffmpeg 변환이 실패하였습니다.";
			result.object = fileInfo;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		System.out.println(
				"변환 후 파일 경로 " + SERVER_LOCATION + WAV_DIR + fileInfo.getSubtitle_file() + languageTag + WAV_EX);
		System.out.println("ffmpeg 작업 종료");

		try {
			duration = videoService.getDurationFromMp4(fileInfo.getSubtitle_file() + languageTag);
			fileInfo.setDuration(duration);
			System.out.println(duration);
			if (userRemainTime < duration) {
				System.out.println("잔여시간이 부족합니다.");
				result.status = false;
				result.data = "잔여시간이 부족합니다.";
				result.object = fileInfo;
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ffprove 파일 정보를 읽어오는데 실패하였습니다.");
			result.status = true;
			result.data = "ffprove 파일 정보를 읽어오는데 실패하였습니다.";
			result.object = fileInfo;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		int wavCount = duration / 30;

		result.status = true;
		result.data = (wavCount + 1) + "개의 파일분할이 가능합니다.";
		result.object = fileInfo;

		// for (int i = 0; i <= wavCount; i++) {
		// try {
		// int parseTime = 50;
		// if(wavCount == i){
		// parseTime = duration - 50 * wavCount;
		// }
		// videoService.convertToSubAudio(fileInfo.getSubtitle_file(), i, parseTime);
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.out.println(i + " 번째 파일분할에 실패하였습니다.");
		// result.status = true;
		// result.data = i + " 번째 파일분할에 실패하였습니다.";
		// result.object = fileInfo;
		// return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		// }
		// }

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/api/wav/subTranslate")
	public Object translately(@Valid @RequestBody BuildTranslateResult resultSet, HttpServletRequest req) {
		int userid = (int) (long) JwtService.getUserInfo(req).get("userid");
		int userRemainTime = userService.getRemainTime(userid);

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		final String languageTag = "_" + resultSet.getFileInfo().getStart_sub_code() + "_"
				+ resultSet.getFileInfo().getTarget_sub_code();
		final String rawFilePath = SERVER_LOCATION + MP4_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag
				+ MP4_EX;
		final String subFilePath = SERVER_LOCATION + TEMP_WAV_DIR + resultSet.getFileInfo().getSubtitle_file()
				+ languageTag + resultSet.getBuildId() + WAV_EX;
		final String vttFilePath = SERVER_LOCATION + VTT_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag
				+ VTT_EX;
		final String tempVttFilePath = SERVER_LOCATION + TEMP_VTT_DIR + resultSet.getFileInfo().getSubtitle_file()
				+ languageTag + VTT_EX;
		final String wavFilePath = SERVER_LOCATION + WAV_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag
				+ WAV_EX;
		final String jpgFilePath = SERVER_LOCATION + JPG_DIR + resultSet.getFileInfo().getSubtitle_file() + languageTag
				+ JPG_EX;
		List<Transcript> tranList = null;
		System.out.println(resultSet);
		int parseTime = 30;
		if (resultSet.getFinalBuild() == resultSet.getBuildId()) {
			parseTime = resultSet.getFileInfo().getDuration() % parseTime;
		}

		if (resultSet.getFinalBuild() < resultSet.getBuildId()) {
			result.status = false;
			result.data = "분할파일 index 가 잘못되었습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}

		System.out.println(userRemainTime + " 에서 " + parseTime + "만큼의 시간이 차감됩니다.");
		System.out.println("변경후 userRemainTime : " + userService.getRemainTime(userid));
		if (userRemainTime < parseTime) {
			result.status = false;
			result.data = "잔여시간이 부족합니다.";
			result.object = null;
			System.out.println("잔여시간이 부족합니다.");
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}
		videoService.reduceRemainTime(userid, parseTime);

		System.out.println("변환 전 파일 경로 " + wavFilePath);
		try {
			System.out.println("translateStart");
			videoService.convertToSubAudio(resultSet.getFileInfo().getSubtitle_file(), resultSet.getBuildId(),
					parseTime, languageTag);

			System.out.println("ffmpeg 작업 종료");

			tranList = videoService.translateLocalFile(subFilePath, resultSet.getFileInfo().getStart_sub_code(),
					resultSet.getFileInfo().getTarget_sub_code());
			System.out.println("translateEnd");
		} catch (Exception e) {
			result.status = false;
			result.data = "번역이 실패하였습니다.";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}
		try {
			System.out.println("papago translate start");
			tranList = videoService.papagoTranslate(tranList, resultSet.getFileInfo().getStart_sub_code(),
					resultSet.getFileInfo().getTarget_sub_code());
			System.out.println("papago translate end");
		} catch (Exception e) {
			result.status = false;
			result.data = "파파고 api 호출에 실패하였습니다.";
			result.object = null;

			e.printStackTrace();

			response = new ResponseEntity<>(result, HttpStatus.OK);
		}

		ParseResultSet parsedResult = new ParseResultSet(resultSet.getVttResult(), resultSet.getTranscript());
		boolean vttSuccess = false;
		try {
			vttSuccess = true;
			System.out.println("parse Start");
			parsedResult = videoService.parseTranslateResult(parsedResult, tranList, subFilePath,
					resultSet.getBuildId());
			System.out.println("parse End");
		} catch (Exception e) {
			result.status = false;
			result.data = "번역문 파싱에 실패하였습니다.";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.OK);

			return response;
		}

		System.out.println("vttFilePath : " + tempVttFilePath);
		try {
			System.out.println("ConverToSrt Start");
			videoService.converToSrtFile_(parsedResult.getParsedResult(), tempVttFilePath);
			if (resultSet.getBuildId() == resultSet.getFinalBuild()) {
				
				resultSet.getFileInfo().setThumbnail(resultSet.getFileInfo().getSubtitle_file() + ".jpg");
				int subid = videoService.saveFileInfo(resultSet.getFileInfo());
				int queCount = videoService.saveTranscript(parsedResult.getTranlist(), subid);
				if (resultSet.getFileInfo().getYoutube_url() == null) {
					try {
						videoService.getCapture(resultSet.getFileInfo().getSubtitle_file());
					} catch (Exception e) {
						System.out.println("video 캡쳐에 실패하였습니다.");
						e.printStackTrace();
					}
				} else {
					// try {
					// 	videoService.getYoutubeName(resultSet.getFileInfo().getYoutube_url(),
					// 	resultSet.getFileInfo().getSubid());
					// } catch (Exception e) {
					// 	System.out.println("유튜브 이름 불러오기에 실패하였습니다.");
					// 	e.printStackTrace();
					// }
				}
				System.out.println(queCount + " 개의 번역큐가 입력되었습니다.");
				videoService.converToSrtFile_(parsedResult.getParsedResult(), vttFilePath);
				System.out.println("최종파일 번역 완료 path : " + vttFilePath);
			}
			System.out.println("ConverToSrt End");
			vttSuccess = true;
		} catch (IOException e) {
			result.status = false;
			result.data = "Vtt 파일변환이 실패하였습니다.";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.OK);

			return response;
		}
		resultSet.setTranscript(parsedResult.getTranlist());
		resultSet.setVttResult(parsedResult.getParsedResult());
		resultSet.setBuildId(resultSet.getBuildId()+1);
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