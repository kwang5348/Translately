package com.kwang.service;

import java.util.List;

import com.kwang.dao.translateDao;
import com.kwang.dto.SubtitleFileInfo;

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


}
