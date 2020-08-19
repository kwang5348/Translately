package com.kwang.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.startup.HomesUserDatabase;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.WordInfo;
import com.google.protobuf.ByteString;
import com.kwang.bucket.UploadObject;
import com.kwang.dao.UserDao;
import com.kwang.dao.translateDao;
import com.kwang.dto.ParseResultSet;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.papago.APIExamTranslate;
import com.kwang.stt.Recognize;

import io.grpc.internal.ClientStream;

@Service
public class VideoTranslateServiceImpl implements VideoTranslateService {

	@Autowired
	private translateDao transDao;

	@Autowired
	private UserDao userDao;
	private static final String SERVER_LOCATION = "/home/ubuntu/resources";
	//private static final String SERVER_LOCATION = "src/main/resources";
	private static final String VTT_DIR = "/vtt/";
	private static final String JPG_DIR = "/jpg/";
	private static final String WAV_DIR = "/wav/";
	private static final String TEMP_WAV_DIR = "/wav/temp/";
	private static final String MP4_DIR = "/mp4/";

	private static final String VTT_EX = ".vtt";
	private static final String JPG_EX = ".jpg";
	private static final String WAV_EX = ".wav";
	private static final String MP4_EX = ".mp4";

	static final int parseTimeLength = 30;

	@Override
	public boolean convertToSubAudio(String fileName, int startPart, int parseTime, String languageTag) throws Exception {
		
		final Runtime run = Runtime.getRuntime();
		
		long time = System.currentTimeMillis();

		final String command = "ffmpeg -y -i " + SERVER_LOCATION + WAV_DIR + fileName + languageTag + WAV_EX + 
								" -ss " + startPart*50 + " -t " + parseTime +  " -ar 16000 -ac 1 " + SERVER_LOCATION + TEMP_WAV_DIR + fileName + languageTag + startPart + WAV_EX;
		System.out.println("command : " + command);
		Process proc = null;
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			proc= run.exec(command);
			InputStream is = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}

			InputStream standardError = proc.getErrorStream();
			InputStreamReader ow = new InputStreamReader(standardError);
			BufferedReader errorReader = new BufferedReader(ow);
			StringBuffer stderr = new StringBuffer();
			String lineErr = null;
			while((lineErr = errorReader.readLine()) != null){
				stderr.append(lineErr).append("\n");
			}

			System.out.println(stderr.toString());

			if(!proc.waitFor(3, TimeUnit.SECONDS)){
				proc.destroy();
			}

		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			System.err.println("Failed to execute: " + e.getMessage());
		} finally {
			if(proc != null)
				proc.destroy();
			System.out.println("경과시간 : " + (System.currentTimeMillis() - time) + "ms");
		}

		return false;
	}

	@Override
	public String convertToAudio(String fileName, String languageTag) throws Exception {
		final Runtime run = Runtime.getRuntime();
		
		long time = System.currentTimeMillis();

		final String command = "ffmpeg -y -i " + SERVER_LOCATION + MP4_DIR + fileName + MP4_EX + " -t 160 -ar 16000 -ac 1 " + SERVER_LOCATION + WAV_DIR + fileName + languageTag + WAV_EX;
		System.out.println("command : " + command);
		Process proc = null;
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			proc= run.exec(command);
			InputStream is = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}

			InputStream standardError = proc.getErrorStream();
			InputStreamReader ow = new InputStreamReader(standardError);
			BufferedReader errorReader = new BufferedReader(ow);
			StringBuffer stderr = new StringBuffer();
			String lineErr = null;
			while((lineErr = errorReader.readLine()) != null){
				stderr.append(lineErr).append("\n");
			}

			System.out.println(stderr.toString());

			if(!proc.waitFor(3, TimeUnit.SECONDS)){
				proc.destroy();
			}

		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			System.err.println("Failed to execute: " + e.getMessage());
		} finally {
			if(proc != null)
				proc.destroy();
			System.out.println("경과시간 : " + (System.currentTimeMillis() - time) + "ms");
		}

		return "success to convert";

	}

	@Override
	public int getDurationFromMp4(String fileName) throws Exception {
		final Runtime run = Runtime.getRuntime();

		final String wavFilePath = SERVER_LOCATION + WAV_DIR + fileName + WAV_EX;
		
		int result = 0;
		long time = System.currentTimeMillis();

		//final String command = "ffprobe -i C:\\Users\\multicampus\\Desktop\\Translately\\s03p13a511\\vue_spring\\backend\\src\\main\\resources\\mp4\\qNRzHXQkagc.mp4 -show_format | findstr duration";
		
		//linux 명령어 grep 이 다르다.
		final String command = "ffprobe -i " + wavFilePath + " -show_format | grep duration ";
		String[] cmd = {
			"/bin/sh",
			"-c",
			command
			};
		System.out.println("command : " + command);
		Process proc = null;
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			proc= run.exec(cmd);
			InputStream is = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
				if(line.charAt(0) == 'd'){
					if(line.indexOf("duration=") == 0){
						
						float temp = Float.parseFloat(line.replace("duration=", ""));
						result = (int) temp;
					}
				}
			}

			InputStream standardError = proc.getErrorStream();
			InputStreamReader ow = new InputStreamReader(standardError);
			BufferedReader errorReader = new BufferedReader(ow);
			StringBuffer stderr = new StringBuffer();
			String lineErr = null;
			while((lineErr = errorReader.readLine()) != null){
				stderr.append(lineErr).append("\n");
			}

			System.out.println(stderr.toString());

			if(!proc.waitFor(3, TimeUnit.SECONDS)){
				proc.destroy();
			}

		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			System.err.println("Failed to execute: " + e.getMessage());
		} finally {
			if(proc != null)
				proc.destroy();
			System.out.println("경과시간 : " + (System.currentTimeMillis() - time) + "ms");
		}

		return result;

	}

	@Override
	public String downLoadYoutube(String fileLink, String epicLink) throws Exception {
		final Runtime run = Runtime.getRuntime();
		String filePath = "/home/ubuntu/resources/mp4/";

		long time = System.currentTimeMillis();

		final String command = "python3.7 download.py " + fileLink + " " + epicLink;
		System.out.println("command : " + command);
		Process proc = null;
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			proc= run.exec(command);
			InputStream is = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}

			InputStream standardError = proc.getErrorStream();
			InputStreamReader ow = new InputStreamReader(standardError);
			BufferedReader errorReader = new BufferedReader(ow);
			StringBuffer stderr = new StringBuffer();
			String lineErr = null;
			while((lineErr = errorReader.readLine()) != null){
				stderr.append(lineErr).append("\n");
			}

			System.out.println(stderr.toString());

			if(!proc.waitFor(60, TimeUnit.SECONDS)){
				proc.destroy();
			}

		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			System.err.println("Failed to execute: " + e.getMessage());
		} finally {
			if(proc != null)
				proc.destroy();
			System.out.println("경과시간 : " + (System.currentTimeMillis() - time) + "ms");
		}

		return fileLink;

	}


	@Override
	public boolean getYoutubeName(String youtubeLink, int subid) throws Exception {
		final Runtime run = Runtime.getRuntime();

		long time = System.currentTimeMillis();

		final String command = "python3.7 youtubeName.py " + youtubeLink + " " + subid;
		System.out.println("command : " + command);
		Process proc = null;
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			proc= run.exec(command);
			InputStream is = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}

			InputStream standardError = proc.getErrorStream();
			InputStreamReader ow = new InputStreamReader(standardError);
			BufferedReader errorReader = new BufferedReader(ow);
			StringBuffer stderr = new StringBuffer();
			String lineErr = null;
			while((lineErr = errorReader.readLine()) != null){
				stderr.append(lineErr).append("\n");
			}

			System.out.println(stderr.toString());

			if(!proc.waitFor(60, TimeUnit.SECONDS)){
				proc.destroy();
			}

		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			System.err.println("Failed to execute: " + e.getMessage());
		} finally {
			if(proc != null)
				proc.destroy();
			System.out.println("경과시간 : " + (System.currentTimeMillis() - time) + "ms");
		}

		return false;
	}

	@Override
	public List<Transcript> translateLocalFile(final String filepath, String start, String target) throws Exception {
		final Recognize rec = new Recognize();
		System.out.println("video service videotran filepath  : " + filepath);
		List<SpeechRecognitionResult> results = rec.syncRecognizeWords(filepath, start);
		List<Transcript> tranList = new ArrayList<Transcript>();
		for (SpeechRecognitionResult result : results) {

			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
			System.out.printf("Transcription: %s%n", alternative.getTranscript());

			StringBuffer parseTarget = new StringBuffer();
			double targetStartTime = 0;
			double targetEndTime = 0;
			boolean endFlag = true;
			int wordIndex = 0;
			for (WordInfo wordInfo : alternative.getWordsList()) {
				if (endFlag) {
					targetStartTime = wordInfo.getStartTime().getSeconds()
					+ (double) wordInfo.getStartTime().getNanos() / 1000000000;
					endFlag = false;
				}
				String targetWord = wordInfo.getWord();
				int wordLength = targetWord.length();
				if (targetWord.charAt(wordLength - 1) == '.' || wordIndex == alternative.getWordsList().size() - 1) {
					Transcript tempScript = new Transcript("", "", 0, 0, "default.jpg");
					endFlag = true;
					parseTarget.append(targetWord); 
					parseTarget.append(" ");
					tempScript.setStartsub(parseTarget.toString());
					System.out.println(parseTarget.toString());
					parseTarget = new StringBuffer();
					targetEndTime = wordInfo.getEndTime().getSeconds()
							+ (double) wordInfo.getEndTime().getNanos() / 1000000000;

					tempScript.setStartTime(targetStartTime);
					tempScript.setEndTime(targetEndTime);
					System.out.println("시작지점 : " + targetStartTime);
					System.out.println("종료지점 : " + targetEndTime);
					tranList.add(tempScript);
				} else {
					parseTarget.append(targetWord); 
					parseTarget.append(" ");
				}
				wordIndex++;
			}

		}
		return tranList;
	}

	@Override
	public String convertToSrt_(double time) {
		StringBuffer timeLine = new StringBuffer();
		int hourInfo = (int) (time / 3600);
		time -= hourInfo;
		int minuteInfo = (int) (time / 60);
		time -= minuteInfo;
		int secondInfo = (int) (time);
		time -= secondInfo;
		int microInfo = (int) (time * 100);
		timeLine.append(String.format("%02d", hourInfo));
		timeLine.append(':');
		timeLine.append(String.format("%02d", minuteInfo));
		timeLine.append(':');
		timeLine.append(String.format("%02d", secondInfo));
		timeLine.append(',');
		timeLine.append(String.format("%03d", microInfo));

		return timeLine.toString();
	}

	@Override
	public String convertToVTT_(double time) {
		StringBuffer timeLine = new StringBuffer();
		int hourInfo = (int) (time / 3600);
		time -= hourInfo * 3600;
		int minuteInfo = (int) (time / 60);
		time -= minuteInfo * 60;
		int secondInfo = (int) (time % 60);
		time -= secondInfo;
		int microInfo = (int) (time * 1000);
		timeLine.append(String.format("%02d", hourInfo));
		timeLine.append(':');
		timeLine.append(String.format("%02d", minuteInfo));
		timeLine.append(':');
		timeLine.append(String.format("%02d", secondInfo));
		timeLine.append('.');
		timeLine.append(String.format("%03d", microInfo));

		return timeLine.toString();
	}

	@Override
	public boolean converToSrtFile_(String contents, String filename) throws IOException {
		boolean flag = false;
		System.out.println("convertToSrt 시작");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF8"));
		System.out.println("conveerTosrt 완료");
		writer.write(contents);
		writer.close();
		return flag;
	}

	@Override
	public ParseResultSet parseTranslateResult(ParseResultSet result, List<Transcript> tranList, String fileName, int buildId) throws IOException {
		List<Transcript> subTranList = result.getTranlist();
		if(result.getParsedResult() == null){
			subTranList = new ArrayList<Transcript>();
		}
		int tranIndex = 0;
		StringBuffer setSrt = new StringBuffer();
		if(result.getParsedResult() == null){
			setSrt.append("WEBVTT\n\n");
			setSrt.append("STYLE\n::cue {\nbackground-image: linear-gradient(to bottom, dimgray, lightgray)\n;color: papayawhip;\n}\n\n");
			setSrt.append("00:00:00.000 --> 00:00:05.000\n<b>이 자막은 Translately 에서 제공되는 자막입니다.<b>\n\n");
			
		} else {
			setSrt.append(result.getParsedResult());
		}
		System.out.println(tranList.size());
		for (Transcript transcript : tranList) {
			System.out.println("====================================");
			int tranLength = transcript.getTargetsub().length();
			int startLength = transcript.getStartsub().length();

			double startTime = transcript.getStartTime();
			double endTime = transcript.getEndTime();
			double currentTime = transcript.getStartTime();
			double tranTime = endTime - startTime;
			double increaseTime = tranTime / tranLength;
			// System.out.println("구문 길이" + tranLength);
			// System.out.println("시작 시간" + startTime);
			// System.out.println("종료 시간" + endTime);
			// System.out.println("현재 시간" + currentTime);
			// System.out.println("구문 시간" + tranTime);

			// 여기서 index (4) 가 구문 나누는 기준
			int phraseNum = (int)(tranTime / 4);
			if(phraseNum == 0) phraseNum = 1;
			int phraseMaxLength = tranLength / phraseNum;
			int phraseLength = 0;
			StringBuffer parsedTran = new StringBuffer();
			int parsedCount = 0;
			StringBuffer tempBuffer = new StringBuffer();
			for(int i = 0; i < tranLength; i++){
				currentTime += increaseTime;
				phraseLength++;
				parsedTran.append(transcript.getTargetsub().charAt(i));

				if(phraseLength >= phraseMaxLength && transcript.getTargetsub().charAt(i) == ' '){
					double phraseStartTime = startTime;
					double phraseEndTime = currentTime;
					int startLan = parsedCount*startLength/phraseNum;
					int endLan = (parsedCount+1)*startLength/phraseNum;
					if(endLan > startLength) endLan = startLength;

					String startPhrase = tempBuffer.toString() + transcript.getStartsub().substring(startLan, endLan);
					System.out.println("변경 전 startPhrase : " + startPhrase);
					tempBuffer = new StringBuffer();
					for(int j = startPhrase.length() -1; j >= 0; j--){
						if(startPhrase.charAt(j) == ' '){
							tempBuffer.append(startPhrase.substring(j, startPhrase.length()));
							System.out.println("tempBuffer : " + tempBuffer.toString());
							startPhrase = startPhrase.substring(0, j);
							System.out.println("변경 후 startPharse : " + startPhrase);
							break;
						}
					}
					parsedCount++;
					subTranList.add(new Transcript(startPhrase, parsedTran.toString(), phraseStartTime + parseTimeLength * buildId , phraseEndTime + parseTimeLength * buildId, "default.jpg"));
					System.out.println(parsedTran.toString());
					{
						// srt 양식 맞추는 과정
						tranIndex++;
						setSrt.append(tranIndex);
						setSrt.append("\n");
						setSrt.append(convertToVTT_(phraseStartTime + parseTimeLength * buildId));
						setSrt.append(" --> ");
						setSrt.append(convertToVTT_(phraseEndTime + parseTimeLength * buildId));
						setSrt.append("\n");
						setSrt.append(parsedTran.toString());
						setSrt.append("\n");
						setSrt.append("\n");
						
					}
					parsedTran = new StringBuffer();
					startTime = currentTime;
					phraseLength = 0;
				}
			}
			if(parsedTran.toString().length()>0){
				int startLan = parsedCount*startLength/phraseNum;
				int endLan = (parsedCount+1)*startLength/phraseNum;
				if(endLan > startLength) endLan = startLength;
				
				subTranList.add(new Transcript(tempBuffer.toString() + transcript.getStartsub().substring(startLan, endLan), parsedTran.toString(), startTime + parseTimeLength * buildId, endTime + parseTimeLength * buildId, "default.jpg"));
				System.out.println(parsedTran.toString());
				{
					// srt 양식 맞추는 과정
					tranIndex++;
					setSrt.append(tranIndex);
					setSrt.append("\n");
					setSrt.append(convertToVTT_(startTime + parseTimeLength * buildId));
					setSrt.append(" --> ");
					setSrt.append(convertToVTT_(endTime + parseTimeLength * buildId));
					setSrt.append("\n");
					setSrt.append(parsedTran.toString());
					setSrt.append("\n");
					setSrt.append("\n");
					
				}
			}
		}


		//transDao.saveTranscript(subTranList, subid);
		
		return new ParseResultSet(setSrt.toString(), subTranList);
	}

	@Override
	public List<Transcript> papagoTranslate(List<Transcript> tranList, String startLanguage, String targetLanguage) throws Exception {
		List<Transcript> papagoTranList = new ArrayList<Transcript>();
		APIExamTranslate papago = new APIExamTranslate();

		try {
			System.out.println("translate start");
			for (Transcript transcript : tranList) {
				System.out.println(transcript.getStartsub());
				transcript.setTargetsub(papago.EngToKoR(transcript.getStartsub(), startLanguage, targetLanguage));
				System.out.println(transcript.getTargetsub());
			}
	
			System.out.println("translate end");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("papago Fail");			
		}
		return tranList;
	}

	@Override
	public int saveFileInfo(SubtitleFileInfo fileInfo) {
		return transDao.saveFileInfo(fileInfo);
	}

	@Override
	public int saveTranscript(List<Transcript> translist, int subid) {
		return transDao.saveTranscript(translist, subid);
	}

	@Override
	public boolean getCapture(String fileName) throws Exception {
		final Runtime run = Runtime.getRuntime();
		
		long time = System.currentTimeMillis();

		final String command = "ffmpeg -ss 1 -i " + SERVER_LOCATION + MP4_DIR + fileName + MP4_EX + 
								" -y -vframes 1 "  + SERVER_LOCATION + JPG_DIR + fileName + JPG_EX;
		System.out.println("command : " + command);
		Process proc = null;
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			proc= run.exec(command);
			InputStream is = proc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}

			InputStream standardError = proc.getErrorStream();
			InputStreamReader ow = new InputStreamReader(standardError);
			BufferedReader errorReader = new BufferedReader(ow);
			StringBuffer stderr = new StringBuffer();
			String lineErr = null;
			while((lineErr = errorReader.readLine()) != null){
				stderr.append(lineErr).append("\n");
			}

			System.out.println(stderr.toString());

			if(!proc.waitFor(3, TimeUnit.SECONDS)){
				proc.destroy();
			}

		} catch (IOException e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e){
			System.err.println("Failed to execute: " + e.getMessage());
		} finally {
			if(proc != null)
				proc.destroy();
			System.out.println("경과시간 : " + (System.currentTimeMillis() - time) + "ms");
		}

		return false;
	}

	@Override
	public int reduceRemainTime(int userid) {
		return userDao.reduceRemainTime(userid);
	}

	@Override
	public String buildVTTString(List<Transcript> translist) {
		StringBuilder setVtt = new StringBuilder();
		setVtt.append("WEBVTT\n\n");
		setVtt.append("STYLE\n::cue {\nbackground-image: linear-gradient(to bottom, dimgray, lightgray)\n;color: papayawhip;\n}\n\n");
		setVtt.append("00:00:00.000 --> 00:00:05.000\n<b>이 자막은 Translately 에서 제공되는 자막입니다.<b>\n\n");
		int tranIndex = 0;
		for (Transcript transcript : translist) {
			tranIndex++;
			setVtt.append(tranIndex);
			setVtt.append("\n");
			setVtt.append(convertToVTT_(transcript.getStartTime()));
			setVtt.append(" --> ");
			setVtt.append(convertToVTT_(transcript.getEndTime()));
			setVtt.append("\n");
			setVtt.append(transcript.getTargetsub());
			setVtt.append("\n");
			setVtt.append("\n");
		}
		return setVtt.toString();
	}




}
