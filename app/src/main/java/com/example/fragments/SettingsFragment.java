package com.example.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.quotation.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
    }
}
