package com.kwang.service;

import java.util.List;

import com.kwang.dto.BuildTranslateResult;
import com.kwang.dto.SubtitleFileInfo;
import com.kwang.dto.Transcript;

public interface SubtitleService {
    public List<SubtitleFileInfo> findAll();
	public List<SubtitleFileInfo> findFilesByKeyword(String keyword);
    public List<SubtitleFileInfo> findFilesByUserid(int userid);
    public List<Transcript> findSubtitleBySubid(int subid);
    public BuildTranslateResult findSubtitleByYoutubeUrl(String youtubeUrl);
    public int modifyTranscript(List<Transcript> translist);
    public int deleteSubtitleBySubid(int subid, int userid);
    public int countUser();
    public int countSubtitle();
    
}
