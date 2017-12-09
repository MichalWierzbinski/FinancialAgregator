package com.kurekhub.rssfinancialreader;

import android.content.Context;
import android.util.Xml;

import com.kurekhub.rssfinancialreader.database.RssFeederDbHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RssParser {
    private final String ns = null;
    private RssFeederDbHelper dbHelper;

    public RssParser(Context applicationContext) {
        dbHelper = RssFeederDbHelper.getInstance(applicationContext);
    }

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        String pubDate = null;
        String description = null;
        List<RssItem> items = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTagValue(parser, "title");
                    break;
                case "link":
                    link = readTagValue(parser, "link");
                    break;
                case "pubDate":
                    pubDate = readTagValue(parser, "pubDate");
                    break;
                case "description":
                    description = readTagValue(parser, "description");
                    break;
            }

            if (title != null && link != null && pubDate != null && description != null) {
                RssItem item = new RssItem(title, link, pubDate, description);
                items.add(item);
                dbHelper.addItem(item);
                title = null;
                link = null;
                pubDate = null;
                description = null;
            }
        }


        return items;
    }

    private String readTagValue(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return text;
    }

    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        return result;
    }
}
