package com.kurekhub.rssfinancialreader;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

class NewsAdapter extends CursorAdapter {
    NewsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.rss_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView) view.findViewById(R.id.news_list_item_title);
        TextView tvPubDate = (TextView) view.findViewById(R.id.news_list_item_pub_date);
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String pubDate = cursor.getString(cursor.getColumnIndexOrThrow("pub_date"));
        tvTitle.setText(title);
        tvPubDate.setText(pubDate);
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = getCursor();
        RssItem rssItem;
        if(cursor.moveToPosition(position)) {
            rssItem = new RssItem(
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("link")),
                    cursor.getString(cursor.getColumnIndexOrThrow("pub_date")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description"))
            );
            return rssItem;
        }

        return null;
    }
}