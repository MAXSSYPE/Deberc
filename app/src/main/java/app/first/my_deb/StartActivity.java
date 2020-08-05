package app.first.my_deb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.preference.PreferenceManager;

public class StartActivity extends Activity {
    private int count = 0;
    private final Handler handler = new Handler();
    static boolean flag = true;

    public static final String PREF_COLOR = "pref_color";
    public static final String COLOR_SELECTED = "selected";
    public static final String COLOR_LIGHT_SELECTED = "color_light_selected";
    public static final String THEME_SELECTED = "theme_selected";
    public static final String BUTTON_COLOR = "button_color";

    public static SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor editor;
    public static int colorSelected;
    public static int colorSelectedLight;
    public static int buttonColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSharedPreferences = getSharedPreferences(PREF_COLOR, MODE_PRIVATE);
        colorSelected = mSharedPreferences.getInt(COLOR_SELECTED, R.color.colorGradientCenter);
        colorSelectedLight = mSharedPreferences.getInt(COLOR_LIGHT_SELECTED, R.color.black);
        buttonColor = mSharedPreferences.getInt(BUTTON_COLOR, R.color.colorGradientCenter);
        defaultSettings();
        onEverySecond.run();
    }

    private final Runnable onEverySecond = new Runnable() {
        public void run() {
            count++;
            int limit = 3;
            if (count == limit) {
                Intent intent = new Intent("app.first.my_deb.SHOW_2x2_ACTIVITY");
                startActivity(intent);
                overridePendingTransition(R.anim.appear, R.anim.disappear);
                finish();
            } else {
                handler.postDelayed(onEverySecond, 1000);
            }
        }
    };

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

    private void defaultSettings() {
        if (!mSharedPreferences.contains(COLOR_SELECTED)) {
            editor = mSharedPreferences.edit();
            editor.putInt(THEME_SELECTED, R.style.AppTheme);
            editor.putInt(COLOR_SELECTED, R.color.colorGradientCenter);
            editor.putInt(COLOR_LIGHT_SELECTED, R.color.black);
            editor.putInt(BUTTON_COLOR, R.color.colorGradientStart);
            editor.apply();
        }
    }
}
