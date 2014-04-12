package com.aesthetikx.hubski;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.aesthetikx.hubski.model.Feed;
import com.aesthetikx.hubski.model.Post;

public class FeedListAdapter extends BaseAdapter implements ListAdapter {

    private Feed feed;
    private Context context;

    public FeedListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return (feed == null) ? 0 : feed.getPosts().size();
    }

    @Override
    public Object getItem(int position) {
        return feed.getPosts().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.feed_list_item, parent, false);
        }else{
            view = convertView;
        }
        TextView title = (TextView)view.findViewById(R.id.text_view_title);
        TextView username = (TextView)view.findViewById(R.id.text_view_username);
        TextView score = (TextView)view.findViewById(R.id.text_view_score);
        TextView tags = (TextView)view.findViewById(R.id.text_view_tags);

        Post post = feed.getPosts().get(position);
        title.setText(post.getTitle());
        username.setText(post.getUsername());
        score.setText(post.getShareCount() + "");
        tags.setText(post.getTagsString());
        return view;
    }

    @Override
    public boolean isEmpty() {
        return feed.getPosts().isEmpty();
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
