package com.cwj.we.bean;

import java.util.List;

public class ProvinceBean {

    /**
     * name : 北京市
     * city : [{"name":"北京市","area":["东城区","西城区","海淀区","朝阳区","丰台区","石景山区","门头沟区","通州区","顺义区","房山区","大兴区","昌平区","怀柔区","平谷区","密云区","延庆区"]}]
     */

    private String name;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * name : 北京市
         * area : ["东城区","西城区","海淀区","朝阳区","丰台区","石景山区","门头沟区","通州区","顺义区","房山区","大兴区","昌平区","怀柔区","平谷区","密云区","延庆区"]
         */

        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
