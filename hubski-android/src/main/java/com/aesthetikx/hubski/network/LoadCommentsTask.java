package com.aesthetikx.hubski.network;

import android.os.AsyncTask;

import com.aesthetikx.hubski.fragment.CommentsFragment;
import com.aesthetikx.hubski.model.Comment;
import com.aesthetikx.hubski.parse.CommentParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoadCommentsTask extends AsyncTask<URL, Void, Comment> {

    private CommentsFragment fragment;

    public LoadCommentsTask(CommentsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected Comment doInBackground(URL... params) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(params[0].toURI());
            HttpResponse response = httpClient.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            return CommentParser.parse(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Comment comment) {
        comment.print(0);
        fragment.postResults(comment);
    }
}
