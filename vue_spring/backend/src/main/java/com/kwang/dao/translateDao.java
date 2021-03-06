package com.kwang.dao;

import java.util.List;
import java.util.Map;

import com.kwang.dto.BuildTranslateResult;
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
	public List<SubtitleFileInfo> findFilesByUseridAndKeyword(int userid, String keyword);
	public List<Transcript> findSubtitleBySubid(int subid);
	public SubtitleFileInfo findSubFileInfoBySubid(int subid);
	public SubtitleFileInfo findSubFileInfoBySubid(Map <String, String> fileName);
	public int modifyTranscript(List<Transcript> translist);
	public BuildTranslateResult findSubtitleByYoutubeUrl(String youtubeUrl);
	public int deleteSubtitleBySubid(int subid, int userid);
    public int countUser();
    public int countSubtitle();
}

