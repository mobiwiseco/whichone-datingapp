package ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import akilliyazilim.whichone.R;

/**
 * Created by mertsimsek on 20/01/15.
 */
public class SettingsFragment extends PreferenceFragment{

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
