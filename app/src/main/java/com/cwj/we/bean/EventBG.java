package com.cwj.we.bean;

public class EventBG {

    private String type;
    private String userIconPath;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserIconPath() {
        return userIconPath;
    }

    public void setUserIconPath(String userIconPath) {
        this.userIconPath = userIconPath;
    }

    public EventBG(String type, String userIconPath) {
        this.type = type;
        this.userIconPath = userIconPath;
    }
}
