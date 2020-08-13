package com.kwang.dto;

public class Transcript {
    private int subid;
    private String startsub;
    private String targetsub;
    private double starttime;
    private double endtime;
    private String que_thumbnail;

    public Transcript(String startsub, String targetsub, double starttime, double endtime, String que_thumbnail) {
        this.setStartsub(startsub);
        this.setTargetsub(targetsub);
        this.starttime = starttime;
        this.endtime = endtime;
        this.que_thumbnail = que_thumbnail;
    }

    public String getStartsub() {
        return startsub;
    }

    public void setStartsub(String startsub) {
        this.startsub = startsub;
    }

    public String getTargetsub() {
        return targetsub;
    }

    public void setTargetsub(String targetsub) {
        this.targetsub = targetsub;
    }

    public int getSubid() {
        return subid;
    }

    public String getQue_thumbnail() {
        return que_thumbnail;
    }

    public void setQue_thumbnail(String que_thumbnail) {
        this.que_thumbnail = que_thumbnail;
    }

    public void setSubid(int subid) {
        this.subid = subid;
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
}