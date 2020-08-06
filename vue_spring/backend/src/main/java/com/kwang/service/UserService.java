package com.kwang.service;

import java.util.List;

import com.kwang.dto.UserData;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	public List<UserData> input_user_from_service(String uid, String password);
	public UserData findUserByEmailAndPassword(UserData request);
	public UserData join(UserData request);
	public boolean findUserByEmail(String email);
}
