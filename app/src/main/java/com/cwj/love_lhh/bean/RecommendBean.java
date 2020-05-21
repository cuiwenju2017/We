package com.cwj.love_lhh.bean;

import java.util.List;

public class RecommendBean {

    /**
     * code : 1
     * msg : 数据返回成功！
     * data : [{"content":"好浓的恨意，不过我喜欢！","author":""},{"content":"终有一天你会知道：公交分钟一班，地铁分钟一班，我们的爱\u2014\u2014一辈子只有这一班！","author":""},{"content":"静坐常思己过，闲谈莫论人非。","author":"漫柳如烟"},{"content":"生活从来都不缺问题，重要的是解决问题的决心，相信自己，你能作茧自缚，就能破茧成蝶！","author":""},{"content":"大笨蛋，知道吗，没有你。我的生活根本没有幸福可言\u2026\u2026","author":""},{"content":"不要把自己看得太强，以致无视外因的成就；不要把自己看的太轻，以致成为他人的踏板。","author":"shmily"},{"content":"都说人生如戏，戏可以重来，人生，却难再续。","author":"寂寞的泪"},{"content":"你对谁有爱意，你就对谁无能为力。","author":""},{"content":"爱得不够，才借口多多。","author":"VILIN"},{"content":"你一直为别人做好事，他们也并不在意。但你一旦犯个错，那他们就怎么都忘不了。","author":""},{"content":"愚人节、谁在以假乱真、以真乱假。有人高兴有人愁。","author":"汀籹梓"},{"content":"He misses her, but he missed her.  错过只在一瞬，思念却是一世。","author":"Rain"},{"content":"那些年，我们一起走过的路，你还记得吗？","author":""},{"content":"你不过是皇上身边的太监，装什么鸟样！！！","author":""},{"content":"(我们就像两只可爱的猪走在爱情大道上，淋浴着爱情的阳光。","author":""},{"content":"两人之间，有一点牵挂，却不会纠缠；两人之间，有一点想念，却不会伤心。","author":""},{"content":"朴树常年低调，但是出的歌都是好歌，去听听他以前的歌，都很好听，都不是那些简简单单的爱来爱去","author":"平凡之路"},{"content":"呐，现在天空要放晴了哟！","author":"グランドエスケープ (Movie edit)"},{"content":"我是一名国家铁路普铁工作者，我在乌局的百里风区上班，有时候真的很羡慕高铁的人啊，鲜花和掌声都是他们的，而我们面对的只有风沙和油污，其实想想，高铁沐浴阳光，普铁在暗处承载着国家经济命脉，有根有花才完美","author":"我和我的祖国"},{"content":"做你自己，我来爱你。","author":""}]
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
         * content : 好浓的恨意，不过我喜欢！
         * author :
         */

        private String content;
        private String author;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
