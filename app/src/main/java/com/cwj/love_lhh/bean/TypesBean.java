package com.cwj.love_lhh.bean;

import java.util.List;

public class TypesBean {

    /**
     * code : 1
     * msg : 数据返回成功！
     * data : [{"typeId":509,"typeName":"财经"},{"typeId":510,"typeName":"科技"},{"typeId":511,"typeName":"军事"},{"typeId":512,"typeName":"时尚"},{"typeId":513,"typeName":"NBA"},{"typeId":514,"typeName":"股票"},{"typeId":515,"typeName":"游戏"},{"typeId":516,"typeName":"健康"},{"typeId":517,"typeName":"知否"},{"typeId":518,"typeName":"要闻"},{"typeId":519,"typeName":"体育"},{"typeId":520,"typeName":"娱乐"},{"typeId":521,"typeName":"头条"},{"typeId":522,"typeName":"视频"},{"typeId":525,"typeName":"热点"},{"typeId":526,"typeName":"小视频"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * typeId : 509
         * typeName : 财经
         */

        private int typeId;
        private String typeName;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
