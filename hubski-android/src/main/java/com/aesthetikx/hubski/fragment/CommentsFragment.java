package com.aesthetikx.hubski.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
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
        /*
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Comments for " + post.getTitle());
        return rootView;
        */
        WebView webview = prepareWebView();
        webview.loadUrl(post.getCommentsUrl().toString());
        return webview;
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

    private WebView prepareWebView() {
        WebView webview = new WebView(getActivity().getApplicationContext());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //TODO handle progress
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

        return webview;
    }
}

