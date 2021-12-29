package com.cwj.we.bean;

import com.google.gson.annotations.SerializedName;

public class WeatherBean {

    /**
     * data : {"forecast_24h":{"0":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"北风","day_wind_direction_code":"8","day_wind_power":"4","day_wind_power_code":"1","max_degree":"5","min_degree":"0","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-27"},"1":{"day_weather":"阴","day_weather_code":"02","day_weather_short":"阴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"7","min_degree":"1","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-28"},"2":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"西北风","day_wind_direction_code":"7","day_wind_power":"5","day_wind_power_code":"2","max_degree":"13","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"西北风","night_wind_direction_code":"7","night_wind_power":"4","night_wind_power_code":"1","time":"2021-12-29"},"3":{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"10","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-30"},"4":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"5","day_wind_power_code":"2","max_degree":"9","min_degree":"2","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-31"},"5":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"11","min_degree":"2","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-01"},"6":{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"4","day_wind_power_code":"1","max_degree":"12","min_degree":"3","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-02"},"7":{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"12","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-03"}},"index":{"airconditioner":{"":"","detail":"您将感到很舒适，一般不需要开启空调。","info":"较少开启","name":"空调开启"},"allergy":{"detail":"","info":"极不易发","name":"过敏"},"carwash":{"detail":"","info":"适宜","name":"洗车"},"chill":{"detail":"","info":"冷","name":"风寒"},"clothes":{"detail":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。","info":"","name":"穿衣"},"cold":{"detail":"天冷，发生感冒机率较大，请注意适当增加衣服，加强自我防护避免感冒。","info":"易发","name":""},"comfort":{"detail":"白天天气阴沉，您会感觉偏冷，不很舒适，请注意添加衣物，以防感冒。","info":"","name":"舒适度"},"diffusion":{"detail":"","info":"较差","name":"空气污染扩散条件"},"dry":{"detail":"","info":"干燥","name":"路况"},"drying":{"detail":"","info":"不太适宜","name":"晾晒"},"fish":{"detail":"天气不好，有风，不适合垂钓。","info":"","name":"钓鱼"},"heatstroke":{"detail":"","info":"无中暑风险","name":"中暑"},"makeup":{"detail":"","info":"保湿","name":"化妆"},"mood":{"detail":"天气阴冷，感觉压抑，不妨给自己的心情放个假，把所有的烦恼丢之脑后，把所有的爱记在心上，整个人便会变得轻松而有力量。","info":"差","name":""},"morning":{"detail":"","info":"较适宜","name":"晨练"},"sports":{"detail":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。","info":"","name":"运动"},"sunglasses":{"detail":"白天天空阴沉，但太阳辐射较强，建议佩戴透射比1级且标注UV380-UV400的浅色太阳镜","info":"","name":"太阳镜"},"sunscreen":{"":"","detail":"属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","info":"弱","name":"防晒"},"time":"20211228","tourism":{"":"","detail":"天气较好，气温稍低，会感觉稍微有点凉，不过也是个好天气哦。适宜旅游，可不要错过机会呦！","info":"适宜","name":"旅游"},"traffic":{"detail":"阴天，路面干燥，交通气象条件良好，车辆可以正常行驶。","info":"良好","name":""},"ultraviolet":{"detail":"弱","info":"最弱","name":"紫外线强度"},"umbrella":{"detail":"阴天，但降水概率很低，因此您在出门的时候无须带雨伞。","info":"不带伞","name":""}},"limit":{"tail_number":"2和8","time":"20211228"},"tips":{"observe":{"0":"光芒透过云缝，洒向大地~","1":"天有点冷，注意保暖~"}}}
     * message : OK
     * status : 200
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * forecast_24h : {"0":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"北风","day_wind_direction_code":"8","day_wind_power":"4","day_wind_power_code":"1","max_degree":"5","min_degree":"0","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-27"},"1":{"day_weather":"阴","day_weather_code":"02","day_weather_short":"阴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"7","min_degree":"1","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-28"},"2":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"西北风","day_wind_direction_code":"7","day_wind_power":"5","day_wind_power_code":"2","max_degree":"13","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"西北风","night_wind_direction_code":"7","night_wind_power":"4","night_wind_power_code":"1","time":"2021-12-29"},"3":{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"10","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-30"},"4":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"5","day_wind_power_code":"2","max_degree":"9","min_degree":"2","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-31"},"5":{"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"11","min_degree":"2","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-01"},"6":{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"4","day_wind_power_code":"1","max_degree":"12","min_degree":"3","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-02"},"7":{"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"12","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-03"}}
         * index : {"airconditioner":{"":"","detail":"您将感到很舒适，一般不需要开启空调。","info":"较少开启","name":"空调开启"},"allergy":{"detail":"","info":"极不易发","name":"过敏"},"carwash":{"detail":"","info":"适宜","name":"洗车"},"chill":{"detail":"","info":"冷","name":"风寒"},"clothes":{"detail":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。","info":"","name":"穿衣"},"cold":{"detail":"天冷，发生感冒机率较大，请注意适当增加衣服，加强自我防护避免感冒。","info":"易发","name":""},"comfort":{"detail":"白天天气阴沉，您会感觉偏冷，不很舒适，请注意添加衣物，以防感冒。","info":"","name":"舒适度"},"diffusion":{"detail":"","info":"较差","name":"空气污染扩散条件"},"dry":{"detail":"","info":"干燥","name":"路况"},"drying":{"detail":"","info":"不太适宜","name":"晾晒"},"fish":{"detail":"天气不好，有风，不适合垂钓。","info":"","name":"钓鱼"},"heatstroke":{"detail":"","info":"无中暑风险","name":"中暑"},"makeup":{"detail":"","info":"保湿","name":"化妆"},"mood":{"detail":"天气阴冷，感觉压抑，不妨给自己的心情放个假，把所有的烦恼丢之脑后，把所有的爱记在心上，整个人便会变得轻松而有力量。","info":"差","name":""},"morning":{"detail":"","info":"较适宜","name":"晨练"},"sports":{"detail":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。","info":"","name":"运动"},"sunglasses":{"detail":"白天天空阴沉，但太阳辐射较强，建议佩戴透射比1级且标注UV380-UV400的浅色太阳镜","info":"","name":"太阳镜"},"sunscreen":{"":"","detail":"属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","info":"弱","name":"防晒"},"time":"20211228","tourism":{"":"","detail":"天气较好，气温稍低，会感觉稍微有点凉，不过也是个好天气哦。适宜旅游，可不要错过机会呦！","info":"适宜","name":"旅游"},"traffic":{"detail":"阴天，路面干燥，交通气象条件良好，车辆可以正常行驶。","info":"良好","name":""},"ultraviolet":{"detail":"弱","info":"最弱","name":"紫外线强度"},"umbrella":{"detail":"阴天，但降水概率很低，因此您在出门的时候无须带雨伞。","info":"不带伞","name":""}}
         * limit : {"tail_number":"2和8","time":"20211228"}
         * tips : {"observe":{"0":"光芒透过云缝，洒向大地~","1":"天有点冷，注意保暖~"}}
         */

        private Forecast24hBean forecast_24h;
        private IndexBean index;
        private LimitBean limit;
        private TipsBean tips;

        public Forecast24hBean getForecast_24h() {
            return forecast_24h;
        }

        public void setForecast_24h(Forecast24hBean forecast_24h) {
            this.forecast_24h = forecast_24h;
        }

        public IndexBean getIndex() {
            return index;
        }

        public void setIndex(IndexBean index) {
            this.index = index;
        }

        public LimitBean getLimit() {
            return limit;
        }

        public void setLimit(LimitBean limit) {
            this.limit = limit;
        }

        public TipsBean getTips() {
            return tips;
        }

        public void setTips(TipsBean tips) {
            this.tips = tips;
        }

        public static class Forecast24hBean {
            /**
             * 0 : {"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"北风","day_wind_direction_code":"8","day_wind_power":"4","day_wind_power_code":"1","max_degree":"5","min_degree":"0","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-27"}
             * 1 : {"day_weather":"阴","day_weather_code":"02","day_weather_short":"阴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"7","min_degree":"1","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-28"}
             * 2 : {"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"西北风","day_wind_direction_code":"7","day_wind_power":"5","day_wind_power_code":"2","max_degree":"13","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"西北风","night_wind_direction_code":"7","night_wind_power":"4","night_wind_power_code":"1","time":"2021-12-29"}
             * 3 : {"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"10","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-30"}
             * 4 : {"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"5","day_wind_power_code":"2","max_degree":"9","min_degree":"2","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2021-12-31"}
             * 5 : {"day_weather":"多云","day_weather_code":"01","day_weather_short":"多云","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"11","min_degree":"2","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-01"}
             * 6 : {"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"东风","day_wind_direction_code":"2","day_wind_power":"4","day_wind_power_code":"1","max_degree":"12","min_degree":"3","night_weather":"多云","night_weather_code":"01","night_weather_short":"多云","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-02"}
             * 7 : {"day_weather":"晴","day_weather_code":"00","day_weather_short":"晴","day_wind_direction":"微风","day_wind_direction_code":"0","day_wind_power":"3","day_wind_power_code":"0","max_degree":"12","min_degree":"0","night_weather":"晴","night_weather_code":"00","night_weather_short":"晴","night_wind_direction":"微风","night_wind_direction_code":"0","night_wind_power":"3","night_wind_power_code":"0","time":"2022-01-03"}
             */

            @SerializedName("0")
            private _$0Bean _$0;
            @SerializedName("1")
            private _$1Bean _$1;
            @SerializedName("2")
            private _$2Bean _$2;
            @SerializedName("3")
            private _$3Bean _$3;
            @SerializedName("4")
            private _$4Bean _$4;
            @SerializedName("5")
            private _$5Bean _$5;
            @SerializedName("6")
            private _$6Bean _$6;
            @SerializedName("7")
            private _$7Bean _$7;

            public _$0Bean get_$0() {
                return _$0;
            }

            public void set_$0(_$0Bean _$0) {
                this._$0 = _$0;
            }

            public _$1Bean get_$1() {
                return _$1;
            }

            public void set_$1(_$1Bean _$1) {
                this._$1 = _$1;
            }

            public _$2Bean get_$2() {
                return _$2;
            }

            public void set_$2(_$2Bean _$2) {
                this._$2 = _$2;
            }

            public _$3Bean get_$3() {
                return _$3;
            }

            public void set_$3(_$3Bean _$3) {
                this._$3 = _$3;
            }

            public _$4Bean get_$4() {
                return _$4;
            }

            public void set_$4(_$4Bean _$4) {
                this._$4 = _$4;
            }

            public _$5Bean get_$5() {
                return _$5;
            }

            public void set_$5(_$5Bean _$5) {
                this._$5 = _$5;
            }

            public _$6Bean get_$6() {
                return _$6;
            }

            public void set_$6(_$6Bean _$6) {
                this._$6 = _$6;
            }

            public _$7Bean get_$7() {
                return _$7;
            }

            public void set_$7(_$7Bean _$7) {
                this._$7 = _$7;
            }

            public static class _$0Bean {
                /**
                 * day_weather : 多云
                 * day_weather_code : 01
                 * day_weather_short : 多云
                 * day_wind_direction : 北风
                 * day_wind_direction_code : 8
                 * day_wind_power : 4
                 * day_wind_power_code : 1
                 * max_degree : 5
                 * min_degree : 0
                 * night_weather : 多云
                 * night_weather_code : 01
                 * night_weather_short : 多云
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2021-12-27
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$1Bean {
                /**
                 * day_weather : 阴
                 * day_weather_code : 02
                 * day_weather_short : 阴
                 * day_wind_direction : 微风
                 * day_wind_direction_code : 0
                 * day_wind_power : 3
                 * day_wind_power_code : 0
                 * max_degree : 7
                 * min_degree : 1
                 * night_weather : 多云
                 * night_weather_code : 01
                 * night_weather_short : 多云
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2021-12-28
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$2Bean {
                /**
                 * day_weather : 多云
                 * day_weather_code : 01
                 * day_weather_short : 多云
                 * day_wind_direction : 西北风
                 * day_wind_direction_code : 7
                 * day_wind_power : 5
                 * day_wind_power_code : 2
                 * max_degree : 13
                 * min_degree : 0
                 * night_weather : 晴
                 * night_weather_code : 00
                 * night_weather_short : 晴
                 * night_wind_direction : 西北风
                 * night_wind_direction_code : 7
                 * night_wind_power : 4
                 * night_wind_power_code : 1
                 * time : 2021-12-29
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$3Bean {
                /**
                 * day_weather : 晴
                 * day_weather_code : 00
                 * day_weather_short : 晴
                 * day_wind_direction : 微风
                 * day_wind_direction_code : 0
                 * day_wind_power : 3
                 * day_wind_power_code : 0
                 * max_degree : 10
                 * min_degree : 0
                 * night_weather : 晴
                 * night_weather_code : 00
                 * night_weather_short : 晴
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2021-12-30
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$4Bean {
                /**
                 * day_weather : 多云
                 * day_weather_code : 01
                 * day_weather_short : 多云
                 * day_wind_direction : 东风
                 * day_wind_direction_code : 2
                 * day_wind_power : 5
                 * day_wind_power_code : 2
                 * max_degree : 9
                 * min_degree : 2
                 * night_weather : 多云
                 * night_weather_code : 01
                 * night_weather_short : 多云
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2021-12-31
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$5Bean {
                /**
                 * day_weather : 多云
                 * day_weather_code : 01
                 * day_weather_short : 多云
                 * day_wind_direction : 微风
                 * day_wind_direction_code : 0
                 * day_wind_power : 3
                 * day_wind_power_code : 0
                 * max_degree : 11
                 * min_degree : 2
                 * night_weather : 晴
                 * night_weather_code : 00
                 * night_weather_short : 晴
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2022-01-01
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$6Bean {
                /**
                 * day_weather : 晴
                 * day_weather_code : 00
                 * day_weather_short : 晴
                 * day_wind_direction : 东风
                 * day_wind_direction_code : 2
                 * day_wind_power : 4
                 * day_wind_power_code : 1
                 * max_degree : 12
                 * min_degree : 3
                 * night_weather : 多云
                 * night_weather_code : 01
                 * night_weather_short : 多云
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2022-01-02
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class _$7Bean {
                /**
                 * day_weather : 晴
                 * day_weather_code : 00
                 * day_weather_short : 晴
                 * day_wind_direction : 微风
                 * day_wind_direction_code : 0
                 * day_wind_power : 3
                 * day_wind_power_code : 0
                 * max_degree : 12
                 * min_degree : 0
                 * night_weather : 晴
                 * night_weather_code : 00
                 * night_weather_short : 晴
                 * night_wind_direction : 微风
                 * night_wind_direction_code : 0
                 * night_wind_power : 3
                 * night_wind_power_code : 0
                 * time : 2022-01-03
                 */

                private String day_weather;
                private String day_weather_code;
                private String day_weather_short;
                private String day_wind_direction;
                private String day_wind_direction_code;
                private String day_wind_power;
                private String day_wind_power_code;
                private String max_degree;
                private String min_degree;
                private String night_weather;
                private String night_weather_code;
                private String night_weather_short;
                private String night_wind_direction;
                private String night_wind_direction_code;
                private String night_wind_power;
                private String night_wind_power_code;
                private String time;

                public String getDay_weather() {
                    return day_weather;
                }

                public void setDay_weather(String day_weather) {
                    this.day_weather = day_weather;
                }

                public String getDay_weather_code() {
                    return day_weather_code;
                }

                public void setDay_weather_code(String day_weather_code) {
                    this.day_weather_code = day_weather_code;
                }

                public String getDay_weather_short() {
                    return day_weather_short;
                }

                public void setDay_weather_short(String day_weather_short) {
                    this.day_weather_short = day_weather_short;
                }

                public String getDay_wind_direction() {
                    return day_wind_direction;
                }

                public void setDay_wind_direction(String day_wind_direction) {
                    this.day_wind_direction = day_wind_direction;
                }

                public String getDay_wind_direction_code() {
                    return day_wind_direction_code;
                }

                public void setDay_wind_direction_code(String day_wind_direction_code) {
                    this.day_wind_direction_code = day_wind_direction_code;
                }

                public String getDay_wind_power() {
                    return day_wind_power;
                }

                public void setDay_wind_power(String day_wind_power) {
                    this.day_wind_power = day_wind_power;
                }

                public String getDay_wind_power_code() {
                    return day_wind_power_code;
                }

                public void setDay_wind_power_code(String day_wind_power_code) {
                    this.day_wind_power_code = day_wind_power_code;
                }

                public String getMax_degree() {
                    return max_degree;
                }

                public void setMax_degree(String max_degree) {
                    this.max_degree = max_degree;
                }

                public String getMin_degree() {
                    return min_degree;
                }

                public void setMin_degree(String min_degree) {
                    this.min_degree = min_degree;
                }

                public String getNight_weather() {
                    return night_weather;
                }

                public void setNight_weather(String night_weather) {
                    this.night_weather = night_weather;
                }

                public String getNight_weather_code() {
                    return night_weather_code;
                }

                public void setNight_weather_code(String night_weather_code) {
                    this.night_weather_code = night_weather_code;
                }

                public String getNight_weather_short() {
                    return night_weather_short;
                }

                public void setNight_weather_short(String night_weather_short) {
                    this.night_weather_short = night_weather_short;
                }

                public String getNight_wind_direction() {
                    return night_wind_direction;
                }

                public void setNight_wind_direction(String night_wind_direction) {
                    this.night_wind_direction = night_wind_direction;
                }

                public String getNight_wind_direction_code() {
                    return night_wind_direction_code;
                }

                public void setNight_wind_direction_code(String night_wind_direction_code) {
                    this.night_wind_direction_code = night_wind_direction_code;
                }

                public String getNight_wind_power() {
                    return night_wind_power;
                }

                public void setNight_wind_power(String night_wind_power) {
                    this.night_wind_power = night_wind_power;
                }

                public String getNight_wind_power_code() {
                    return night_wind_power_code;
                }

                public void setNight_wind_power_code(String night_wind_power_code) {
                    this.night_wind_power_code = night_wind_power_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }

        public static class IndexBean {
            /**
             * airconditioner : {"":"","detail":"您将感到很舒适，一般不需要开启空调。","info":"较少开启","name":"空调开启"}
             * allergy : {"detail":"","info":"极不易发","name":"过敏"}
             * carwash : {"detail":"","info":"适宜","name":"洗车"}
             * chill : {"detail":"","info":"冷","name":"风寒"}
             * clothes : {"detail":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。","info":"","name":"穿衣"}
             * cold : {"detail":"天冷，发生感冒机率较大，请注意适当增加衣服，加强自我防护避免感冒。","info":"易发","name":""}
             * comfort : {"detail":"白天天气阴沉，您会感觉偏冷，不很舒适，请注意添加衣物，以防感冒。","info":"","name":"舒适度"}
             * diffusion : {"detail":"","info":"较差","name":"空气污染扩散条件"}
             * dry : {"detail":"","info":"干燥","name":"路况"}
             * drying : {"detail":"","info":"不太适宜","name":"晾晒"}
             * fish : {"detail":"天气不好，有风，不适合垂钓。","info":"","name":"钓鱼"}
             * heatstroke : {"detail":"","info":"无中暑风险","name":"中暑"}
             * makeup : {"detail":"","info":"保湿","name":"化妆"}
             * mood : {"detail":"天气阴冷，感觉压抑，不妨给自己的心情放个假，把所有的烦恼丢之脑后，把所有的爱记在心上，整个人便会变得轻松而有力量。","info":"差","name":""}
             * morning : {"detail":"","info":"较适宜","name":"晨练"}
             * sports : {"detail":"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。","info":"","name":"运动"}
             * sunglasses : {"detail":"白天天空阴沉，但太阳辐射较强，建议佩戴透射比1级且标注UV380-UV400的浅色太阳镜","info":"","name":"太阳镜"}
             * sunscreen : {"":"","detail":"属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","info":"弱","name":"防晒"}
             * time : 20211228
             * tourism : {"":"","detail":"天气较好，气温稍低，会感觉稍微有点凉，不过也是个好天气哦。适宜旅游，可不要错过机会呦！","info":"适宜","name":"旅游"}
             * traffic : {"detail":"阴天，路面干燥，交通气象条件良好，车辆可以正常行驶。","info":"良好","name":""}
             * ultraviolet : {"detail":"弱","info":"最弱","name":"紫外线强度"}
             * umbrella : {"detail":"阴天，但降水概率很低，因此您在出门的时候无须带雨伞。","info":"不带伞","name":""}
             */

            private AirconditionerBean airconditioner;
            private AllergyBean allergy;
            private CarwashBean carwash;
            private ChillBean chill;
            private ClothesBean clothes;
            private ColdBean cold;
            private ComfortBean comfort;
            private DiffusionBean diffusion;
            private DryBean dry;
            private DryingBean drying;
            private FishBean fish;
            private HeatstrokeBean heatstroke;
            private MakeupBean makeup;
            private MoodBean mood;
            private MorningBean morning;
            private SportsBean sports;
            private SunglassesBean sunglasses;
            private SunscreenBean sunscreen;
            private String time;
            private TourismBean tourism;
            private TrafficBean traffic;
            private UltravioletBean ultraviolet;
            private UmbrellaBean umbrella;

            public AirconditionerBean getAirconditioner() {
                return airconditioner;
            }

            public void setAirconditioner(AirconditionerBean airconditioner) {
                this.airconditioner = airconditioner;
            }

            public AllergyBean getAllergy() {
                return allergy;
            }

            public void setAllergy(AllergyBean allergy) {
                this.allergy = allergy;
            }

            public CarwashBean getCarwash() {
                return carwash;
            }

            public void setCarwash(CarwashBean carwash) {
                this.carwash = carwash;
            }

            public ChillBean getChill() {
                return chill;
            }

            public void setChill(ChillBean chill) {
                this.chill = chill;
            }

            public ClothesBean getClothes() {
                return clothes;
            }

            public void setClothes(ClothesBean clothes) {
                this.clothes = clothes;
            }

            public ColdBean getCold() {
                return cold;
            }

            public void setCold(ColdBean cold) {
                this.cold = cold;
            }

            public ComfortBean getComfort() {
                return comfort;
            }

            public void setComfort(ComfortBean comfort) {
                this.comfort = comfort;
            }

            public DiffusionBean getDiffusion() {
                return diffusion;
            }

            public void setDiffusion(DiffusionBean diffusion) {
                this.diffusion = diffusion;
            }

            public DryBean getDry() {
                return dry;
            }

            public void setDry(DryBean dry) {
                this.dry = dry;
            }

            public DryingBean getDrying() {
                return drying;
            }

            public void setDrying(DryingBean drying) {
                this.drying = drying;
            }

            public FishBean getFish() {
                return fish;
            }

            public void setFish(FishBean fish) {
                this.fish = fish;
            }

            public HeatstrokeBean getHeatstroke() {
                return heatstroke;
            }

            public void setHeatstroke(HeatstrokeBean heatstroke) {
                this.heatstroke = heatstroke;
            }

            public MakeupBean getMakeup() {
                return makeup;
            }

            public void setMakeup(MakeupBean makeup) {
                this.makeup = makeup;
            }

            public MoodBean getMood() {
                return mood;
            }

            public void setMood(MoodBean mood) {
                this.mood = mood;
            }

            public MorningBean getMorning() {
                return morning;
            }

            public void setMorning(MorningBean morning) {
                this.morning = morning;
            }

            public SportsBean getSports() {
                return sports;
            }

            public void setSports(SportsBean sports) {
                this.sports = sports;
            }

            public SunglassesBean getSunglasses() {
                return sunglasses;
            }

            public void setSunglasses(SunglassesBean sunglasses) {
                this.sunglasses = sunglasses;
            }

            public SunscreenBean getSunscreen() {
                return sunscreen;
            }

            public void setSunscreen(SunscreenBean sunscreen) {
                this.sunscreen = sunscreen;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public TourismBean getTourism() {
                return tourism;
            }

            public void setTourism(TourismBean tourism) {
                this.tourism = tourism;
            }

            public TrafficBean getTraffic() {
                return traffic;
            }

            public void setTraffic(TrafficBean traffic) {
                this.traffic = traffic;
            }

            public UltravioletBean getUltraviolet() {
                return ultraviolet;
            }

            public void setUltraviolet(UltravioletBean ultraviolet) {
                this.ultraviolet = ultraviolet;
            }

            public UmbrellaBean getUmbrella() {
                return umbrella;
            }

            public void setUmbrella(UmbrellaBean umbrella) {
                this.umbrella = umbrella;
            }

            public static class AirconditionerBean {
                /**
                 *  :
                 * detail : 您将感到很舒适，一般不需要开启空调。
                 * info : 较少开启
                 * name : 空调开启
                 */

                @SerializedName("")
                private String _$44; // FIXME check this code
                private String detail;
                private String info;
                private String name;

                public String get_$44() {
                    return _$44;
                }

                public void set_$44(String _$44) {
                    this._$44 = _$44;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class AllergyBean {
                /**
                 * detail :
                 * info : 极不易发
                 * name : 过敏
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class CarwashBean {
                /**
                 * detail :
                 * info : 适宜
                 * name : 洗车
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class ChillBean {
                /**
                 * detail :
                 * info : 冷
                 * name : 风寒
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class ClothesBean {
                /**
                 * detail : 天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。
                 * info :
                 * name : 穿衣
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class ColdBean {
                /**
                 * detail : 天冷，发生感冒机率较大，请注意适当增加衣服，加强自我防护避免感冒。
                 * info : 易发
                 * name :
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class ComfortBean {
                /**
                 * detail : 白天天气阴沉，您会感觉偏冷，不很舒适，请注意添加衣物，以防感冒。
                 * info :
                 * name : 舒适度
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class DiffusionBean {
                /**
                 * detail :
                 * info : 较差
                 * name : 空气污染扩散条件
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class DryBean {
                /**
                 * detail :
                 * info : 干燥
                 * name : 路况
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class DryingBean {
                /**
                 * detail :
                 * info : 不太适宜
                 * name : 晾晒
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class FishBean {
                /**
                 * detail : 天气不好，有风，不适合垂钓。
                 * info :
                 * name : 钓鱼
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class HeatstrokeBean {
                /**
                 * detail :
                 * info : 无中暑风险
                 * name : 中暑
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class MakeupBean {
                /**
                 * detail :
                 * info : 保湿
                 * name : 化妆
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class MoodBean {
                /**
                 * detail : 天气阴冷，感觉压抑，不妨给自己的心情放个假，把所有的烦恼丢之脑后，把所有的爱记在心上，整个人便会变得轻松而有力量。
                 * info : 差
                 * name :
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class MorningBean {
                /**
                 * detail :
                 * info : 较适宜
                 * name : 晨练
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class SportsBean {
                /**
                 * detail : 天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。
                 * info :
                 * name : 运动
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class SunglassesBean {
                /**
                 * detail : 白天天空阴沉，但太阳辐射较强，建议佩戴透射比1级且标注UV380-UV400的浅色太阳镜
                 * info :
                 * name : 太阳镜
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class SunscreenBean {
                /**
                 *  :
                 * detail : 属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
                 * info : 弱
                 * name : 防晒
                 */

                @SerializedName("")
                private String _$7; // FIXME check this code
                private String detail;
                private String info;
                private String name;

                public String get_$7() {
                    return _$7;
                }

                public void set_$7(String _$7) {
                    this._$7 = _$7;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class TourismBean {
                /**
                 *  :
                 * detail : 天气较好，气温稍低，会感觉稍微有点凉，不过也是个好天气哦。适宜旅游，可不要错过机会呦！
                 * info : 适宜
                 * name : 旅游
                 */

                @SerializedName("")
                private String _$193; // FIXME check this code
                private String detail;
                private String info;
                private String name;

                public String get_$193() {
                    return _$193;
                }

                public void set_$193(String _$193) {
                    this._$193 = _$193;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class TrafficBean {
                /**
                 * detail : 阴天，路面干燥，交通气象条件良好，车辆可以正常行驶。
                 * info : 良好
                 * name :
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class UltravioletBean {
                /**
                 * detail : 弱
                 * info : 最弱
                 * name : 紫外线强度
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class UmbrellaBean {
                /**
                 * detail : 阴天，但降水概率很低，因此您在出门的时候无须带雨伞。
                 * info : 不带伞
                 * name :
                 */

                private String detail;
                private String info;
                private String name;

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class LimitBean {
            /**
             * tail_number : 2和8
             * time : 20211228
             */

            private String tail_number;
            private String time;

            public String getTail_number() {
                return tail_number;
            }

            public void setTail_number(String tail_number) {
                this.tail_number = tail_number;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TipsBean {
            /**
             * observe : {"0":"光芒透过云缝，洒向大地~","1":"天有点冷，注意保暖~"}
             */

            private ObserveBean observe;

            public ObserveBean getObserve() {
                return observe;
            }

            public void setObserve(ObserveBean observe) {
                this.observe = observe;
            }

            public static class ObserveBean {
                /**
                 * 0 : 光芒透过云缝，洒向大地~
                 * 1 : 天有点冷，注意保暖~
                 */

                @SerializedName("0")
                private String _$0;
                @SerializedName("1")
                private String _$1;

                public String get_$0() {
                    return _$0;
                }

                public void set_$0(String _$0) {
                    this._$0 = _$0;
                }

                public String get_$1() {
                    return _$1;
                }

                public void set_$1(String _$1) {
                    this._$1 = _$1;
                }
            }
        }
    }
}
