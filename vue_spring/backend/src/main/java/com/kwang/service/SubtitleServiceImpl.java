package com.kwang.service;

import java.util.List;

import com.kwang.dao.translateDao;
import com.kwang.dto.BuildTranslateResult;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubtitleServiceImpl implements SubtitleService {

	@Autowired
	private translateDao transDao;

    @Override
    public List<SubtitleFileInfo> findAll() {
        System.out.println("subtitle service 진입성공");
        return transDao.findAll();
    }

    @Override
    public List<SubtitleFileInfo> findFilesByKeyword(String keyword) {
        
        return transDao.findFilesByKeyword(keyword);
    }

    @Override
    public List<SubtitleFileInfo> findFilesByUserid(int userid) {
        return transDao.findFilesByUserid(userid);
    }

    @Override
    public List<Transcript> findSubtitleBySubid(int subid) {
        
        return transDao.findSubtitleBySubid(subid);
    }

    @Override
    public int modifyTranscript(List<Transcript> translist) {
        return transDao.modifyTranscript(translist);
    }

    @Override
    public BuildTranslateResult findSubtitleByYoutubeUrl(String youtubeUrl) {
        return transDao.findSubtitleByYoutubeUrl(youtubeUrl);
    }

    @Override
    public int deleteSubtitleBySubid(int subid, int userid) {
        System.out.println(subid + " " + userid);
        return transDao.deleteSubtitleBySubid(subid, userid);
    }

    @Override
    public int countUser() {
        return transDao.countUser();
    }

    @Override
    public int countSubtitle() {
        return transDao.countSubtitle();
    }

    @Override
    public SubtitleFileInfo findSubFileInfoBySubid(int subid) {
        return transDao.findSubFileInfoBySubid(subid);
    }

    @Override
    public List<SubtitleFileInfo> findFilesByUseridAndKeyword(int userid, String keyword) {
        
        return transDao.findFilesByUseridAndKeyword(userid, keyword);
    }


}
