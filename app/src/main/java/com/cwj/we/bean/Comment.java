package com.cwj.we.bean;

import cn.bmob.v3.BmobObject;

/**
 * 评论
 */
public class Comment extends BmobObject {

    private String content;//评论内容
    private User user;//评论的用户
    private Post post;//所评论的帖子

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
