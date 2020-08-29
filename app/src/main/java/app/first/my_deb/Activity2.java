package app.first.my_deb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.shawnlin.numberpicker.NumberPicker;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;

import akndmr.github.io.colorprefutil.ColorPrefUtil;
import id.ionbit.ionalert.IonAlert;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static app.first.my_deb.StartActivity.THEME_SELECTED;
import static app.first.my_deb.StartActivity.buttonColor;
import static app.first.my_deb.StartActivity.colorSelected;
import static app.first.my_deb.StartActivity.mSharedPreferences;

public class Activity2 extends AppCompatActivity implements CalcDialog.CalcDialogCallback {
    private final ArrayList<String> arrPlayer1 = new ArrayList<>();
    private final ArrayList<String> arrPlayer2 = new ArrayList<>();
    private TextView resultField1;
    private TextView resultField2;
    private EditText numberField1;
    private EditText numberField2;
    private TextView name1;
    private TextView name2;
    private final String score = "app.first.my_deb.activity_score";
    private ResideMenu resideMenu;
    private NumberPicker numberPicker;
    @Nullable
    private BigDecimal value = null;
    final CalcDialog calcDialog = new CalcDialog();
    private ImageButton calc;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int themeSelected = mSharedPreferences.getInt(THEME_SELECTED, R.style.AppTheme);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);

        setContentView(R.layout.activity_2);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MobileAds.initialize(this, "ca-app-pub-5807744662830254~2797692226");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        numberField1 = findViewById(R.id.numberField1);
        numberField2 = findViewById(R.id.numberField2);
        resultField1 = findViewById(R.id.resultField1);
        resultField2 = findViewById(R.id.resultField2);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        calc = findViewById(R.id.but_calc);
        numberPicker = findViewById(R.id.number_picker);
        final String[] data = {"162", "182", "202", "212", "222", "232", "242", "252", "262", "272", "282", "302", "322"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(13);
        numberPicker.setDisplayedValues(data);
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!(numberField1.getText().toString().length() == 0 && numberField2.getText().toString().length() == 0) || !(numberField1.getText().toString().length() != 0 && numberField2.getText().toString().length() != 0)) {
                        if (numberField1.getText().toString().length() != 0 && Integer.parseInt(numberField1.getText().toString()) > 0) {
                            if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.parseInt(numberField1.getText().toString()) >= 0 && Integer.parseInt(numberField1.getText().toString()) <= Integer.parseInt(data[numberPicker.getValue() - 1])) {
                                numberField2.setHint(String.valueOf(Integer.parseInt(data[numberPicker.getValue() - 1]) - Integer.parseInt(numberField1.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                        if (numberField2.getText().toString().length() != 0 && Integer.parseInt(numberField2.getText().toString()) > 0) {
                            if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.parseInt(numberField2.getText().toString()) >= 0 && Integer.parseInt(numberField2.getText().toString()) <= Integer.parseInt(data[numberPicker.getValue() - 1])) {
                                numberField1.setHint(String.valueOf(Integer.parseInt(data[numberPicker.getValue() - 1]) - Integer.parseInt(numberField2.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    if (!(numberField1.getText().toString().length() == 0 && numberField2.getText().toString().length() == 0) || !(numberField1.getText().toString().length() != 0 && numberField2.getText().toString().length() != 0)) {
                        if (numberField1.getText().toString().length() != 0 && Integer.parseInt(numberField1.getText().toString()) > 0) {
                            if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.parseInt(numberField1.getText().toString()) >= 0 && Integer.parseInt(numberField1.getText().toString()) <= Integer.parseInt(data[picker.getValue() - 1])) {
                                numberField2.setHint(String.valueOf(Integer.parseInt(data[picker.getValue() - 1]) - Integer.parseInt(numberField1.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                        if (numberField2.getText().toString().length() != 0 && Integer.parseInt(numberField2.getText().toString()) > 0) {
                            if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.parseInt(numberField2.getText().toString()) >= 0 && Integer.parseInt(numberField2.getText().toString()) <= Integer.parseInt(data[picker.getValue() - 1])) {
                                numberField1.setHint(String.valueOf(Integer.parseInt(data[picker.getValue() - 1]) - Integer.parseInt(numberField2.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        });
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                try {
                    if (!(numberField1.getText().toString().length() == 0 && numberField2.getText().toString().length() == 0) || !(numberField1.getText().toString().length() != 0 && numberField2.getText().toString().length() != 0)) {
                        if (numberField1.getText().toString().length() != 0 && Integer.parseInt(numberField1.getText().toString()) > 0) {
                            if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.parseInt(numberField1.getText().toString()) >= 0 && Integer.parseInt(numberField1.getText().toString()) <= Integer.parseInt(data[view.getValue() - 1])) {
                                numberField2.setHint(String.valueOf(Integer.parseInt(data[view.getValue() - 1]) - Integer.parseInt(numberField1.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                        if (numberField2.getText().toString().length() != 0 && Integer.parseInt(numberField2.getText().toString()) > 0) {
                            if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.parseInt(numberField2.getText().toString()) >= 0 && Integer.parseInt(numberField2.getText().toString()) <= Integer.parseInt(data[view.getValue() - 1])) {
                                numberField1.setHint(String.valueOf(Integer.parseInt(data[view.getValue() - 1]) - Integer.parseInt(numberField2.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                    }
                } catch (Exception ignored) {

                }
            }
        });
        loadText(this);

        numberField1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.parseInt(numberField1.getText().toString()) >= 0 && Integer.parseInt(numberField1.getText().toString()) <= Integer.parseInt(data[numberPicker.getValue() - 1])) {
                        numberField2.setHint(String.valueOf(Integer.parseInt(data[numberPicker.getValue() - 1]) - Integer.parseInt(numberField1.getText().toString())));
                    } else {
                        numberField1.setHint("0");
                        numberField2.setHint("0");
                    }
                } catch (Exception ignored) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        numberField2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.parseInt(numberField2.getText().toString()) >= 0 && Integer.parseInt(numberField2.getText().toString()) <= Integer.parseInt(data[numberPicker.getValue() - 1])) {
                        numberField1.setHint(String.valueOf(Integer.parseInt(data[numberPicker.getValue() - 1]) - Integer.parseInt(numberField2.getText().toString())));
                    } else {
                        numberField1.setHint("0");
                        numberField2.setHint("0");
                    }
                } catch (Exception ignored) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.dark);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);

        String[] titles = {getResources().getString(R.string.new_game), getResources().getString(R.string.count), getResources().getString(R.string.settings), getResources().getString(R.string.vs22), getResources().getString(R.string.vs4), getResources().getString(R.string.vs3)};
        int[] icon = {R.drawable.newg, R.drawable.score, R.drawable.settings, R.drawable.for1, R.drawable.for2, R.drawable.for3};
        ResideMenuItem item1 = new ResideMenuItem(this, icon[0], titles[0]);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IonAlert(Activity2.this, IonAlert.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.sure))
                        .setContentText(getResources().getString(R.string.new_game))
                        .setCancelText(getResources().getString(R.string.no))
                        .setConfirmText(getResources().getString(R.string.yes))
                        .showCancelButton(true)
                        .setConfirmClickListener(sDialog -> {
                            resideMenu.closeMenu();
                            resultField1.setText("0");
                            resultField2.setText("0");
                            numberField1.setText("");
                            numberField2.setText("");
                            SharedPreferences scoreSharPref = getSharedPreferences("Score.txt", MODE_PRIVATE);
                            Editor editor = scoreSharPref.edit().clear();
                            editor.apply();
                            arrPlayer1.clear();
                            arrPlayer2.clear();
                            sDialog.cancel();
                        })
                        .show();
            }
        });
        resideMenu.addMenuItem(item1, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item2 = new ResideMenuItem(this, icon[1], titles[1]);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.score"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        resideMenu.addMenuItem(item2, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item3 = new ResideMenuItem(this, icon[2], titles[2]);
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.settings2"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item3, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item4 = new ResideMenuItem(this, icon[3], titles[3]);
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.SHOW_2x2_ACTIVITY"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item4, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item5 = new ResideMenuItem(this, icon[4], titles[4]);
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.SHOW_4_ACTIVITY"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item5, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item6 = new ResideMenuItem(this, icon[5], titles[5]);
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent("app.first.my_deb.SHOW_3_ACTIVITY"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item6, ResideMenu.DIRECTION_LEFT);

        setTheme();
    }

    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!(numberField1.getText().toString().equals("") && numberField2.getText().toString().equals("")))
            try {
                int prev1 = Integer.parseInt(resultField1.getText().toString());
                int prev2 = Integer.parseInt(resultField2.getText().toString());
                int now1;
                int now2;
                if (!numberField1.getText().toString().equals(""))
                    now1 = Integer.parseInt(numberField1.getText().toString());
                else {
                    now1 = Integer.parseInt(numberField1.getHint().toString());
                    numberField1.setText(numberField1.getHint().toString());
                }

                if (!numberField2.getText().toString().equals(""))
                    now2 = Integer.parseInt(numberField2.getText().toString());
                else {
                    now2 = Integer.parseInt(numberField2.getHint().toString());
                    numberField2.setText(numberField2.getHint().toString());
                }
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
                numberField1.setHint("0");
                numberField2.setHint("0");
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
    protected void onStop() {
        super.onStop();
        saveText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
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
                    numberField1.setText("");
                    numberField2.setText("");
                    SharedPreferences scoreSharPref = getSharedPreferences("Score.txt", MODE_PRIVATE);
                    Editor editor = scoreSharPref.edit().clear();
                    editor.apply();
                    arrPlayer1.clear();
                    arrPlayer2.clear();
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
        TextFieldBoxes box1 = findViewById(R.id.text_box1);
        TextFieldBoxes box2 = findViewById(R.id.text_box2);
        if (mSharedPreferences.getInt(THEME_SELECTED, 0) == R.style.AppThemeDark) {
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, calc, buttonColor);
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, buttonAdd, buttonColor);
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, buttonNew, buttonColor);
            ColorPrefUtil.changeBackgroundColorOfSingleView(this, linearLayout, colorSelected);
            box1.setPanelBackgroundColor(getColor(R.color.colorGradientStartDark));
            box2.setPanelBackgroundColor(getColor(R.color.colorGradientStartDark));
            numberPicker.setDividerColor(getColor(R.color.colorAccentAma));
            numberPicker.setTextColor(getColor(R.color.white));
            numberPicker.setSelectedTextColor(getColor(R.color.white));
            resideMenu.setBackground(R.drawable.dark2);
        }
    }
}
