package com.aesthetikx.hubski.network;

import android.os.AsyncTask;

import com.aesthetikx.hubski.model.Feed;

import com.aesthetikx.hubski.parse.FeedParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LoadFeedTask extends AsyncTask<URL, Void, Feed> {

    @Override
    protected Feed doInBackground(URL... params) {
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
            return FeedParser.parse(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
