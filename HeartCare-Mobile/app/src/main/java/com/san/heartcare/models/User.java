package com.san.heartcare.models;


import java.util.Map;

public class User {
    public String name;
    public String last_name;

    public int age;
    public double height;
    public double weight;
    public String wristband;
    public Map<String, Boolean> diseases;
    public Map<String, Boolean> heart_rates;
    public Map<String, Boolean> incidents;

    public User(String name, String last_name, int age, double height, double weight, String wristband, Map<String, Boolean> diseases, Map<String, Boolean> heart_rates, Map<String, Boolean> incidents) {
        this.name = name;
        this.last_name = last_name;
        this.height = height;
        this.weight = weight;
        this.wristband = wristband;
        this.diseases = diseases;
        this.heart_rates = heart_rates;
        this.incidents = incidents;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWristband() {
        return wristband;
    }

    public void setWristband(String wristband) {
        this.wristband = wristband;
    }

    public Map<String, Boolean> getDiseases() {
        return diseases;
    }

    public void setDiseases(Map<String, Boolean> diseases) {
        this.diseases = diseases;
    }

    public Map<String, Boolean> getHeart_rates() {
        return heart_rates;
    }

    public void setHeart_rates(Map<String, Boolean> heart_rates) {
        this.heart_rates = heart_rates;
    }

    public Map<String, Boolean> getIncidents() {
        return incidents;
    }

    public void setIncidents(Map<String, Boolean> incidents) {
        this.incidents = incidents;
    }
}
