package com.cwj.love_lhh.bean;

import java.util.List;

public class JokesBean {

    /**
     * page : 1
     * totalCount : 81992
     * totalPage : 8200
     * limit : 10
     * list : [{"content":"卤煮小少妇一枚。今天早上老公很严肃的突然对我说，老婆，我要告诉你一个秘密。小时候我的手很小，我就好担心我以后要是找个大胸的老婆，一只手都抓不过来，那得多心酸吖！现在才发现，当初的我真是想太多了。。。多了。。。你过来！我保证不打死你！！！&nbsp;","updateTime":"2020-05-20 22:59:22"},{"content":"天忽冷忽热，屁股着凉了，痔疮又犯了，打算今天去看医生。谁知道一早晨，丈母娘来电话了：\u201c今天不光要你媳妇过来，你也要过来呀！我准备炒几个菜，全家团聚一下。\u201d我不好意思说：\u201c妈！我有点事，不能去！\u201d丈母娘生气了：\u201c屁事？\u201d我当时没有反应过来：\u201c妈，你都知道了！\u201d","updateTime":"2020-05-20 22:59:22"},{"content":"我发烧，裹着两个大被子躺沙发上，正昏昏欲睡时，小侄子端着我的杯子小心翼翼走过来了，心里一阵暖流，没白疼这小子啊。侄子把杯子递给我：\u201c姑姑，电视上说童子尿治百病，你快喝了吧，喝完就好了。\u201d","updateTime":"2020-05-20 22:59:21"},{"content":"单位这几天忙，我一同事好久沒去练车一起练车的奇葩妹子大早打来电话\u201c张哥，这几天怎么不见你来练车？\u201d\u201c妹子，想哥啦？\u201d\u201c你想哪去了，这几天你沒来，教练老盯我一人骂，顶不住了。\u201d","updateTime":"2020-05-20 22:59:20"},{"content":"网购了一件衣服，邮过来居然兜里还揣着五千块钱！这把我气得！果断给了差评：\u201c居然敢卖我二手衣服，赶紧主动联系我，我把钱给你转过去\u2026\u2026\u201d","updateTime":"2020-05-20 22:59:19"},{"content":"我朋友自幼习武，武校出来被骗入传销。他口才不佳，零业绩，又特能吃，赶又赶不走，因为里面没人打得过他。几个月后，别人都瘦了，他壮了\u2026一天深夜整个组 织趁他熟睡后，集 体 秘 密撤 离，留下他孤伶伶一个人\u2026\u2026","updateTime":"2020-05-20 22:59:18"},{"content":"附近有个大妈每天挑一桶豆腐脑来卖，又便宜又好吃，大伙看到她来就会围上去。今天她说：\u201c老公惹我生气了，以后不卖给男人！\u201d然后好多大叔去找她老公，让他向大妈道歉，不然以后孤立他不和他下棋。","updateTime":"2020-05-20 22:59:18"},{"content":"开窗户不小心把花盆碰了下去，正巧砸在一辆豪车上面。我马上蹲下，故装镇定，就当什么事也没有发生。楼下开豪车那男的大声喊道：\u201c敢做不敢当是不是？你要是有点良心，就伸个脑袋出来，看一下我的车都被砸成啥样了\u2026\u2026\u201d我颤颤巍巍的把头伸了出去，说道：\u201c喊什么呀！我\u2026\u2026我赔你\u2026\u2026\u201d然后我这一赔，就是一辈子\u2026\u2026","updateTime":"2020-05-20 22:59:17"},{"content":"我妈原来想生女儿，结果我是男孩。老妈就一直把我当女儿养，洗衣做饭织毛衣什么都教。我假期回来，正切菜。我妈突然对我说：\u201c以后经期不能碰凉水。\u201c我条件反射的回答了：\u201c恩\u201c。老妈什么时候把我嫁出去啊？","updateTime":"2020-05-20 22:59:16"},{"content":"昨天闲着无聊，就把同事两人笔记本上的无线鼠标USB头交换到对方机子上插好，然后闪人就去吃饭了！一个小时后回来，两个家伙还在摆弄电脑，不停换USB口，插拔鼠标，重装驱动，反复重启电脑，哈哈哈！你们猜后来怎么着？我被打了！真是一个悲伤的故事。","updateTime":"2020-05-20 22:59:15"}]
     */

    private int page;
    private int totalCount;
    private int totalPage;
    private int limit;
    private List<ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * content : 卤煮小少妇一枚。今天早上老公很严肃的突然对我说，老婆，我要告诉你一个秘密。小时候我的手很小，我就好担心我以后要是找个大胸的老婆，一只手都抓不过来，那得多心酸吖！现在才发现，当初的我真是想太多了。。。多了。。。你过来！我保证不打死你！！！&nbsp;
         * updateTime : 2020-05-20 22:59:22
         */

        private String content;
        private String updateTime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
