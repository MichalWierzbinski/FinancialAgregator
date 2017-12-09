package com.example.axis_of_no_talents.financialagregator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.axis_of_no_talents.financialagregator.db.DBHelper;

import java.util.Map;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_LINK = "EXTRA_LINK";
    public static final String EXTRA_PUB_DATE = "EXTRA_PUB_DATE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";

    private ListView listView;
    private View view;
    private ProgressBar progressBar;


    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            listView = (ListView) view.findViewById(R.id.rss_list);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView.setOnItemClickListener(this);
            if(isOnline()) {
                startService();
            }


        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        EditText searchInput = (EditText) view.findViewById(R.id.search_input);

        preferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return view;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    private void updateNewsView(String searchQuery) {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        DBHelper handler = DBHelper.getInstance(getActivity());
        SQLiteDatabase db = handler.getWritableDatabase();
        String sqlQuery;

        String categoriesQuery = "";
        boolean categoriesFirst = true;

        //todo Categories nd search logic

        Cursor rssCursor = db.rawQuery("SELECT * FROM rss_feeder ORDER BY _id ASC", null);
        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), rssCursor);
        listView.setAdapter(newsAdapter);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

    }

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            updateNewsView(null);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //todo logic
    }
}