package com.team4.yamblz.timetogo.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobilizationBotData {
    @SerializedName("people")
    @Expose
    private List<Person> people = null;
    @SerializedName("places")
    @Expose
    private List<PlaceCategory> mPlaceCategories = null;
    @SerializedName("schedule")
    @Expose
    private List<Schedule> schedule = null;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<PlaceCategory> getPlaceCategories() {
        return mPlaceCategories;
    }

    public void setPlaceCategories(List<PlaceCategory> placeCategories) {
        this.mPlaceCategories = placeCategories;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}