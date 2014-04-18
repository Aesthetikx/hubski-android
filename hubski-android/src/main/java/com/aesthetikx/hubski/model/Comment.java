package com.aesthetikx.hubski.model;

import com.aesthetikx.hubski.adapter.BaseTreeListItem;
import com.aesthetikx.hubski.adapter.TreeListItem;

import java.net.URL;
import java.util.List;

public class Comment extends BaseTreeListItem implements TreeListItem {

    private String username;
    private URL userLink;
    private URL link;
    private String body;
    private String age;
    private int score;
    private List<Comment> children;

    public Comment(String username, URL userLink, URL link, String body, String age, int score, List<Comment> children, int depth) {
        super(children, true, true, depth);
        this.username = username;
        this.userLink = userLink;
        this.link = link;
        this.body = body;
        this.age = age;
        this.score = score;
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

    public List<Comment> getChildren() {
        return children;
    }

    public void print(int depth) {
        String tab = "";
        for (int i = 0; i < depth; ++i) tab += "\t";
        System.out.println(tab + getUsername());
        for (Comment child: getChildren()) {
            child.print(depth + 1);
        }
    }

}
