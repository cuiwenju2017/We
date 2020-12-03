package com.cwj.we.bean;

import cn.bmob.v3.BmobObject;

public class Day extends BmobObject {
    private User author;
    private String togetherTime;//在一起的时间
    private String getMarriedTime;//结婚时间农历  格式（2018）年三月初八
    private String getMarriedTime2;//结婚时间农历 格式2018-03-08
    private String getMarriedTime3;//结婚时间公历

    public String getTogetherTime() {
        return togetherTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTogetherTime(String togetherTime) {
        this.togetherTime = togetherTime;
    }

    public String getGetMarriedTime() {
        return getMarriedTime;
    }

    public void setGetMarriedTime(String getMarriedTime) {
        this.getMarriedTime = getMarriedTime;
    }

    public String getGetMarriedTime2() {
        return getMarriedTime2;
    }

    public void setGetMarriedTime2(String getMarriedTime2) {
        this.getMarriedTime2 = getMarriedTime2;
    }

    public String getGetMarriedTime3() {
        return getMarriedTime3;
    }

    public void setGetMarriedTime3(String getMarriedTime3) {
        this.getMarriedTime3 = getMarriedTime3;
    }
}
