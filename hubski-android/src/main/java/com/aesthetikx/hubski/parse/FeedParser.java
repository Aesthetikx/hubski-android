package com.aesthetikx.hubski.parse;

import com.aesthetikx.hubski.model.Feed;
import com.aesthetikx.hubski.model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedParser {

    public static Feed parse(String html) {
        Document doc = Jsoup.parse(html);
        Elements unitElements = doc.select("div#grid > div#unit.box");

        List<Post> posts = new ArrayList<>();

        for (Element unit: unitElements) {
            Element plusMinus = unit.select("div.plusminus").get(0);
            Element postContent = unit.select("div.postcontent").get(0);

            String title = getTitle(postContent);
            String username = getUsername(postContent);
            List<String> tags = getTags(postContent);
            int commentCount = getCommentCount(postContent);
            int shareCount = getScore(plusMinus);
            URL commentsUrl = getCommentsUrl(postContent);
            URL articleUrl = getArticleUrl(postContent);

            Post p;
            if (articleUrl == null) {
                p = new Post(title, username, tags, commentCount, shareCount, commentsUrl);
            } else {
                p = new Post(title, username, tags, commentCount, shareCount, commentsUrl, articleUrl);
            }
            // System.out.println(p.toString());
            posts.add(p);
        }

        return new Feed(posts);
    }

    private static int getScore(Element plusMinus) {
        try {
            return Integer.parseInt(plusMinus.select("span.score > a > img").get(0).attr("class").substring(0,1));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static String getTitle(Element postContent) {
        try {
            return postContent.select("div.feedtitle > span > a").get(0).text();
        } catch (Exception e) {
            e.printStackTrace();
            return "notitle";
        }
    }

    private static String getUsername(Element postContent) {
        try {
            return postContent.select("div.titlelinks > span#username").get(0).text();
        } catch (Exception e) {
            e.printStackTrace();
            return "nouser";
        }
    }

    private static List<String> getTags(Element postContent) {
        List<String> tags = new ArrayList<>();
        try {
            Elements tagElements = postContent.select("div.subtitle > span a");
            for (Element tag: tagElements) {
                tags.add(tag.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    private static int getCommentCount(Element postContent) {
        try {
            return Integer.parseInt(postContent.select("span.feedcombub > a").text());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static URL getCommentsUrl(Element postContent) {
        try {
            return new URL("http://www.hubski.com/" + postContent.select("div.feedtitle > span > a").get(0).attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static URL getArticleUrl(Element postContent) {
        try {
            String onclick = postContent.select("div.feedtitle > span > a").get(0).attr("onclick");
            if (onclick.isEmpty()) return null;

            Pattern pattern = Pattern.compile("window\\.open\\('(.*)'\\);");
            Matcher matcher = pattern.matcher(onclick);
            if (matcher.find()) {
                return new URL(matcher.group(1));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
