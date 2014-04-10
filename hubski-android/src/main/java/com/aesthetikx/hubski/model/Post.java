package com.aesthetikx.hubski.model;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class Post implements Serializable {

    private String title;
    private String username;
    private List<String> tags;
    private boolean hasArticle;
    private int commentCount;
    private int shareCount;
    private URL commentsUrl;
    private URL articleUrl;

    /**
     * Constructor for a Hubski post without an article
     */
    public Post(String title, String username, List<String> tags, int commentCount, int shareCount, URL commentsUrl) {
        this.title = title;
        this.username = username;
        this.tags = tags;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.commentsUrl = commentsUrl;
        this.hasArticle = false;
    }

    /**
     * Constructor for a Hubski post with an article
     */
    public Post(String title, String username, List<String> tags, int commentCount, int shareCount, URL commentsUrl, URL articleUrl) {
        this.title = title;
        this.username = username;
        this.tags = tags;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.commentsUrl = commentsUrl;
        this.articleUrl = articleUrl;
        this.hasArticle = true;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean hasArticle() {
        return hasArticle;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public URL getCommentsUrl() {
        return commentsUrl;
    }

    public URL getArticleUrl() {
        return articleUrl;
    }

    @Override
    public String toString() {
        String ret = "Post: " + title + " (User " + username + ", " + commentCount + " comments, " + shareCount + " shares), tags[";
        ret += getTagsString();
        ret += "]";
        ret += ", comments: " + commentsUrl.toString();
        if (hasArticle()) {
            ret += ", article: " + articleUrl.toString();
        } else {
            ret += ", no article.";
        }
        return ret;
    }

    public String getTagsString() {
        String ret = "";
        for (int i = 0; i < tags.size(); i++) {
            ret += tags.get(i);
            if (i != tags.size() -1) ret += " ";
        }
        return ret;
    }
}
