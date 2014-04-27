package com.aesthetikx.hubski.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.ListView;
import com.aesthetikx.hubski.R;
import com.aesthetikx.hubski.adapter.CommentListAdapter;
import com.aesthetikx.hubski.adapter.TreeItemClickListener;
import com.aesthetikx.hubski.model.Comment;
import com.aesthetikx.hubski.model.Post;
import com.aesthetikx.hubski.network.LoadCommentsTask;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {

    private Post post;
    private CommentListAdapter adapter;
    private ListView list;

    public static CommentsFragment newInstance(Post post) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable("post", post);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list = (ListView) inflater.inflate(R.layout.fragment_comments, container, false);
        adapter = new CommentListAdapter(getActivity().getBaseContext());
        list.setAdapter(adapter);
        return list;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadCommentsTask(CommentsFragment.this).execute(post.getCommentsUrl());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (post.hasArticle()) {
            inflater.inflate(R.menu.comments, menu);
        } else {
            inflater.inflate(R.menu.comments_no_article, menu);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.post = (Post) getArguments().getSerializable("post");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_article:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, ArticleFragment.newInstance(post))
                        //.addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void flattenComment(Comment comment, List<Comment> comments) {
        comments.add(comment);
        for (Comment child: comment.getChildren()) {
            flattenComment(child, comments);
        }
    }

    public void postResults(Comment rootComment) {
        List<Comment> comments = new ArrayList<>();
        flattenComment(rootComment, comments);
        adapter.setComments(comments);
        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(new TreeItemClickListener(comments, adapter));
    }
}

