package com.team4.yamblz.timetogo.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceCategory {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("objects")
    @Expose
    private List<Place> places = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}