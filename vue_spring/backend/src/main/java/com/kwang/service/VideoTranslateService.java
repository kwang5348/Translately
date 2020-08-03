package com.kwang.service;

import java.io.IOException;
import java.util.List;

import com.kwang.dto.Transcript;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.web.multipart.MultipartFile;

public interface VideoTranslateService {
	public String convertToAudio(String filepath) throws Exception;
	public List<Transcript> translateLocalFile(final String filepath) throws Exception;
	public String convertToSrt_(double time);
	public String convertToVTT_(double time);
	public boolean converToSrtFile_(String contents, String filename) throws IOException;
	public List<Transcript> parseTranslateResult(List<Transcript> tranList) throws IOException;
	public List<Transcript> papagoTranslate(List<Transcript> tranList) throws Exception;
}
