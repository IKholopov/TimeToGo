package com.team4.yamblz.timetogo.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("schools")
    @Expose
    private List<String> schools = null;
    @SerializedName("telegram")
    @Expose
    private String telegram;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getSchools() {
        return schools;
    }

    public void setSchools(List<String> schools) {
        this.schools = schools;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }
}