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
        String username = doc.select("div.whole > div > div.sub > div.postcontent > div.subtitle > div.shareline > span#username > a").first().text();
        URL userLink = null;
        URL link = null;
        try {
            userLink = new URL("http://www.hubski.com/user?id=" + username);
            link = new URL("http://www.TODO.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = doc.select("div.whole > div > div.wholepub > div.pubcontent > div.pubtext").first().text();
        String age = "50 years";
        int score = 5;
        return new Comment(username, userLink, link, body, age, score, children, 0);
    }

    private static Comment getComment(Element outerComm, Element subCom, int depth) {
        int score = Integer.parseInt(outerComm.select("div.plusminus > span.score a > img")
                .get(0).attr("class").substring(0, 1));
        Element userElement = outerComm.select("div > span.subhead > span#username > a").get(0);
        String username = userElement.text();
        URL userLink = null;
        try {
            userLink = new URL("http://www.hubski.com/" + userElement.attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        URL link = null;
        try {
            link = new URL("http://www.hubski.com/" + outerComm.select("div > span.subhead > a").attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = outerComm.select("div > div#comtext.comm").text();
        String age = "TODO";

        Element subSubCom = subCom.select("div.subsubcom").first();
        List<Comment> children = parseSubSubCom(subSubCom, depth + 1);
        return new Comment(username, userLink, link, body, age, score, children, depth);
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
