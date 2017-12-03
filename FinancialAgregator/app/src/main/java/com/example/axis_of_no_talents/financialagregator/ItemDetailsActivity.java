package com.example.axis_of_no_talents.financialagregator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

public class ItemDetailsActivity extends AppCompatActivity {
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent intent = getIntent();
        String title = intent.getStringExtra(HomeFragment.EXTRA_TITLE);
        link = intent.getStringExtra(HomeFragment.EXTRA_LINK);
        String pubDate = intent.getStringExtra(HomeFragment.EXTRA_PUB_DATE);
        String description = intent.getStringExtra(HomeFragment.EXTRA_DESCRIPTION);

        TextView tvTitle = (TextView) findViewById(R.id.item_details_title);
        TextView tvPubDate = (TextView) findViewById(R.id.item_details_pub_date);

        tvTitle.setText(title);
        tvPubDate.setText(pubDate);

        ImageView imageView = (ImageView) findViewById(R.id.item_details_image);

        if(Jsoup.parse(description).select("img").first() != null) {
            String imageURL = Jsoup.parse(description).select("img").first().attr("src");
            Picasso.with(this)
                    .load(imageURL)
                    .into(imageView);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        description = description.replaceAll("<img(.*?)>", "").trim();
        description = description.replaceAll("<(.*?)p>", "").trim();
        TextView tvDescription = (TextView) findViewById(R.id.item_details_description);
        tvDescription.setText(description);
    }

    public void onMoreClick(View view) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
