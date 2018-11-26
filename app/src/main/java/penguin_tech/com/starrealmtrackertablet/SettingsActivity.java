package penguin_tech.com.starrealmtrackertablet;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.view.MenuItem;

import penguin_tech.com.starrealmtrackertablet.R;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)getActivity());
            updateSummary(KEY_PREF_PLAYER_1);
            updateSummary(KEY_PREF_PLAYER_2);
            updateSummary(KEY_PREF_PLAYER_3);
            updateSummary(KEY_PREF_PLAYER_4);
        }

        public void updateSummary(String key) {
            EditTextPreference pref = (EditTextPreference)findPreference(key);
            pref.setSummary(pref.getText());
        }

    }

    public static final String KEY_PREF_KEEP_SCREEN_ON = "pref_keep_screen_on";
    public static final String KEY_PREF_PLAYER_1 = "pref_player_1";
    public static final String KEY_PREF_PLAYER_2 = "pref_player_2";
    public static final String KEY_PREF_PLAYER_3 = "pref_player_3";
    public static final String KEY_PREF_PLAYER_4 = "pref_player_4";

    private int resultCode = RESULT_CANCELED;
    private SettingsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch(key) {
            case KEY_PREF_PLAYER_1:
            case KEY_PREF_PLAYER_2:
            case KEY_PREF_PLAYER_3:
            case KEY_PREF_PLAYER_4:
                fragment.updateSummary(key);
            case KEY_PREF_KEEP_SCREEN_ON:
                resultCode = RESULT_OK;
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(resultCode, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment.getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fragment.getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
