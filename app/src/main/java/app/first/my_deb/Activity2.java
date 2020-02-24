package app.first.my_deb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> arrPlayer1= new ArrayList<>();
    private ArrayList<String> arrPlayer2= new ArrayList<>();
    private TextView resultField1;
    private TextView resultField2;
    private EditText numberField1;
    private EditText numberField2;
    private TextView name1;
    private TextView name2;
    private final String score = "app.first.my_deb.activity_score";
    private final Intent intent2 = new Intent(score);
    private ResideMenu resideMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MobileAds.initialize(this, "ca-app-pub-5807744662830254~2797692226");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        LinearLayout linearLayout = findViewById(R.id.main);
        resultField1 = findViewById(R.id.resultField1);
        resultField2 = findViewById(R.id.resultField2);
        numberField1 = findViewById(R.id.numberField1);
        numberField2 = findViewById(R.id.numberField2);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        loadText(this);

        AnimationDrawable animDrawable = (AnimationDrawable) linearLayout.getBackground();
        animDrawable.setEnterFadeDuration(10);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.dark);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.attachToActivity(this);

        String[] titles = {"Новая игра", "Cчет", "2 на 2", "Hа 4", "Hа 3"};
        int[] icon = {R.drawable.newg, R.drawable.score, R.drawable.for1, R.drawable.for2, R.drawable.for3};
        ResideMenuItem item1 = new ResideMenuItem(this, icon[0], titles[0]);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Activity2.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Новая игра")
                        .setMessage("Вы уверены?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resideMenu.closeMenu();
                                resultField1.setText("0");
                                resultField2.setText("0");
                                numberField1.setText("");
                                numberField2.setText("");
                                name1.setText("");
                                name2.setText("");
                                SharedPreferences scoreSharPref = getSharedPreferences("Score.txt", MODE_PRIVATE);
                                Editor editor = scoreSharPref.edit().clear();
                                editor.apply();
                                arrPlayer1.clear();
                                arrPlayer2.clear();
                            }
                        }).setNegativeButton("Нет", null).show();
            }
        });
        resideMenu.addMenuItem(item1,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item2 = new ResideMenuItem(this, icon[1], titles[1]);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.score"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        resideMenu.addMenuItem(item2,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item3 = new ResideMenuItem(this, icon[2], titles[2]);
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.SHOW_2x2_ACTIVITY"));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item3,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item4 = new ResideMenuItem(this, icon[3], titles[3]);
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.SHOW_4_ACTIVITY"));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item4,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item5 = new ResideMenuItem(this, icon[4], titles[4]);
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.SHOW_3_ACTIVITY"));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item5,  ResideMenu.DIRECTION_LEFT);
    }

    public void onClick(View view) {
        try {
            int prev1 = Integer.valueOf(resultField1.getText().toString());
            int prev2 = Integer.valueOf(resultField2.getText().toString());
            int now1 = 0;
            int now2 = 0;
            if (!numberField1.getText().toString().equals(""))
                now1 = Integer.valueOf(numberField1.getText().toString());
            else
                numberField1.setText("0");
            if (!numberField2.getText().toString().equals(""))
                now2 = Integer.valueOf(numberField2.getText().toString());
            else
                numberField2.setText("0");

            resultField1.setText(String.valueOf(prev1 + now1));
            resultField2.setText(String.valueOf(prev2 + now2));
            SharedPreferences scoreSharPref = getSharedPreferences("Score.txt", MODE_PRIVATE);
            Editor editor = scoreSharPref.edit();
            arrPlayer1.add(numberField1.getText().toString());
            arrPlayer2.add(numberField2.getText().toString());
            Gson gson = new Gson();
            String listStr1 = gson.toJson(arrPlayer1);
            String listStr2 = gson.toJson(arrPlayer2);
            editor.putString("pl1", listStr1);
            editor.putString("pl2", listStr2);
            editor.apply();
            numberField1.setText("");
            numberField2.setText("");
        } catch (Exception ignored) {

        }
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

    private void saveText() {
        SharedPreferences sPref = getSharedPreferences("Save2.txt", MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString("res1", resultField1.getText().toString());
        ed.putString("res2", resultField2.getText().toString());
        ed.putString("name1", name1.getText().toString());
        ed.putString("name2", name2.getText().toString());
        ed.apply();
    }

    private void loadText(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("Save2.txt", MODE_PRIVATE);
        name1.setText(sPref.getString("name1", ""));
        name2.setText(sPref.getString("name2", ""));
        if (sPref.getString("res1", "0").equals(""))
            resultField1.setText("0");
        else
            resultField1.setText(sPref.getString("res1", "0"));

        if (sPref.getString("res2", "0").equals(""))
            resultField2.setText("0");
        else
            resultField2.setText(sPref.getString("res2", "0"));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Выход")
                .setMessage("Вы уверены что хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Нет", null).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
}
