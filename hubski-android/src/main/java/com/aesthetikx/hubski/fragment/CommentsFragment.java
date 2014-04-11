package com.aesthetikx.hubski.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.TextView;
import com.aesthetikx.hubski.R;
import com.aesthetikx.hubski.model.Post;

public class CommentsFragment extends Fragment {

    private Post post;

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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Comments for " + post.getTitle());
        return rootView;
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
                manager.beginTransaction().replace(R.id.container, ArticleFragment.newInstance(post)).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

