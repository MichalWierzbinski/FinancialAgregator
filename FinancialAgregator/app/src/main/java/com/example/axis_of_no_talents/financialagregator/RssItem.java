package com.example.axis_of_no_talents.financialagregator;

public class RssItem {
    private final String title;
    private final String link;
    private final String pubDate;
    private final String description;

    public RssItem(String title, String link, String pubDate, String description) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }
}