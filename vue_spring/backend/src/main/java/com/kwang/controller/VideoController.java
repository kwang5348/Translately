package com.kwang.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.grpc.Server;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.kwang.dao.translateDao;
import com.kwang.dto.BasicResponse;
import com.kwang.dto.ParseResultSet;
import com.kwang.dto.RecInfo;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.papago.APIExamTranslate;
import com.kwang.service.VideoTranslateService;
import com.kwang.stt.InfiniteStreamRecognize;
import com.kwang.stt.Recognize;

@CrossOrigin
// (origins = { "http://localhost:3000",
// "http://i3a511.p.ssafy.io" })
@RestController
public class VideoController {

	private static final String EXTENSION = ".vtt";
    private static final String SERVER_LOCATION = "/home/ubuntu/resources";

	static boolean semaFlag = false;

	@Autowired
	VideoTranslateService service;
	translateDao transDao;


	@GetMapping(value = "/api/translate")
	public Object translately(@RequestParam(required = true) final String start,
			@RequestParam(required = true) final String target, @RequestParam(required = true) final String fileName) {

		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		if (semaFlag) {
			result.status = false;
			result.data = "다른분의 번역이 진행중입니다. 잠시만 기다려주세요";
			result.object = null;
			System.out.println("다른분의 번역이 진행중입니다. 잠시만 기다려주세요");
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		semaFlag = true;
		String filePath = "/home/ubuntu/resources/wav/";
		String localFileName = fileName.replace(" ", "");

		List<Transcript> tranList = null;
		try {
			System.out.println("translateStart");
			System.out.println("변환 전 파일 경로 " + filePath + localFileName);
			localFileName = service.convertToAudio(localFileName, start, target);
			System.out.println("변환 후 파일 경로 " + localFileName);

			System.out.println("ffmpeg 작업 종료");
			
			tranList = service.translateLocalFile(localFileName, start, target);
			System.out.println("translateEnd");
		} catch (Exception e) {
			result.status = false;
			result.data = "translate failed";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			semaFlag = false;
			return response;
		}
		try {
			System.out.println("papago translate start");
			tranList = service.papagoTranslate(tranList, start, target);
			System.out.println("papago translate end");
		} catch (Exception e) {
			result.status = false;
			result.data = "papago Fail";
			result.object = null;

			e.printStackTrace();
			semaFlag = false;

			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		ParseResultSet parsedResult = null;
		boolean srtSuccess = false;
		try {
			srtSuccess = true;
			System.out.println("parse Start");
			parsedResult = service.parseTranslateResult(tranList, localFileName);
			System.out.println("parse End");
		} catch (Exception e) {
			result.status = false;
			result.data = "parse fail";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			semaFlag = false;

			return response;
		}

		String srtFilePath = localFileName.replace(".wav", ".vtt");
		srtFilePath = srtFilePath.replace("/wav", "/vtt");
		System.out.println("vttFilePath : " + srtFilePath);
		try {
			System.out.println("ConverToSrt Start");
			service.converToSrtFile_(parsedResult.getParsedResult(), srtFilePath);
			System.out.println("ConverToSrt End");
			srtSuccess = true;
		} catch (IOException e) {
			result.status = false;
			result.data = "srt convert Fail";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);

			return response;
		}

		if (srtSuccess) {
			result.status = true;
			result.data = "success";
			result.object = parsedResult.getTranlist();
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		semaFlag = false;

		return response;
	}

	@GetMapping(value = "/api/youtube/upload")
	public Object youTubeUploadToLocalFileSystem(@RequestParam(required = true) final String fileLink) {
		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;
		String filePath = "/home/ubuntu/resources/wav/";

		try {
			System.out.println("youtube 다운로드가 시작됩니다.");
			
			String epicLink = fileLink.replace("https://", "");
			epicLink = epicLink.replace("www.youtube.com/", "");
			epicLink = epicLink.replace("watch?v=", "");
			System.out.println(epicLink);
			service.downLoadYoutube(fileLink, epicLink);

			System.out.println("youtube 다운로드가 종료되었습니다.");
		} catch (Exception e) {
			result.status = false;
			result.data = "Youtube DownLoad Fail";
			result.object = null;

			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);

			return response;
		}


		result.status = true;
		result.data = "유튜브 파일 업로드에 성공했습니다.";
		result.object = fileLink;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@PostMapping(value = "/api/wav/upload")
	public Object uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) throws Exception{

		final BasicResponse result = new BasicResponse();
		ResponseEntity response = null;

		String fileName = file.getOriginalFilename().replace(" ", "");
		if(fileName.indexOf(".mp4") == -1){
			result.status = false;
			result.data = "지원하지 않는 파일 형식입니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			return response;
		}
		String fileBasePath = "/home/ubuntu/resources/wav/";
		Path path = Paths.get(fileBasePath + fileName);
		boolean uploadFlag = false;
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			uploadFlag = true;
			System.out.println("file upload success");
		} catch (IOException e) {
			uploadFlag = false;
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		.path("/files/download/")
		.path(fileName)
		.toUriString();
		if(!uploadFlag){
			semaFlag = false;
			result.status = false;
			result.data = "업로드가 성공하지 못했습니다.";
			result.object = null;
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			return response;
		}

		result.status = true;
		result.data = "업로드에 성공했습니다.";
		result.object = fileBasePath + fileName;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

    @GetMapping(value = "/api/vtt/download")
    public ResponseEntity<Resource> download(@RequestParam(required = true) final String fileLink) throws IOException {
        File file = new File(SERVER_LOCATION + "/vtt/" + fileLink + EXTENSION);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=subtitle.vtt");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}






