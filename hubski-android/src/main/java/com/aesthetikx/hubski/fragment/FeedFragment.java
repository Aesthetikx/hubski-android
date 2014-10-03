package com.aesthetikx.hubski.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
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

import java.net.URL;
import java.util.List;

public class FeedFragment extends ListFragment {

    private PullToRefreshLayout mPullToRefreshLayout;
    private CustomListener mListener;
    private FeedListAdapter mAdapter;
    private List<String> data;

    public static FeedFragment newInstance(int type) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putInt("feed_type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mAdapter = new FeedListAdapter(getActivity().getBaseContext());
        setListAdapter(mAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewGroup viewGroup = (ViewGroup) view;

        mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

        mListener = new CustomListener();

        ActionBarPullToRefresh.from(getActivity())
                .insertLayoutInto(viewGroup)
                .theseChildrenArePullable(getListView(), getListView().getEmptyView())
                .listener(mListener)
                .setup(mPullToRefreshLayout);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post)mAdapter.getItem(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, CommentsFragment.newInstance(post))
                        .addToBackStack(null)
                        .commit();
            }
        });

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
        mAdapter.setFeed(feed);
        mPullToRefreshLayout.setRefreshComplete();
        System.out.println("notifying");
        mAdapter.notifyDataSetChanged();
        System.out.println("done");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //((MainActivity) activity).onSectionAttached(getArguments().getInt("feed_type"));
    }
}
