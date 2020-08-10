package com.kwang.service;

import java.util.List;

import com.kwang.dto.SubtitleFileInfo;

public interface SubtitleService {
    public List<SubtitleFileInfo> findAll();
	public List<SubtitleFileInfo> findFilesByKeyword(String keyword);

}
