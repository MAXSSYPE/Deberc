package app.first.my_deb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

import static app.first.my_deb.StartActivity.BUTTON_COLOR;
import static app.first.my_deb.StartActivity.COLOR_LIGHT_SELECTED;
import static app.first.my_deb.StartActivity.COLOR_SELECTED;
import static app.first.my_deb.StartActivity.PREF_COLOR;
import static app.first.my_deb.StartActivity.THEME_SELECTED;
import static app.first.my_deb.StartActivity.buttonColor;
import static app.first.my_deb.StartActivity.colorSelected;
import static app.first.my_deb.StartActivity.colorSelectedLight;
import static app.first.my_deb.StartActivity.editor;
import static app.first.my_deb.StartActivity.mSharedPreferences;

public class SettingsActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(PREF_COLOR, MODE_PRIVATE);
        int themeSelected = mSharedPreferences.getInt(THEME_SELECTED, R.style.AppTheme);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        colorSelected = mSharedPreferences.getInt(COLOR_SELECTED, R.color.colorGradientCenter);
        colorSelectedLight = mSharedPreferences.getInt(COLOR_LIGHT_SELECTED, R.color.black);
        buttonColor = mSharedPreferences.getInt(BUTTON_COLOR, R.color.colorGradientCenter);
        setTheme();
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
        startActivity(new Intent("app.first.my_deb.SHOW_3_ACTIVITY"));
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onBackPressed() {
        try {
            finish();
            startActivity(new Intent("app.first.my_deb.SHOW_3_ACTIVITY"));
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        } catch (Exception ignored) {
        }
    }

    private void setTheme() {
        LinearLayout linearLayout = findViewById(R.id.lin);
        Button button = findViewById(R.id.button);
        Button buttonDark = findViewById(R.id.but_dark);
        Button buttonLight = findViewById(R.id.but_light);
        ColorPrefUtil.changeBackgroundColorOfSingleView(this, linearLayout, colorSelected);
        ColorPrefUtil.changeBackgroundColorOfSingleView(this, buttonDark, buttonColor);
        ColorPrefUtil.changeBackgroundColorOfSingleView(this, buttonLight, buttonColor);
        ColorPrefUtil.changeBackgroundColorOfSingleView(this, button, buttonColor);
    }

    public void onThemeClick(View view) {
        editor = mSharedPreferences.edit();
        switch (view.getId()) {
            case R.id.but_light:
                editor.putInt(THEME_SELECTED, R.style.AppTheme);
                editor.putInt(COLOR_SELECTED, R.color.colorGradientCenter);
                editor.putInt(COLOR_LIGHT_SELECTED, R.color.black);
                editor.putInt(BUTTON_COLOR, R.color.colorGradientStart);
                editor.apply();
                recreate();
                break;
            case R.id.but_dark:
                editor.putInt(THEME_SELECTED, R.style.AppThemeDark);
                editor.putInt(COLOR_SELECTED, R.color.colorGradientCenterDark);
                editor.putInt(COLOR_LIGHT_SELECTED, R.color.white);
                editor.putInt(BUTTON_COLOR, R.color.colorGradientStartDark);
                editor.apply();
                recreate();
                break;
        }
    }

}