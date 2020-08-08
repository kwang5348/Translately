package com.kwang.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import com.kwang.dao.translateDao;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.papago.APIExamTranslate;
import com.kwang.stt.Recognize;

import io.grpc.internal.ClientStream;

@Service
public class VideoTranslateServiceImpl implements VideoTranslateService {

	@Autowired
	private translateDao transDao;
	
	static int subid;
	@Override
	public String convertToAudio(String fileName) throws Exception {
		final Runtime run = Runtime.getRuntime();
		String filePath = "/home/ubuntu/resources/wav/";
		if (fileName.indexOf(".mp4") == -1) {
			return null;
		}

		String resultFile = filePath + fileName.replace(".mp4", ".wav");

		final String command = "ffmpeg -i " + filePath + fileName + " -t 20 -ar 16000 -ac 1 " + resultFile;
		System.out.println("command : " + command);
		try {
			//run.exec("cmd.exe chcp 65001"); // cmd에서 한글문제로 썸네일이 만들어지지않을시 cmd창에서 utf-8로 변환하는 명령
			run.exec(command);

		} catch (final Exception e) {
			System.out.println("error : " + e.getMessage());
			e.printStackTrace();
		}

		// 파일을 저장하는 dao 호출
		SubtitleFileInfo fileInfo = new SubtitleFileInfo(1, "wow excellent fantastic", "default.jpg", fileName.replace(".mp4", ""), null);
		subid = transDao.saveFileInfo(fileInfo);

		return resultFile;

	}

	@Override
	public List<Transcript> translateLocalFile(final String filepath) throws Exception {
		final Recognize rec = new Recognize();
		System.out.println("video service videotran filepath  : " + filepath);
		List<SpeechRecognitionResult> results = rec.syncRecognizeWords(filepath);
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
					Transcript tempScript = new Transcript("", "", 0, 0);
					endFlag = true;
					parseTarget.append(targetWord); 
					parseTarget.append(" ");
					tempScript.setEng(parseTarget.toString());
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
	public String parseTranslateResult(List<Transcript> tranList) throws IOException {
		List<Transcript> subTranList = new ArrayList<Transcript>();
		int tranIndex = 0;
		StringBuffer setSrt = new StringBuffer();
		System.out.println(tranList.size());
		for (Transcript transcript : tranList) {
			System.out.println("====================================");
			int tranLength = transcript.getKor().length();
			int startLength = transcript.getEng().length();

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
				parsedTran.append(transcript.getKor().charAt(i));

				if(phraseLength >= phraseMaxLength && transcript.getKor().charAt(i) == ' '){
					double phraseStartTime = startTime;
					double phraseEndTime = currentTime;
					int startLan = parsedCount*startLength/4;
					int endLan = (parsedCount+1)*startLength/4;
					if(endLan > startLength) endLan = startLength;

					String startPhrase = tempBuffer.toString() + transcript.getEng().substring(startLan, endLan);
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
					subTranList.add(new Transcript(startPhrase, parsedTran.toString(), phraseStartTime, phraseEndTime));
					System.out.println(parsedTran.toString());
					{
						// srt 양식 맞추는 과정
						tranIndex++;
						setSrt.append(tranIndex);
						setSrt.append("\n");
						setSrt.append(convertToVTT_(phraseStartTime));
						setSrt.append(" --> ");
						setSrt.append(convertToVTT_(phraseEndTime));
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
				int startLan = parsedCount*startLength/4;
				int endLan = (parsedCount+1)*startLength/4;
				if(endLan > startLength) endLan = startLength;
				subTranList.add(new Transcript(tempBuffer.toString() + transcript.getEng().substring(startLan, endLan), parsedTran.toString(), startTime, endTime));
				System.out.println(parsedTran.toString());
				{
					// srt 양식 맞추는 과정
					tranIndex++;
					setSrt.append(tranIndex);
					setSrt.append("\n");
					setSrt.append(convertToVTT_(startTime));
					setSrt.append(" --> ");
					setSrt.append(convertToVTT_(endTime));
					setSrt.append("\n");
					setSrt.append(parsedTran.toString());
					setSrt.append("\n");
					setSrt.append("\n");
					
				}
			}
		}
		transDao.saveTranscript(subTranList, subid);
		return setSrt.toString();
	}

	@Override
	public List<Transcript> papagoTranslate(List<Transcript> tranList) throws Exception {
		List<Transcript> papagoTranList = new ArrayList<Transcript>();
		APIExamTranslate papago = new APIExamTranslate();

		try {
			System.out.println("translate start");
			for (Transcript transcript : tranList) {
				System.out.println(transcript.getEng());
				transcript.setKor(papago.EngToKoR(transcript.getEng(), "en-US"));
				System.out.println(transcript.getKor());
			}
	
			System.out.println("translate end");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("papago Fail");			
		}
		return tranList;
	}

}
