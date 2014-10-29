package com.aesthetikx.hubski.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.aesthetikx.hubski.adapter.FeedListAdapter;
import com.aesthetikx.hubski.MainActivity;
import com.aesthetikx.hubski.R;
import com.aesthetikx.hubski.model.Feed;
import com.aesthetikx.hubski.model.Post;
import com.aesthetikx.hubski.network.LoadFeedTask;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import uk.co.senab.actionbarpulltorefresh.library.viewdelegates.ScrollYDelegate;

import java.net.URL;
import java.util.List;

public class FeedFragment extends Fragment {

    private PullToRefreshLayout mPullToRefreshLayout;
    private FeedListAdapter mAdapter;
    private List<String> data;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    public static FeedFragment newInstance(int type) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putInt("feed_type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.ptr_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(new CustomListener())
                .useViewDelegate(RecyclerView.class, new ScrollYDelegate())
                .setup(mPullToRefreshLayout);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            int type = getArguments().getInt("feed_type");
            if (type == MainActivity.FRAGMENT_GLOBAL) {
                new LoadFeedTask(FeedFragment.this).execute(new URL("http://www.hubski.com/global?id="));
            } else {
                new LoadFeedTask(FeedFragment.this).execute(new URL("http://www.hubski.com/"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CustomListener implements OnRefreshListener {
        @Override
        public void onRefreshStarted(View view) {
            try {
                new LoadFeedTask(FeedFragment.this).execute(new URL("http://www.hubski.com/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void postResults(Feed feed) {
        System.out.println("Got feed of size " + feed.getPosts().size());
        mAdapter = new FeedListAdapter(feed, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = (Post) view.getTag();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, CommentsFragment.newInstance(post))
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(mAdapter);
        mPullToRefreshLayout.setRefreshComplete();
    }
}
