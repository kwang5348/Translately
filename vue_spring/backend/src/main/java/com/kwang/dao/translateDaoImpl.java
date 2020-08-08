package com.kwang.dao;

import java.util.List;

import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.dto.UserData;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class translateDaoImpl implements translateDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Transcript> findTranscript(String filename) {
		return sqlSession.selectList("transcript.showtranslist");
	}

	@Override
	public int saveFileInfo(SubtitleFileInfo fileinfo) {
		int successCount = sqlSession.insert("transcript.subtitlefileinfo", fileinfo);
		if (successCount == 0){
			System.out.println("transLateDao : 파일정보 저장에 실패하였습니다.");
		} else {
			System.out.println("transLateDao : 파일정보 저장에 성공하였습니다.");
		}
		return successCount;
	}

	@Override
	public int saveTranscript(List<Transcript> translist) {
		int successCount = sqlSession.insert("transcript.savetranscript", translist);
		System.out.println("transLateDao : 총 " + successCount + " 의 번역 큐가 입력되었습니다.");
		return successCount;
	}


}