package com.aesthetikx.hubski.model;

import java.net.URL;
import java.util.List;

public class Comment {

    private String username;
    private URL userLink;
    private URL link;
    private String body;
    private String age;
    private int score;
    private Comment parent;
    private List<Comment> children;

    public Comment(String username, URL userLink, URL link, String body, String age, int score,
                   Comment parent, List<Comment> children) {
        this.username = username;
        this.userLink = userLink;
        this.link = link;
        this.body = body;
        this.age = age;
        this.score = score;
        this.parent = parent;
        this.children = children;
    }

    public String getUsername() {
        return username;
    }

    public URL getUserLink() {
        return userLink;
    }

    public URL getLink() {
        return link;
    }

    public String getBody() {
        return body;
    }

    public String getAge() {
        return age;
    }

    public int getScore() {
        return score;
    }

    public Comment getParent() {
        return parent;
    }

    public List<Comment> getChildren() {
        return children;
    }

}
