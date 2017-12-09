package com.example.axis_of_no_talents.financialagregator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class CategoriesFragment extends Fragment {
    public static final String TAG = "[CategoriesFragment]";

    private String bankierKey = "www.bankier.pl";
    private String interiaKey = "biznes.interia.pl";
    private String moneyKey = "www.money.pl";
    private String wpKey = "finanse.wp.pl";

    private CheckBox bankierBox;
    private CheckBox interiaBox;
    private CheckBox moneyBox;
    private CheckBox wpBox;

    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;

    private class CheckBoxSomethingChanger implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            prefEditor = preferences.edit();
            String checkBoxText = buttonView.getText().toString();
            Log.i(TAG, checkBoxText);
            prefEditor.putBoolean(checkBoxText, isChecked);
            prefEditor.apply();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        preferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        boolean bankierChecked = preferences.getBoolean(bankierKey, Boolean.TRUE);
        boolean interiaChecked = preferences.getBoolean(interiaKey, Boolean.TRUE);
        boolean moneyChecked = preferences.getBoolean(moneyKey, Boolean.TRUE);
        boolean wpChecked = preferences.getBoolean(wpKey, Boolean.TRUE);

        bankierBox = (CheckBox) view.findViewById(R.id.bankier_page);
        interiaBox = (CheckBox) view.findViewById(R.id.interia_page);
        moneyBox = (CheckBox) view.findViewById(R.id.money_page);
        wpBox = (CheckBox) view.findViewById(R.id.wp_page);

        bankierBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());
        interiaBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());
        moneyBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());
        wpBox.setOnCheckedChangeListener(new CheckBoxSomethingChanger());

        bankierBox.setChecked(bankierChecked);
        interiaBox.setChecked(interiaChecked);
        moneyBox.setChecked(moneyChecked);
        wpBox.setChecked(wpChecked);

        return view;
    }
}