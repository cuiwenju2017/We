package com.cwj.love_lhh.bean;

import java.util.List;

public class RubbishBean {

    /**
     * aim : {"goodsName":"西瓜","goodsType":"湿垃圾"}
     * recommendList : [{"goodsName":"西瓜皮","goodsType":"湿垃圾"},{"goodsName":"包裹着西瓜籽的纸巾","goodsType":"干垃圾"},{"goodsName":"西瓜籽","goodsType":"湿垃圾"},{"goodsName":"西瓜子","goodsType":"湿垃圾"}]
     */

    private AimBean aim;
    private List<RecommendListBean> recommendList;

    public AimBean getAim() {
        return aim;
    }

    public void setAim(AimBean aim) {
        this.aim = aim;
    }

    public List<RecommendListBean> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<RecommendListBean> recommendList) {
        this.recommendList = recommendList;
    }

    public static class AimBean {
        /**
         * goodsName : 西瓜
         * goodsType : 湿垃圾
         */

        private String goodsName;
        private String goodsType;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }
    }

    public static class RecommendListBean {
        /**
         * goodsName : 西瓜皮
         * goodsType : 湿垃圾
         */

        private String goodsName;
        private String goodsType;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }
    }
}
