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

    public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
}