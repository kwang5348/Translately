package com.kwang.dao;

import java.util.List;

import com.kwang.dto.UserData;

public interface UserDao {

	public List<UserData> input_user_from_dao();
	public UserData findUserByEmailAndPassword(UserData request);
	public int join(UserData request);
	public UserData findUserByEmail(String email);
	public int deleteUserByEmail(String request);
	public int modifyUser(UserData request);
	public int reduceRemainTime(int userid, int parseTime);
	public int getRemainTime(int userid);

}

