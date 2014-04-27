package com.aesthetikx.hubski.adapter;

import android.content.Context;
import android.graphics.Color;
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
        Comment comment = (Comment) getItem(position);//comments.get(position);
        System.out.println(position + "-" + comment.getUsername() + " -v " + comment.isVisible() + " -e " + comment.isExpanded());
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.comment_list_item, parent, false);
        TextView tv = (TextView) view.findViewById(R.id.text_view_username);
        tv.setText(comment.getUsername());
        FrameLayout spacer = (FrameLayout) view.findViewById(R.id.spacer);
        spacer.setLayoutParams(new RelativeLayout.LayoutParams(40 * comment.getDepth(), RelativeLayout.LayoutParams.MATCH_PARENT));
        WebView webview = (WebView) view.findViewById(R.id.web_view);
        webview.loadDataWithBaseURL("https://hubski.com", comment.getBody(), "text/html", "UTF-8", "");
        View colorbar = view.findViewById(R.id.color_bar);
        colorbar.setBackgroundColor(Color.rgb(0,255,255));
        view.setVisibility(comment.isVisible() ? View.VISIBLE : View.GONE);
        return view;
    }
}
