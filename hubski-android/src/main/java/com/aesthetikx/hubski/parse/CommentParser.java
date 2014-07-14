package com.aesthetikx.hubski.parse;

import com.aesthetikx.hubski.model.Comment;
import com.aesthetikx.hubski.model.RootComment;
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
        Document doc = Jsoup.parse(html);

        List<Comment> rootChildren = new ArrayList<>();

        Elements outercomms = doc.select("div.whole > div > div.outercomm");
        Elements subcoms = doc.select("div.whole > div > div.subcom");
        for (int i = 0; i < outercomms.size(); i++) {
            Element outercomm = outercomms.get(i);
            Element subcom = subcoms.get(i);
            rootChildren.add(getComment(outercomm, subcom, 1));
        }

        return getRootComment(doc, rootChildren);
    }

    private static Comment getRootComment(Document doc, List<Comment> children) {
        /* Username */
        String username = doc.select("div.whole > div > div.sub > div.postcontent > div.subtitle > div.shareline > span#username > a").first().text();

        /* URLS */
        URL userLink = null;
        URL link = null;
        try {
            userLink = new URL("http://www.hubski.com/user?id=" + username);
            link = new URL("http://www.TODO.com");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Body */
        String body;
        try {
            body = doc.select("div.whole > div > div.wholepub > div.pubcontent > div.pubtext").first().text();
        } catch (Exception e) {
            body = "";
        }

        /* Age */
        String age = "(unknown age)";
        try {
            age = doc.select("div.whole > div > div.sub > div.postcontent > div.subtitle > div.shareline").first().html().split("</span>&nbsp;")[1].split("&nbsp")[0].trim();
        } catch (Exception e) { }

        /* Score */
        int score = 5;

        /* Tags */
        List<String> tags = new ArrayList<>();
        Elements tagElements = doc.select("div.subtitle span > a[href~=(tag*)]");
        for (Element tagElement : tagElements) {
            tags.add(tagElement.text());
        }

        /* Title */
        String title = doc.select("div.title > span > a").first().text();

        return new RootComment(title, tags, username, userLink, link, body, age, score, children, 0);
    }

    private static Comment getComment(Element outerComm, Element subCom, int depth) {
        int score = Integer.parseInt(outerComm.select("div.plusminus > span.score a > img").get(0).attr("class").substring(0, 1));

        Element userElement = null; 
        String userName = "(unkown username)";
        URL userLink = null;
        try{
            userElement = outerComm.select("div > div.subhead > span#username > a").get(0);
            userName = userElement.text();
            userLink = new URL("http://www.hubski.com/" + userElement.attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        URL link = null;
        try {
            link = new URL("http://www.hubski.com/" + outerComm.select("div > div.subhead > a").attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String body = outerComm.select("div > div#comtext.comm").text();
        String age = "(unknown age)";
        try {
            age = outerComm.select("div > div.subhead").first().html().split("</span>")[1].split("&nbsp")[0].trim();
        } catch (Exception e) { }

        Element subSubCom = subCom.select("div.subsubcom").first();
        List<Comment> children = parseSubSubCom(subSubCom, depth + 1);
        return new Comment(userName, userLink, link, body, age, score, children, depth);
    }

    private static List<Comment> parseSubSubCom(Element parentSubSubCom, int depth) {
        Elements outerComms = parentSubSubCom.select("> div.outercomm");
        Elements subComs = parentSubSubCom.select("> div.subcom");

        List<Comment> children = new ArrayList<>();

        for (int i = 0; i < outerComms.size(); i++) {
            Element outerComm = outerComms.get(i);
            Element subCom = subComs.get(i);
            children.add(getComment(outerComm, subCom, depth));
        }

        return children;
    }
}
