package com.example.axis_of_no_talents.financialagregator;

import android.content.Context;
import android.content.SharedPreferences;
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


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private View view;

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
            listView.setOnItemClickListener(this);

        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        EditText searchInput = (EditText) view.findViewById(R.id.search_input);

        preferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return view;
    }

    private void startService() {
        //todo logic
    }

    private void updateNewsView(String searchQuery) {
        //todo logic
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