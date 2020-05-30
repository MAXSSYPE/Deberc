package app.first.my_deb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class StartActivity extends Activity {
    private int count = 0;
    private final Handler handler = new Handler();
    static boolean flag = true;

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
        setContentView(R.layout.activity_start);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
}
