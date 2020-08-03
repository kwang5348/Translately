package com.kwang.dto;


public class Transcript {
    String eng;
    String kor;
    double startTime;
    double endTime;

    public String getEng() {
        return this.eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getKor() {
        return this.kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public double getStartTime() {
        return this.startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return this.endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    

    public Transcript (String eng, String kor, double startTime, double endTime){
        this.eng = eng;
        this.kor = kor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}