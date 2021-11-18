package com.cwj.we.bean;

import cn.bmob.v3.BmobObject;

public class JingqiTime extends BmobObject {
    private User author;
    private String time;
    private boolean type;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
