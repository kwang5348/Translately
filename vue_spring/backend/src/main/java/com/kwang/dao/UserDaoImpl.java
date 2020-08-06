package com.kwang.dao;

import java.util.List;

import com.kwang.dto.UserData;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession sqlSession;

	public List<UserData> input_user_from_dao() {
		List<UserData> userlogin = sqlSession.selectList("usermapper.SelectUser");

		return userlogin;
	}	

	@Override
	public UserData findUserByEmailAndPassword(UserData request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserData join(UserData request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}
}