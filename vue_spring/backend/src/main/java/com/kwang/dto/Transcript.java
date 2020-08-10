package com.kwang.dto;

public class Transcript {
    private int subid;
    private String startsub;
    private String targetsub;
    private double starttime;
    private double endtime;

    @Override
    public String toString() {
        return "{" + " startsub='" + startsub + "'" + ", targetsub='" + targetsub + "'" + ", starttime='" + starttime
                + "'" + ", endtime='" + endtime + "'" + "}";
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public String getEng() {
        return this.startsub;
    }

    public void setEng(String eng) {
        this.startsub = eng;
    }

    public String getKor() {
        return this.targetsub;
    }

    public void setKor(String kor) {
        this.targetsub = kor;
    }

    public double getStartTime() {
        return this.starttime;
    }

    public void setStartTime(double startTime) {
        this.starttime = startTime;
    }

    public double getEndTime() {
        return this.endtime;
    }

    public void setEndTime(double endTime) {
        this.endtime = endTime;
    }

    

    public Transcript (String eng, String kor, double startTime, double endTime){
        this.startsub = eng;
        this.targetsub = kor;
        this.starttime = startTime;
        this.endtime = endTime;
    }

}