package com.kwang.dto;

public class SubtitleFileInfo {
    private int subid;
    private int userid;
    private String video_name;
    private String thumbnail;
    private String subtitle_file;
    private String youtube_url;
    private String start_sub_code;
    private String target_sub_code;
    private int duration;


    public SubtitleFileInfo(){

    }

    public SubtitleFileInfo(int userid, String video_name, String thumbnail, String subtitle_file, String youtube_url, String start_sub_code, String target_sub_code, int duration) {
        this.userid = userid;
        this.video_name = video_name;
        this.thumbnail = thumbnail;
        this.subtitle_file = subtitle_file;
        this.youtube_url = youtube_url;
        this.start_sub_code = start_sub_code;
        this.target_sub_code = target_sub_code;
        this.duration = duration;
    }

    public SubtitleFileInfo(int subid, int userid, String video_name, String thumbnail, String subtitle_file, String youtube_url, String start_sub_code, String target_sub_code, int duration) {
        this.subid = subid;
        this.userid = userid;
        this.video_name = video_name;
        this.thumbnail = thumbnail;
        this.subtitle_file = subtitle_file;
        this.youtube_url = youtube_url;
        this.start_sub_code = start_sub_code;
        this.target_sub_code = target_sub_code;
        this.duration = duration;
    }



    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTarget_sub_code() {
        return target_sub_code;
    }

    public void setTarget_sub_code(String target_sub_code) {
        this.target_sub_code = target_sub_code;
    }

    public String getStart_sub_code() {
        return start_sub_code;
    }

    public void setStart_sub_code(String start_sub_code) {
        this.start_sub_code = start_sub_code;
    }



    public int getSubid() {
        return subid;
    }
    
    public String getThumbnail() {
        return this.thumbnail;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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