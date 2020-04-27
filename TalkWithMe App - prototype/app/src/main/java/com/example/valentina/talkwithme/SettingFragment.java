package com.example.valentina.talkwithme;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load the preferences from the xml resource
        addPreferencesFromResource(R.xml.preferences);
    }
}