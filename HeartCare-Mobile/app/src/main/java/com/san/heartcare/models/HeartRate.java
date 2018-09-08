package com.san.heartcare.models;

public class HeartRate {
    public String bpm;
    public Location location;
    public String timestamp;

    public HeartRate() {}

    public HeartRate(String bpm, Location location, String timestamp) {
        this.bpm = bpm;
        this.location = location;
        this.timestamp = timestamp;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
