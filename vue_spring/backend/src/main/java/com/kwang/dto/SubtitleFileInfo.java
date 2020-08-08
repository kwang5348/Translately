package com.kwang.dto;

public class SubtitleFileInfo {
    private String video_name;
    private String thumbnail;
    private String subtitle_file;
    private String youtube_url;

    public SubtitleFileInfo(String video_name, String thumbnail, String subtitle_file, String youtube_url) {
        this.video_name = video_name;
        this.thumbnail = thumbnail;
        this.subtitle_file = subtitle_file;
        this.youtube_url = youtube_url;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSubtitle_file() {
        return this.subtitle_file;
    }

    public void setSubtitle_file(String subtitle_file) {
        this.subtitle_file = subtitle_file;
    }

    public String getYoutube_url() {
        return this.youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

}