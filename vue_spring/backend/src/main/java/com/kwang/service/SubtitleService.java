package com.kwang.service;

import java.util.List;
import java.util.Map;

import com.kwang.dto.BuildTranslateResult;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;

public interface SubtitleService {
    public List<SubtitleFileInfo> findAll();
	public List<SubtitleFileInfo> findFilesByKeyword(String keyword);
    public List<SubtitleFileInfo> findFilesByUserid(int userid);
    public List<SubtitleFileInfo> findFilesByUseridAndKeyword(int userid, String keyword);
    public List<Transcript> findSubtitleBySubid(int subid);
    public SubtitleFileInfo findSubFileInfoBySubid(Map <String, String> fileName);
    public BuildTranslateResult findSubtitleByYoutubeUrl(String youtubeUrl);
    public SubtitleFileInfo findSubFileInfoBySubid(int subid);
    public int modifyTranscript(List<Transcript> translist);
    public int deleteSubtitleBySubid(int subid, int userid);
    public int countUser();
    public int countSubtitle();
    
}
