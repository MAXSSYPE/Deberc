package app.first.my_deb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.maltaisn.calcdialog.CalcDialog;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;

import akndmr.github.io.colorprefutil.ColorPrefUtil;
import id.ionbit.ionalert.IonAlert;

import static app.first.my_deb.StartActivity.THEME_SELECTED;
import static app.first.my_deb.StartActivity.buttonColor;
import static app.first.my_deb.StartActivity.colorSelected;
import static app.first.my_deb.StartActivity.mSharedPreferences;


public class Activity4 extends AppCompatActivity implements CalcDialog.CalcDialogCallback {
    private TextView resultField1;
    private TextView resultField2;
    private TextView resultField3;
    private TextView resultField4;
    private EditText numberField1;
    private EditText numberField2;
    private EditText numberField3;
    private EditText numberField4;
    private TextView name1;
    private TextView name2;
    private TextView name3;
    private TextView name4;
    private ResideMenu resideMenu;
    private final ArrayList<String> arrPlayer1 = new ArrayList<>();
    private final ArrayList<String> arrPlayer2 = new ArrayList<>();
    private final ArrayList<String> arrPlayer3 = new ArrayList<>();
    private final ArrayList<String> arrPlayer4 = new ArrayList<>();

    @Nullable
    private BigDecimal value = null;
    final CalcDialog calcDialog = new CalcDialog();
    private ImageButton calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int themeSelected = mSharedPreferences.getInt(THEME_SELECTED, R.style.AppTheme);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);

        setContentView(R.layout.activity_4);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MobileAds.initialize(this, "ca-app-pub-5807744662830254~2797692226");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        resultField1 = findViewById(R.id.resultField1);
        resultField2 = findViewById(R.id.resultField2);
        resultField3 = findViewById(R.id.resultField3);
        resultField4 = findViewById(R.id.resultField4);
        numberField1 = findViewById(R.id.numberField1);
        numberField2 = findViewById(R.id.numberField2);
        numberField3 = findViewById(R.id.numberField3);
        numberField4 = findViewById(R.id.numberField4);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        calc = findViewById(R.id.but_calc);
        loadText(this);

        numberField1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handle = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    onClick(numberField1);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        numberField2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handle = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    onClick(numberField2);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        numberField3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handle = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    onClick(numberField3);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        numberField4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handle = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    onClick(numberField4);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.dark);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);

        String[] titles = {getResources().getString(R.string.new_game), getResources().getString(R.string.count), getResources().getString(R.string.settings), getResources().getString(R.string.vs22), getResources().getString(R.string.vs3), getResources().getString(R.string.vs2)};
        int[] icon = {R.drawable.newg, R.drawable.score, R.drawable.settings, R.drawable.for1, R.drawable.for2, R.drawable.for3};
        ResideMenuItem item1 = new ResideMenuItem(this, icon[0], titles[0]);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IonAlert(Activity4.this, IonAlert.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.sure))
                        .setContentText(getResources().getString(R.string.new_game))
                        .setCancelText(getResources().getString(R.string.no))
                        .setConfirmText(getResources().getString(R.string.yes))
                        .showCancelButton(true)
                        .setConfirmClickListener(sDialog -> {
                            resideMenu.closeMenu();
                            resultField1.setText("0");
                            resultField2.setText("0");
                            resultField3.setText("0");
                            resultField4.setText("0");
                            numberField1.setText("");
                            numberField2.setText("");
                            numberField3.setText("");
                            numberField4.setText("");
                            SharedPreferences scoreSharPref = getSharedPreferences("Score4.txt", MODE_PRIVATE);
                            SharedPreferences.Editor editor = scoreSharPref.edit().clear();
                            editor.apply();
                            arrPlayer1.clear();
                            arrPlayer2.clear();
                            arrPlayer3.clear();
                            arrPlayer4.clear();
                            sDialog.cancel();
                        })
                        .show();
            }
        });
        resideMenu.addMenuItem(item1,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item2 = new ResideMenuItem(this, icon[1], titles[1]);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.score4"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        resideMenu.addMenuItem(item2,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item3 = new ResideMenuItem(this, icon[2], titles[2]);
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.settings4"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item3,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item4 = new ResideMenuItem(this, icon[3], titles[3]);
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.SHOW_2x2_ACTIVITY"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item4,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item5 = new ResideMenuItem(this, icon[4], titles[4]);
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.SHOW_3_ACTIVITY"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item5,  ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item6 = new ResideMenuItem(this, icon[5], titles[5]);
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.SHOW_2_ACTIVITY"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item6,  ResideMenu.DIRECTION_LEFT);

        setTheme();
    }

    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!(numberField1.getText().toString().equals("") && numberField2.getText().toString().equals("") && numberField3.getText().toString().equals("") && numberField4.getText().toString().equals("")))
            try {
                int prev1 = Integer.parseInt(resultField1.getText().toString());
                int prev2 = Integer.parseInt(resultField2.getText().toString());
                int prev3 = Integer.parseInt(resultField3.getText().toString());
                int prev4 = Integer.parseInt(resultField4.getText().toString());
                int now1 = 0;
                int now2 = 0;
                int now3 = 0;
                int now4 = 0;

                if (!numberField1.getText().toString().equals(""))
                    now1 = Integer.parseInt(numberField1.getText().toString());
                else
                    numberField1.setText("0");
                if (!numberField2.getText().toString().equals(""))
                    now2 = Integer.parseInt(numberField2.getText().toString());
                else
                    numberField2.setText("0");
                if (!numberField3.getText().toString().equals(""))
                    now3 = Integer.parseInt(numberField3.getText().toString());
                else
                    numberField3.setText("0");
                if (!numberField4.getText().toString().equals(""))
                    now4 = Integer.parseInt(numberField4.getText().toString());
                else
                    numberField4.setText("0");

                resultField1.setText(String.valueOf(prev1 + now1));
                resultField2.setText(String.valueOf(prev2 + now2));
                resultField3.setText(String.valueOf(prev3 + now3));
                resultField4.setText(String.valueOf(prev4 + now4));
                SharedPreferences scoreSharPref = getSharedPreferences("Score4.txt", MODE_PRIVATE);
                SharedPreferences.Editor editor = scoreSharPref.edit();
                arrPlayer1.add(numberField1.getText().toString());
                arrPlayer2.add(numberField2.getText().toString());
                arrPlayer3.add(numberField3.getText().toString());
                arrPlayer4.add(numberField4.getText().toString());
                Gson gson = new Gson();
                String listStr1 = gson.toJson(arrPlayer1);
                String listStr2 = gson.toJson(arrPlayer2);
                String listStr3 = gson.toJson(arrPlayer3);
                String listStr4 = gson.toJson(arrPlayer4);
                editor.putString("pl1", listStr1);
                editor.putString("pl2", listStr2);
                editor.putString("pl3", listStr3);
                editor.putString("pl4", listStr4);
                editor.apply();
                numberField1.setText("");
                numberField2.setText("");
                numberField3.setText("");
                numberField4.setText("");
            } catch (Exception ignored) {

            }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (resideMenu.isOpened()) {
            new IonAlert(this, IonAlert.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.exitTitle))
                    .setContentText(getResources().getString(R.string.exit))
                    .setCancelText(getResources().getString(R.string.no))
                    .setConfirmText(getResources().getString(R.string.yes))
                    .showCancelButton(true)
                    .setConfirmClickListener(sDialog -> {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                        sDialog.cancel();
                    })
                    .show();
        } else {
            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
    }

    private void saveText() {
        SharedPreferences sPref = getSharedPreferences("Save4.txt", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("res1", resultField1.getText().toString());
        ed.putString("res2", resultField2.getText().toString());
        ed.putString("res3", resultField3.getText().toString());
        ed.putString("res4", resultField4.getText().toString());
        ed.putString("name1", name1.getText().toString());
        ed.putString("name2", name2.getText().toString());
        ed.putString("name3", name3.getText().toString());
        ed.putString("name4", name4.getText().toString());
        ed.apply();
    }

    private void loadText(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("Save4.txt", MODE_PRIVATE);
        name1.setText(sPref.getString("name1", ""));
        name2.setText(sPref.getString("name2", ""));
        name3.setText(sPref.getString("name3", ""));
        name4.setText(sPref.getString("name4", ""));
        if (sPref.getString("res1", "0").equals(""))
            resultField1.setText("0");
        else
            resultField1.setText(sPref.getString("res1", "0"));

        if (sPref.getString("res2", "0").equals(""))
            resultField2.setText("0");
        else
            resultField2.setText(sPref.getString("res2", "0"));

        if (sPref.getString("res3", "0").equals(""))
            resultField3.setText("0");
        else
            resultField3.setText(sPref.getString("res3", "0"));

        if (sPref.getString("res4", "0").equals(""))
            resultField4.setText("0");
        else
            resultField4.setText(sPref.getString("res4", "0"));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
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

    public void onNewClick(View view) {
        new IonAlert(this, IonAlert.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.sure))
                .setContentText(getResources().getString(R.string.new_game))
                .setCancelText(getResources().getString(R.string.no))
                .setConfirmText(getResources().getString(R.string.yes))
                .showCancelButton(true)
                .setConfirmClickListener(sDialog -> {
                    resultField1.setText("0");
                    resultField2.setText("0");
                    resultField3.setText("0");
                    resultField4.setText("0");
                    numberField1.setText("");
                    numberField2.setText("");
                    numberField3.setText("");
                    numberField4.setText("");
                    SharedPreferences scoreSharPref = getSharedPreferences("Score4.txt", MODE_PRIVATE);
                    SharedPreferences.Editor editor = scoreSharPref.edit().clear();
                    editor.apply();
                    arrPlayer1.clear();
                    arrPlayer2.clear();
                    arrPlayer3.clear();
                    arrPlayer4.clear();
                    sDialog.cancel();
                })
                .show();
    }

    private final ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                assert imm != null;
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        @Override
        public void closeMenu() {

        }
    };

    public void onCalcClick(View view) {
        calcDialog.getSettings().setExpressionShown(true)
                .setInitialValue(value);
        calcDialog.show(getSupportFragmentManager(), "calc_dialog");
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        this.value = value;
    }

    private void setTheme() {
        LinearLayout linearLayout = findViewById(R.id.main);
        calc = findViewById(R.id.but_calc);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonNew = findViewById(R.id.button_new);
        if (mSharedPreferences.getInt(THEME_SELECTED, 0) == R.style.AppThemeDark) {
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, calc, buttonColor);
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, buttonAdd, buttonColor);
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, buttonNew, buttonColor);
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, linearLayout, colorSelected);
            resideMenu.setBackground(R.drawable.dark2);
        }
    }
}
