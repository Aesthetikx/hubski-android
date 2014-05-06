package com.aesthetikx.hubski.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.aesthetikx.hubski.R;

import java.net.URL;
import java.util.List;

public class RootComment extends Comment {

    protected String title;
    protected List<String> tags;

    public RootComment(String title, List<String> tags, String username, URL userLink, URL link, String body, String age, int score, List<Comment> children, int depth) {
        super(username, userLink, link, body, age, score, children, depth);
        this.title = title;
        this.tags = tags;
    }

    @Override
    public View getExpandedView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.root_comment, parent, false);

        TextView titleTextView = (TextView) view.findViewById(R.id.text_view_title);
        titleTextView.setText(title);

        TextView usernameTextView = (TextView) view.findViewById(R.id.text_view_username);
        usernameTextView.setText(getUsername());

        TextView ageTextView = (TextView) view.findViewById(R.id.text_view_age);
        ageTextView.setText(getAge());

        String tagString = "";
        for (String tag : tags) { tagString += tag + " "; }
        TextView tagsTextView = (TextView) view.findViewById(R.id.text_view_tags);
        tagsTextView.setText(tagString);

        TextView bodyTextView = (TextView) view.findViewById(R.id.text_view_body);
        bodyTextView.setText(getBody());

        return view;
    }

    @Override
    public void toggleExpanded() { }

}
