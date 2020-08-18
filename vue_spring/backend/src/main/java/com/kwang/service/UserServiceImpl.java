package com.kwang.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import com.kwang.dto.UserData;

import io.grpc.internal.ClientStream;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserData> input_user_from_service(String uid, String password) {
		// UserData result = userDao.input_user_from_service(ud);
		return userDao.input_user_from_dao();
	}

	@Override
	public UserData findUserByEmailAndPassword(UserData request) {
		return userDao.findUserByEmailAndPassword(request);
	}

	@Override
	public int join(UserData request) {
		return userDao.join(request);
	}

	@Override
	public UserData findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public int deleteUserByEmail(String request) {
		
		return userDao.deleteUserByEmail(request);
	}

	@Override
	public int modifyUser(UserData request) {
		return userDao.modifyUser(request);
	}

    @Override
    public int getRemainTime(int userid) {
        
        return userDao.getRemainTime(userid);
    }

}
