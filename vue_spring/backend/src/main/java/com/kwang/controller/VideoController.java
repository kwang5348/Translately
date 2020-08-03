package com.kwang.controller;

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
import org.springframework.http.HttpStatus;
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

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.kwang.dto.BasicResponse;
import com.kwang.dto.RecInfo;
import com.kwang.dto.Transcript;
import com.kwang.papago.APIExamTranslate;
import com.kwang.service.VideoTranslateService;
import com.kwang.stt.InfiniteStreamRecognize;
import com.kwang.stt.Recognize;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class VideoController {

	@Autowired
	VideoTranslateService service;

	static boolean semaFlag = false;

	// @GetMapping(value = "/api/check")
	// public String checkPath() throws FileNotFoundException, IOException {
		

	// 	String result = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
	// 	GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/home/ubuntu/key/ssafyapi-03a93ca825f5.json"))
	// 	.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		
	// 	Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

	// 	System.out.println("Buckets:");
	// 	Page<Bucket> buckets = storage.list();
	// 	for (Bucket bucket : buckets.iterateAll()) {
	// 	  System.out.println(bucket.toString());
	// 	}
		
	// }

	@GetMapping(value = "/api/translate")
	public Object translately(@RequestParam(required = true) final String start,
			@RequestParam(required = true) final String target, @RequestParam(required = true) final String fileName) {
		if(semaFlag){
			return new ResponseEntity<>(semaFlag, HttpStatus.NOT_FOUND);
		}
		semaFlag = true;
		String filePath = "/home/ubuntu/resources/wav/";
		String localFileName = fileName.replace(" ", "");
		localFileName = localFileName.replace(".mp4", ".wav");
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		List<Transcript> tranList = null;
		try {
			System.out.println("translateStart");
			System.out.println(filePath + localFileName);
			tranList = service.translateLocalFile(filePath + localFileName);
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
			tranList = service.papagoTranslate(tranList);
			System.out.println("papago translate end");
		} catch (Exception e) {
			result.status = false;
			result.data = "papago Fail";
			result.object = null;

			e.printStackTrace();
			semaFlag = false;

			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		List<Transcript> parsedResult = null;
		boolean srtSuccess = false;
		try{
			srtSuccess = true;
			System.out.println("parse Start");
			parsedResult = service.parseTranslateResult(tranList);
			System.out.println("parse End");
		}catch (Exception e) {
			result.status = false;
			result.data = "parse fail";
			result.object = null;
			
			e.printStackTrace();
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			semaFlag = false;

			return response;
		}

		String srtSavePath = "./resources/vtt/";
		String srtFilePath = srtSavePath + localFileName.replace(".wav", ".vtt");
		// try {
		// 	System.out.println("ConverToSrt Start");
		// 	//service.converToSrtFile_(parsedResult, srtFilePath);
		// 	System.out.println("ConverToSrt End");
		// 	srtSuccess = true;
		// } catch (IOException e) {
		// 	result.status = false;
		// 	result.data = "srt convert Fail";
		// 	result.object = null;
			
		// 	e.printStackTrace();
		// 	response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);

		// 	return response;
		// }
		
		if (srtSuccess) {
			result.status = true;
			result.data = "success";
			result.object = parsedResult;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		semaFlag = false;

		return response;
	}


	@PostMapping(value = "/api/wav/upload")
	public Object uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) throws Exception{
		String fileName = file.getOriginalFilename().replace(" ", "");
		String fileBasePath = "./resources/wav/";
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

			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		if(uploadFlag)
			service.convertToAudio(fileBasePath + fileName);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}






