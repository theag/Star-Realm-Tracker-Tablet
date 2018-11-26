package penguin_tech.com.starrealmtrackertablet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements EditValueDialogFragment.EditValueListener {

    private static final int SETTINGS_REQUEST = 1;
    private static final String[] AUTHORITY = new String[]{"AUTHORITY_1","AUTHORITY_2","AUTHORITY_3","AUTHORITY_4"};
    private static final String[] TRADE = new String[]{"TRADE_1","TRADE_2","TRADE_3","TRADE_4"};
    private static final String[] COMBAT = new String[]{"COMBAT_1","COMBAT_2","COMBAT_3","COMBAT_4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(SettingsActivity.KEY_PREF_KEEP_SCREEN_ON, false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null) {
            return;
        }
        PlayerFragment pf1, pf2, pf3, pf4;
        sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.contains(AUTHORITY[0])) {
            pf1 = PlayerFragment.newInstance("Player 1",
                    sharedPreferences.getInt(AUTHORITY[0], -1),
                    sharedPreferences.getInt(TRADE[0], -1),
                    sharedPreferences.getInt(COMBAT[0], -1));
            pf2 = PlayerFragment.newInstance("Player 2",
                    sharedPreferences.getInt(AUTHORITY[1], -1),
                    sharedPreferences.getInt(TRADE[1], -1),
                    sharedPreferences.getInt(COMBAT[1], -1));
            pf3 = PlayerFragment.newInstance("Player 3",
                    sharedPreferences.getInt(AUTHORITY[2], -1),
                    sharedPreferences.getInt(TRADE[2], -1),
                    sharedPreferences.getInt(COMBAT[2], -1));
            pf4 = PlayerFragment.newInstance("Player 4",
                    sharedPreferences.getInt(AUTHORITY[3], -1),
                    sharedPreferences.getInt(TRADE[3], -1),
                    sharedPreferences.getInt(COMBAT[3], -1));
        } else {
            pf1 = PlayerFragment.newInstance("Player 1");
            pf2 = PlayerFragment.newInstance("Player 2");
            pf3 = PlayerFragment.newInstance("Player 3");
            pf4 = PlayerFragment.newInstance("Player 4");
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_1, pf1, "1")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_2, pf2, "2")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_3, pf3, "3")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_4, pf4, "4")
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        PlayerFragment pf;
        for(int i = 0; i < 4; i++) {
            pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag(""+(1+i));
            editor.putInt(AUTHORITY[i], pf.getAuthority());
            editor.putInt(TRADE[i], pf.getTrade());
            editor.putInt(COMBAT[i], pf.getCombat());
        }
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mi_new:
                PlayerFragment pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("1");
                pf.newGame();
                pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("2");
                pf.newGame();
                pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("3");
                pf.newGame();
                pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("4");
                pf.newGame();
                return true;
            case R.id.mi_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, SETTINGS_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case SETTINGS_REQUEST:
                if(resultCode == RESULT_OK) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    if(sharedPreferences.getBoolean(SettingsActivity.KEY_PREF_KEEP_SCREEN_ON, false)) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    } else {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                    PlayerFragment pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("1");
                    pf.setPlayerName(sharedPreferences.getString(SettingsActivity.KEY_PREF_PLAYER_1, "Player 1"));
                    pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("2");
                    pf.setPlayerName(sharedPreferences.getString(SettingsActivity.KEY_PREF_PLAYER_1, "Player 2"));
                    pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("3");
                    pf.setPlayerName(sharedPreferences.getString(SettingsActivity.KEY_PREF_PLAYER_1, "Player 3"));
                    pf = (PlayerFragment)getSupportFragmentManager().findFragmentByTag("4");
                    pf.setPlayerName(sharedPreferences.getString(SettingsActivity.KEY_PREF_PLAYER_1, "Player 4"));
                }
                break;
        }
    }

    @Override
    public void valueChanged(String fragTag, String tag, int value) {
        PlayerFragment pf = (PlayerFragment) getSupportFragmentManager().findFragmentByTag(fragTag);
        pf.valueChanged(tag, value);
    }
}
