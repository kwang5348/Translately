package com.kwang.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kwang.dto.ParseResultSet;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.web.multipart.MultipartFile;

public interface VideoTranslateService {
	public boolean getCapture(String fileName) throws Exception;
	public int convertToAudio(String fileName, String languageTag) throws Exception;
	public int getDurationFromMp4(String fileName) throws Exception;
	public int convertToSubAudio(String fileName, int startTime, int duration, String languageTag) throws Exception;
	public Map<String, Object>  checkYoutubeInfo(String fileLink) throws Exception;
	public int downLoadYoutube(String fileLink, String epicLink) throws Exception;
	public boolean getYoutubeName(String youtubeLink, int subid) throws Exception;
	public List<Transcript> translateLocalFile(final String filepath, String start, String target) throws Exception;
	public String convertToSrt_(double time);
	public String convertToVTT_(double time);
	public boolean converToSrtFile_(String contents, String filename) throws IOException;
	public ParseResultSet parseTranslateResult(ParseResultSet result, List<Transcript> tranList, String fileName, int buildId) throws IOException;
	public List<Transcript> papagoTranslate(List<Transcript> tranList, String startLanguage, String targetLanguage) throws Exception;
	public int saveFileInfo(SubtitleFileInfo fileInfo);
	public int saveTranscript(List<Transcript> translist, int subid);
	public int reduceRemainTime(int userid, int parseTime);
	public String buildVTTString(List<Transcript> translist);

}
