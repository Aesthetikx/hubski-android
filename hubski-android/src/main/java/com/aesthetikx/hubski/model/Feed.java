package com.aesthetikx.hubski.model;

import java.util.List;

public class Feed {

    private List<Post> posts;

    public Feed(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
