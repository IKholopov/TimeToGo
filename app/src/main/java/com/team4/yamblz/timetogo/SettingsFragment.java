package com.team4.yamblz.timetogo;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.bar_back);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("push_notification")){
            NotificationService.setServiceAlarm(getActivity(),sharedPreferences.getBoolean(key,false));
        }
    }
}
