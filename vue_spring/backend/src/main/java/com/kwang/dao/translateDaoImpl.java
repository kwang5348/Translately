package com.kwang.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kwang.dto.BuildTranslateResult;
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
	public List<SubtitleFileInfo> findFilesByKeyword(String keyword) {
		// System.out.println("dao 진입 성공");
		List<SubtitleFileInfo> result = sqlSession.selectList("transcript.showfiles_by_keyword", keyword);
		return result;
	}

	@Override
	public List<SubtitleFileInfo> findFilesByUserid(int userid) {
		// System.out.println("유저 id로 자막 찾기 dao 진입 성공");
		List<SubtitleFileInfo> result = sqlSession.selectList("transcript.showfiles_by_userid", userid);
		return result;
	}

	@Override
	public List<Transcript> findSubtitleBySubid(int subid) {
		// System.out.println("유저 id로 자막 찾기 dao 진입 성공");
		List<Transcript> result = sqlSession.selectList("transcript.find_subtitle_by_subid", subid);
		return result;
	}

	@Override
	public List<Transcript> findTranscript(String filename) {
		return sqlSession.selectList("transcript.showtranslist", filename);
	}

	@Override
	public int saveFileInfo(SubtitleFileInfo fileInfo) {
		int successCount = sqlSession.insert("transcript.subtitlefileinfo", fileInfo);
		if (successCount == 0){
			System.out.println("transLateDao : 파일정보 저장에 실패하였습니다.");
			return 0;
		} else {
			System.out.println("transLateDao : 파일정보 저장에 성공하였습니다.");
			return fileInfo.getSubid();
		}
	}

	@Override
	public int saveTranscript(List<Transcript> translist, int subid) {
		int result = 0;
		for (Transcript transcript : translist) {
			System.out.println(transcript.toString());
			transcript.setSubid(subid);
			int successCount = sqlSession.insert("transcript.savetranscriptone", transcript);
			result += successCount;
		}
		System.out.println("transLateDao : 총 " + result + " 의 번역 큐가 입력되었습니다.");
		return result;
	}

	@Override
	public List<SubtitleFileInfo> findAll() {
		System.out.println("dao 진입 성공");
		List<SubtitleFileInfo> result = sqlSession.selectList("transcript.selectAll");
		return result;

	}

	@Override
	public int modifyTranscript(List<Transcript> translist) {
		int result = 0;
		for (Transcript transcript : translist) {
			System.out.println(transcript.getSubid());
			int successCount = sqlSession.update("transcript.modify_transcript", transcript);
			System.out.println( "이게 뭐임 : " + successCount);
			result += successCount;
		}
		System.out.println("transLateDao : 총 " + result + " 의 번역 큐가 수정되었습니다.");
		return result;
	}

	@Override
	public BuildTranslateResult findSubtitleByYoutubeUrl(String youtubeUrl) {
		int subid = 0;
		subid = sqlSession.selectOne("transcript.get_sub_youtubeUrl", youtubeUrl);

		if(subid == 0){
			return null;
		} else {
			//List<Transcript> translist = 
		}
		return null;
	}

	@Override
	public int deleteSubtitleBySubid(int subid, int userid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("subid", subid);
		map.put("userid", userid);
		int successCount = sqlSession.delete("transcript.delete", map);
		return successCount;
	}

	@Override
	public int countUser() {
		return sqlSession.selectOne("transcript.count_user");
	}

	@Override
	public int countSubtitle() {
		return sqlSession.selectOne("transcript.count_subtitle");
	}

	@Override
	public SubtitleFileInfo findSubFileInfoBySubid(int subid) {
		
		return sqlSession.selectOne("transcript.get_sub_fileinfo", subid);
	}

	@Override
	public List<SubtitleFileInfo> findFilesByUseridAndKeyword(int userid, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("keyword", keyword);
		List<SubtitleFileInfo> result = sqlSession.selectList("transcript.showfiles_myproject_by_keyword", map);
		return result;
	}

	@Override
	public SubtitleFileInfo findSubFileInfoBySubid(Map <String, String> fileName) {
		return sqlSession.selectOne("transcript.get_sub_fileinfo_by_filename", fileName);
	}


}