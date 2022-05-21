package com.cwj.we.bean;

import org.litepal.crud.LitePalSupport;

public class DayimaZhouqiBean extends LitePalSupport {

    private long id;
    private String time;
    private boolean isDayima;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDayima() {
        return isDayima;
    }

    public void setDayima(boolean dayima) {
        isDayima = dayima;
    }
}
