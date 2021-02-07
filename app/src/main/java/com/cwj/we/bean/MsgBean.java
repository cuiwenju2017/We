package com.cwj.we.bean;

public class MsgBean {

    /**
     * result : 0
     * content : 杭州天气 ：当前温度18℃，感冒易发期，外出请适当调整衣物，注意补充水分。{br}[02月07日] ：多云，低温 8℃，高温 18℃，风力1级{br}[02月08日] ：小雨，低温 3℃，高温 14℃，风力3级{br}[02月09日] ：多云，低温 7℃，高温 12℃，风力2级{br}[02月10日] ：小雨，低温 8℃，高温 12℃，风力1级{br}[02月11日] ：小雨，低温 9℃，高温 14℃，风力2级
     */

    private int result;
    private String content;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
