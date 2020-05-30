package app.first.my_deb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String supp;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        supp = sharedPreferences.getString("theme", "");
        if (supp.equals("light")) {
            setTheme(R.style.AppTheme);
        } else if (supp.equals("dark")) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        LinearLayout linearLayout = findViewById(R.id.lin);
        if (supp.equals("dark"))
            linearLayout.setBackground(getDrawable(R.drawable.field_dark));
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(newBase);
        String lang = pref.getString("langs", "");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(ContextWrapper.wrap(newBase, lang));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    public void onClick(View view) {
        finish();
        startActivity(new Intent("app.first.my_deb.SHOW_4_ACTIVITY"));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onBackPressed() {
        try {
            finish();
            startActivity(new Intent("app.first.my_deb.SHOW_4_ACTIVITY"));
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        } catch (Exception ignored) {
        }
    }

}