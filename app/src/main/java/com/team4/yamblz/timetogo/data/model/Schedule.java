package com.team4.yamblz.timetogo.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {
    private long mId;
    private boolean mAccepted;
    @SerializedName("duration")
    @Expose
    private Double duration;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("schools")
    @Expose
    private List<String> schools = null;
    @SerializedName("teacher")
    @Expose
    private List<String> teacher = null;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("title")
    @Expose
    private String title;

    public Schedule() {
    }

    public Schedule(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getSchools() {
        return schools;
    }

    public void setSchools(List<String> schools) {
        this.schools = schools;
    }

    public List<String> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<String> teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAccepted() {
        return mAccepted;
    }

    public void setAccepted(boolean accepted) {
        mAccepted = accepted;
    }
}