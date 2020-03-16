package app.first.my_deb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

public class Score3 extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Slidr.attach(this);
        linearLayout = findViewById(R.id.lin);
        loadText(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        try {
            finish();
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        } catch (Exception ignored) {
        }
    }

    private void loadText(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("Score3.txt", MODE_PRIVATE);
        ArrayList<String> pl1 = new Gson().fromJson(sPref.getString("pl1", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        ArrayList<String> pl2 = new Gson().fromJson(sPref.getString("pl2", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        ArrayList<String> pl3 = new Gson().fromJson(sPref.getString("pl3", ""), new TypeToken<ArrayList<String>>() {
        }.getType());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 10);
        params.weight = 1;
        params.width = 0;
        params.gravity = Gravity.CENTER;

        LinearLayout up = new LinearLayout(this);
        up.setOrientation(LinearLayout.HORIZONTAL);
        TextView up1 = new TextView(this);
        up1.setLayoutParams(params);
        up1.setTextColor(Color.BLACK);
        up1.setTextSize(18);
        up1.setGravity(Gravity.CENTER_HORIZONTAL);
        up1.setText(getResources().getString(R.string.distribution));

        TextView up2 = new TextView(this);
        up2.setLayoutParams(params);
        up2.setTextColor(Color.BLACK);
        up2.setTextSize(18);
        up2.setGravity(Gravity.CENTER_HORIZONTAL);
        up2.setText(getResources().getString(R.string.gamer1));

        TextView up3 = new TextView(this);
        up3.setLayoutParams(params);
        up3.setTextColor(Color.BLACK);
        up3.setTextSize(18);
        up3.setGravity(Gravity.CENTER_HORIZONTAL);
        up3.setText(getResources().getString(R.string.gamer2));

        TextView up4 = new TextView(this);
        up4.setLayoutParams(params);
        up4.setTextColor(Color.BLACK);
        up4.setTextSize(18);
        up4.setGravity(Gravity.CENTER_HORIZONTAL);
        up4.setText(getResources().getString(R.string.gamer3));
        up.addView(up1);
        up.addView(up2);
        up.addView(up3);
        up.addView(up4);
        linearLayout.addView(up);

        if (pl1 != null)
            for (int i = 0; i < pl1.size(); i++) {
                LinearLayout supp = new LinearLayout(this);
                supp.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView1 = new TextView(this);
                textView1.setLayoutParams(params);
                textView1.setTextColor(Color.BLACK);
                textView1.setTextSize(24);
                textView1.setGravity(Gravity.CENTER_HORIZONTAL);
                textView1.setText(String.valueOf(i + 1));

                TextView textView2 = new TextView(this);
                textView2.setLayoutParams(params);
                textView2.setTextColor(Color.BLACK);
                textView2.setTextSize(24);
                textView2.setGravity(Gravity.CENTER_HORIZONTAL);
                textView2.setText(pl1.get(i));

                TextView textView3 = new TextView(this);
                textView3.setLayoutParams(params);
                textView3.setTextColor(Color.BLACK);
                textView3.setTextSize(24);
                textView3.setGravity(Gravity.CENTER_HORIZONTAL);
                textView3.setText(pl2.get(i));

                TextView textView4 = new TextView(this);
                textView4.setLayoutParams(params);
                textView4.setTextColor(Color.BLACK);
                textView4.setTextSize(24);
                textView4.setGravity(Gravity.CENTER_HORIZONTAL);
                textView4.setText(pl3.get(i));

                supp.addView(textView1);
                supp.addView(textView2);
                supp.addView(textView3);
                supp.addView(textView4);
                linearLayout.addView(supp);
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
}
