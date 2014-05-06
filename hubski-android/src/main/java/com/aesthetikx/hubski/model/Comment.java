package com.aesthetikx.hubski.model;

import android.graphics.Color;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aesthetikx.hubski.R;
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

    private int barColors[] = {
            Color.rgb(68, 193, 103),
            Color.rgb(156, 208, 113),
            Color.rgb(60, 180, 132),
            Color.rgb(133, 214, 179),
            Color.rgb(59, 158, 176)
    };

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

    public void print() {
        String tab = "";
        for (int i = 0; i < getDepth(); ++i) tab += "\t";
        System.out.println(tab + getUsername());
        for (Comment child: getChildren()) {
            child.print();
        }
    }

    private int getBarColor() {
        int index = getDepth();
        index %= barColors.length;
        return barColors[index];
    }

    @Override
    public View getExpandedView(LayoutInflater inflater, ViewGroup parent) {
        View view;
        view = inflater.inflate(R.layout.comment_list_item, parent, false);

        TextView textViewUsername = (TextView) view.findViewById(R.id.text_view_username);
        textViewUsername.setText(getUsername());

        TextView textViewAge = (TextView) view.findViewById(R.id.text_view_age);
        textViewAge.setText(getAge());

        int fiveDpi = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, inflater.getContext().getResources().getDisplayMetrics());

        FrameLayout spacer = (FrameLayout) view.findViewById(R.id.spacer);
        spacer.setLayoutParams(new RelativeLayout.LayoutParams(fiveDpi * getDepth(), RelativeLayout.LayoutParams.MATCH_PARENT));

        TextView textViewBody = (TextView) view.findViewById(R.id.text_view_body);
        textViewBody.setText(Html.fromHtml(getBody()));

        View colorbar = view.findViewById(R.id.color_bar);
        colorbar.setBackgroundColor(getBarColor());
        return view;
    }

    @Override
    public View getCollapsedView(LayoutInflater inflater, ViewGroup parent) {
        View view;
        view = inflater.inflate(R.layout.comment_list_item_collapsed, parent, false);

        TextView tv = (TextView) view.findViewById(R.id.text_view_hidden_count);
        int hiddenCount = getChildCount() + 1;
        if (hiddenCount == 1) {
            tv.setText(hiddenCount + " comment hidden");
        } else {
            tv.setText(hiddenCount + " comments hidden");
        }

        int fiveDpi = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, inflater.getContext().getResources().getDisplayMetrics());

        FrameLayout spacer = (FrameLayout) view.findViewById(R.id.spacer);
        spacer.setLayoutParams(new RelativeLayout.LayoutParams(fiveDpi * getDepth(), RelativeLayout.LayoutParams.MATCH_PARENT));

        View colorbar = view.findViewById(R.id.color_bar);
        colorbar.setBackgroundColor(getBarColor());
        return view;
    }
}
