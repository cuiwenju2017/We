package com.cwj.we.bean;

import java.util.List;

public class WeatherBeans {

    /**
     * city : 杭州
     * update_time : 11:30
     * date : 11月11日
     * weather : [{"date":"11日（今天）","weather":"晴转阴","icon1":"00","icon2":"02","temp":"18/9℃","w":"3-4级转","wind":"西北风转无持续风向"},{"date":"12日（明天）","weather":"晴","icon1":"00","icon2":"00","temp":"16/6℃","w":"","wind":"无持续风向"},{"date":"13日（后天）","weather":"晴","icon1":"00","icon2":"00","temp":"18/6℃","w":"3-4级转","wind":"南风转无持续风向"},{"date":"14日（周日）","weather":"多云转阴","icon1":"01","icon2":"02","temp":"19/7℃","w":"3-4级转","wind":"南风转无持续风向"},{"date":"15日（周一）","weather":"多云","icon1":"01","icon2":"01","temp":"20/8℃","w":"","wind":"无持续风向"},{"date":"16日（周二）","weather":"晴转小雨","icon1":"00","icon2":"07","temp":"20/9℃","w":"4-5级转","wind":"北风转无持续风向"},{"date":"17日（周三）","weather":"小雨","icon1":"07","icon2":"07","temp":"17/8℃","w":"3-4级","wind":"北风转西风"}]
     */

    private String city;
    private String update_time;
    private String date;
    private List<WeatherBean> weather;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<WeatherBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherBean> weather) {
        this.weather = weather;
    }

    public static class WeatherBean {
        /**
         * date : 11日（今天）
         * weather : 晴转阴
         * icon1 : 00
         * icon2 : 02
         * temp : 18/9℃
         * w : 3-4级转
         * wind : 西北风转无持续风向
         */

        private String date;
        private String weather;
        private String icon1;
        private String icon2;
        private String temp;
        private String w;
        private String wind;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getIcon1() {
            return icon1;
        }

        public void setIcon1(String icon1) {
            this.icon1 = icon1;
        }

        public String getIcon2() {
            return icon2;
        }

        public void setIcon2(String icon2) {
            this.icon2 = icon2;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getW() {
            return w;
        }

        public void setW(String w) {
            this.w = w;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }
    }
}
