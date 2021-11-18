package com.cwj.we.bean;

import cn.bmob.v3.BmobObject;

public class Zhouqi extends BmobObject {
    private User author;
    private int zhouqiDay;//周期天数
    private int jingqiDay;//经期天数

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getZhouqiDay() {
        return zhouqiDay;
    }

    public void setZhouqiDay(int zhouqiDay) {
        this.zhouqiDay = zhouqiDay;
    }

    public int getJingqiDay() {
        return jingqiDay;
    }

    public void setJingqiDay(int jingqiDay) {
        this.jingqiDay = jingqiDay;
    }
}
