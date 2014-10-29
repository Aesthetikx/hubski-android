package com.aesthetikx.hubski.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.aesthetikx.hubski.R;
import com.aesthetikx.hubski.model.Feed;
import com.aesthetikx.hubski.model.Post;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    private Feed feed;
    private OnClickListener clickListener;

    public FeedListAdapter(Feed feed, OnClickListener clickListener) {
        this.feed = feed;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
         view.setOnClickListener(clickListener);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Post post = feed.getPosts().get(position);

        holder.itemView.setTag(post);

        holder.title.setText(post.getTitle());
        holder.username.setText(post.getUsername());
        holder.score.setText(post.getShareCount() + "");
        holder.tags.setText(post.getTagsString());
    }

    @Override
    public int getItemCount() {
        return (feed == null) ? 0 : feed.getPosts().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView username;
        public TextView score;
        public TextView tags;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.text_view_title);
            username = (TextView)view.findViewById(R.id.text_view_username);
            score = (TextView)view.findViewById(R.id.text_view_score);
            tags = (TextView)view.findViewById(R.id.text_view_tags);
        }
    }
}
