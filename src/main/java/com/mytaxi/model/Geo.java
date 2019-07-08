package com.mytaxi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geo {
    private String latitude;
    private String longitude;

    public Geo(){
        super();
    }

    public Geo(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter Methods

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    // Setter Methods

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}