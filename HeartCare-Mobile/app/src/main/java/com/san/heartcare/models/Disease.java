package com.san.heartcare.models;

import java.util.Map;

public class Disease {
    public String name;
    public String description;
    public Map<String, Boolean> users;

    public Disease() {
    }

    public Disease(String name, String description, Map<String, Boolean> users) {
        this.name = name;
        this.description = description;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }
}
