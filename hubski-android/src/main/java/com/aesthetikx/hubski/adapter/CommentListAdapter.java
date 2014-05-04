package com.aesthetikx.hubski.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aesthetikx.hubski.R;
import com.aesthetikx.hubski.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {

    private List<Comment> comments = new ArrayList<Comment>();
    private Context context;

    public CommentListAdapter(Context context) {
        this.context = context;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (Comment c: comments) {
            if (c.isVisible()) ++count;
        }
        System.out.println("Count visible:" + count);
        return count;
    }

    @Override
    public Object getItem(int position) {
        int i = 0;
        while (position > 0) {
            --position;
            ++i;
            while (!comments.get(i).isVisible()) ++i;
        }
        return comments.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = (Comment) getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        if (comment.isExpanded()) {
            view = comment.getExpandedView(inflater, parent);
        } else {
            view = comment.getCollapsedView(inflater, parent);
        }

        view.setVisibility(comment.isVisible() ? View.VISIBLE : View.GONE);

        return view;
    }
}
