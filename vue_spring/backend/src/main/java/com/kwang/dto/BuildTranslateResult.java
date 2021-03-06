package com.kwang.dto;

import java.util.List;

public class BuildTranslateResult {
    private int buildId;
    private int finalBuild;
    private List<Transcript> transcript;
    private SubtitleFileInfo fileInfo;
    private String vttResult;

    public BuildTranslateResult() {
    }

    public BuildTranslateResult(List<Transcript> transcript, SubtitleFileInfo fileInfo) {
        this.transcript = transcript;
        this.fileInfo = fileInfo;
    }

    public BuildTranslateResult(int buildId, int finalBuild, List<Transcript> transcript, SubtitleFileInfo fileInfo, String vttResult) {
        this.buildId = buildId;
        this.finalBuild = finalBuild;
        this.transcript = transcript;
        this.fileInfo = fileInfo;
        this.vttResult = vttResult;
    }


    public int getBuildId() {
        return this.buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public int getFinalBuild() {
        return this.finalBuild;
    }

    public void setFinalBuild(int finalBuild) {
        this.finalBuild = finalBuild;
    }

    public List<Transcript> getTranscript() {
        return this.transcript;
    }

    public void setTranscript(List<Transcript> transcript) {
        this.transcript = transcript;
    }

    public SubtitleFileInfo getFileInfo() {
        return this.fileInfo;
    }

    public void setFileInfo(SubtitleFileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getVttResult() {
        return this.vttResult;
    }

    public void setVttResult(String vttResult) {
        this.vttResult = vttResult;
    }

}   