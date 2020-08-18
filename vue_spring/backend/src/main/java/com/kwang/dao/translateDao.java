package com.kwang.dao;

import java.util.List;

import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;
import com.kwang.dto.UserData;

public interface translateDao {
	
	public List<Transcript> findTranscript(String filename);
	public int saveFileInfo(SubtitleFileInfo fileInfo);
	public int saveTranscript(List<Transcript> translist, int subid);
	
	public List<SubtitleFileInfo> findAll();
	public List<SubtitleFileInfo> findFilesByKeyword(String keyword);
	public List<SubtitleFileInfo> findFilesByUserid(int userid);
	public List<Transcript> findSubtitleBySubid(int subid);
	public int modifyTranscript(List<Transcript> translist);

}

