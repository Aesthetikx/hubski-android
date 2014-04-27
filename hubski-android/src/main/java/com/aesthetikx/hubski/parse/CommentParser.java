package com.aesthetikx.hubski.parse;

import com.aesthetikx.hubski.model.Comment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentParser {

    public static Comment parse(String html) throws MalformedURLException {
        URL ul = new URL("http://www.google.com");
        URL l = new URL("http://www.google.com");

        List<Comment> rootChildren = new ArrayList<>();
        for (int x = 0; x < 2; x++) {
            List<Comment> xChildren = new ArrayList<>();
            for (int y = 0; y < 2; y++) {
                xChildren.add(new Comment("x" + x + "y" + y, ul, l, "body", "age", 1, new ArrayList<Comment>(), 2));
            }
            rootChildren.add(new Comment("x" + x, ul, l, "body", "age", 1, xChildren, 1));
        }
        Comment rootComment = new Comment("cat", ul, l, "body", "age", 1, rootChildren, 0);
        return rootComment;
        /*
        Document doc = Jsoup.parse(html);

        List<Comment> rootChildren = new ArrayList<>();

        Elements outercomms = doc.select("div.whole > div > div.outercomm");
        Elements subcoms = doc.select("div.whole > div > div.subcom");
        for (int i = 0; i < outercomms.size(); i++) {
            Element outercomm = outercomms.get(i);
            Element subcom = subcoms.get(i);
            rootChildren.add(getComment(outercomm, subcom));
        }

        return getRootComment(doc, rootChildren);
        */
    }

    private static Comment getRootComment(Document doc, List<Comment> children) {
        URL userLink = null;
        URL link = null;
        try {
            userLink = new URL("http://www.TODO.com");
            link = new URL("http://www.TODO.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = "Root Comment";
        String age = "50 years";
        int score = 5;
        return new Comment("cat", userLink, link, body, age, score, children, 0);
    }

    private static Comment getComment(Element outercomm, Element subcom) {
        System.out.println("getcomment called");
        int score = Integer.parseInt(outercomm.select("div.plusminus > span.score a > img")
                .get(0).attr("class").substring(0, 1));
        Element userElement = outercomm.select("div > span.subhead > span#username > a").get(0);
        String username = userElement.text();
        URL userLink = null;
        try {
            userLink = new URL("http://www.hubski.com/" + userElement.attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        URL link = null;
        try {
            link = new URL("http://www.hubski.com/" + outercomm.select("div > span.subhead > a").attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = outercomm.select("div > div#comtext.comm").text();
        String age = "TODO";

        List<Comment> children = parseSubcom(subcom);
        System.out.println("getcomment returning");
        return new Comment(username, userLink, link, body, age, score, children, 0);
    }

    private static List<Comment> parseSubcom(Element parentsubcom) {
        System.out.println("Parse subcom called");
        Elements outercomms = parentsubcom.select("div.subsubcom > div.outercomm");
        Elements subcoms = parentsubcom.select("div.subsubcom > div.subcom");

        List<Comment> children = new ArrayList<>();

        for (int i = 0; i < outercomms.size(); i++) {
            Element outercomm = outercomms.get(i);
            Element subcom = subcoms.get(i);
            children.add(getComment(outercomm, subcom));
        }

        System.out.println("Parse subcom returning with " + children.size() + " children");
        return children;
    }
}
