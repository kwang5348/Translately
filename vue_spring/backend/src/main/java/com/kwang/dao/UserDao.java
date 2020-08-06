package com.kwang.dao;

import java.util.List;

import com.kwang.dto.UserData;

public interface UserDao {

	public List<UserData> input_user_from_dao();
	public UserData findUserByEmailAndPassword(UserData request);
	public UserData join(UserData request);
	public boolean findUserByEmail(String email);
}

