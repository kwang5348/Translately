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
		System.out.println("login dato start");
		System.out.println(request.toString());
		UserData result = sqlSession.selectOne("userinfo.login", request);
		System.out.println(request.toString());
		System.out.println("login dao end");
		return result;
	}

	@Override
	public int join(UserData request) {
		int successCount = sqlSession.insert("userinfo.join", request);
		return successCount;
	}

	@Override
	public boolean findUserByEmail(String email) {
		UserData result = sqlSession.selectOne("userinfo.detail", email);
		if(result == null){
			System.out.println("해당 email 유저를 찾지 못했습니다.");
			System.out.println(result);
			return false;
		} else {
			System.out.println("해당 email 유저를 찾았습니다.");
			System.out.println(result.toString());
			return true;
		}
	}

	@Override
	public int deleteUserByEmail(String request) {
		int successCount = sqlSession.delete("userinfo.delete", request);
		return successCount;
	}

	@Override
	public int modifyUser(UserData request) {
		int successCount = sqlSession.update("userinfo.modify", request);
		return successCount;

	}


}