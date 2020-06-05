package app.first.my_deb;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.shawnlin.numberpicker.NumberPicker;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;

public class Activity2x2 extends Activity {
    private ArrayList<String> arrPlayer1 = new ArrayList<>();
    private ArrayList<String> arrPlayer2 = new ArrayList<>();
    private TextView resultField1;
    private TextView resultField2;
    private EditText numberField1;
    private EditText numberField2;
    private TextView name1;
    private TextView name2;
    private ResideMenu resideMenu;
    private NumberPicker numberPicker;

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
        setContentView(R.layout.activity_2x2);
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
        numberPicker = findViewById(R.id.number_picker);
        final String[] data = {"162", "182", "202", "212", "222", "232", "242", "252", "262", "272", "282", "302", "322"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(13);
        numberPicker.setDisplayedValues(data);
        if (supp.equals("dark")) {
            numberPicker.setDividerColor(getColor(R.color.white));
            numberPicker.setTextColor(getColor(R.color.white));
            numberPicker.setSelectedTextColor(getColor(R.color.white));
            name1.setTextColor(getColor(R.color.white));
            name2.setTextColor(getColor(R.color.white));
            numberField1.setHintTextColor(getColor(R.color.grey));
            numberField2.setHintTextColor(getColor(R.color.grey));
            numberField1.setTextColor(getColor(R.color.white));
            numberField2.setTextColor(getColor(R.color.white));
            resultField1.setHintTextColor(getColor(R.color.grey));
            resultField2.setHintTextColor(getColor(R.color.grey));
            name1.setHintTextColor(getColor(R.color.grey));
            name2.setHintTextColor(getColor(R.color.grey));
        }
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!(numberField1.getText().toString().length() == 0 && numberField2.getText().toString().length() == 0) || !(numberField1.getText().toString().length() != 0 && numberField2.getText().toString().length() != 0)) {
                        if (numberField1.getText().toString().length() != 0 && Integer.valueOf(numberField1.getText().toString()) > 0) {
                            if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.valueOf(numberField1.getText().toString()) >= 0 && Integer.valueOf(numberField1.getText().toString()) <= Integer.valueOf(data[numberPicker.getValue() - 1])) {
                                numberField2.setHint(String.valueOf(Integer.valueOf(data[numberPicker.getValue() - 1]) - Integer.valueOf(numberField1.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                        if (numberField2.getText().toString().length() != 0 && Integer.valueOf(numberField2.getText().toString()) > 0) {
                            if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.valueOf(numberField2.getText().toString()) >= 0 && Integer.valueOf(numberField2.getText().toString()) <= Integer.valueOf(data[numberPicker.getValue() - 1])) {
                                numberField1.setHint(String.valueOf(Integer.valueOf(data[numberPicker.getValue() - 1]) - Integer.valueOf(numberField2.getText().toString())));
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
                        if (numberField1.getText().toString().length() != 0 && Integer.valueOf(numberField1.getText().toString()) > 0) {
                            if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.valueOf(numberField1.getText().toString()) >= 0 && Integer.valueOf(numberField1.getText().toString()) <= Integer.valueOf(data[picker.getValue() - 1])) {
                                numberField2.setHint(String.valueOf(Integer.valueOf(data[picker.getValue() - 1]) - Integer.valueOf(numberField1.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                        if (numberField2.getText().toString().length() != 0 && Integer.valueOf(numberField2.getText().toString()) > 0) {
                            if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.valueOf(numberField2.getText().toString()) >= 0 && Integer.valueOf(numberField2.getText().toString()) <= Integer.valueOf(data[picker.getValue() - 1])) {
                                numberField1.setHint(String.valueOf(Integer.valueOf(data[picker.getValue() - 1]) - Integer.valueOf(numberField2.getText().toString())));
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
                        if (numberField1.getText().toString().length() != 0 && Integer.valueOf(numberField1.getText().toString()) > 0) {
                            if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.valueOf(numberField1.getText().toString()) >= 0 && Integer.valueOf(numberField1.getText().toString()) <= Integer.valueOf(data[view.getValue() - 1])) {
                                numberField2.setHint(String.valueOf(Integer.valueOf(data[view.getValue() - 1]) - Integer.valueOf(numberField1.getText().toString())));
                            } else {
                                numberField1.setHint("0");
                                numberField2.setHint("0");
                            }
                        }
                        if (numberField2.getText().toString().length() != 0 && Integer.valueOf(numberField2.getText().toString()) > 0) {
                            if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.valueOf(numberField2.getText().toString()) >= 0 && Integer.valueOf(numberField2.getText().toString()) <= Integer.valueOf(data[view.getValue() - 1])) {
                                numberField1.setHint(String.valueOf(Integer.valueOf(data[view.getValue() - 1]) - Integer.valueOf(numberField2.getText().toString())));
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

        LinearLayout linearLayout = findViewById(R.id.main);
        if (supp.equals("dark"))
            linearLayout.setBackground(getDrawable(R.drawable.gradient_animation_dark));
        AnimationDrawable animDrawable = (AnimationDrawable) linearLayout.getBackground();
        animDrawable.setEnterFadeDuration(10);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();
        loadText(this);

        numberField1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!numberField1.getText().toString().equals("") && !numberField1.getText().toString().equals("-") && Integer.valueOf(numberField1.getText().toString()) >= 0 && Integer.valueOf(numberField1.getText().toString()) <= Integer.valueOf(data[numberPicker.getValue() - 1])) {
                        numberField2.setHint(String.valueOf(Integer.valueOf(data[numberPicker.getValue() - 1]) - Integer.valueOf(numberField1.getText().toString())));
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
                    if (!numberField2.getText().toString().equals("") && !numberField2.getText().toString().equals("-") && Integer.valueOf(numberField2.getText().toString()) >= 0 && Integer.valueOf(numberField2.getText().toString()) <= Integer.valueOf(data[numberPicker.getValue() - 1])) {
                        numberField1.setHint(String.valueOf(Integer.valueOf(data[numberPicker.getValue() - 1]) - Integer.valueOf(numberField2.getText().toString())));
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

        String[] titles = {getResources().getString(R.string.new_game), getResources().getString(R.string.count), getResources().getString(R.string.settings), getResources().getString(R.string.vs4), getResources().getString(R.string.vs3), getResources().getString(R.string.vs2)};
        int[] icon = {R.drawable.newg, R.drawable.score, R.drawable.settings, R.drawable.for1, R.drawable.for2, R.drawable.for3};
        ResideMenuItem item1 = new ResideMenuItem(this, icon[0], titles[0]);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Activity2x2.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.new_game))
                        .setMessage(getResources().getString(R.string.sure))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resideMenu.closeMenu();
                                resultField1.setText("0");
                                resultField2.setText("0");
                                numberField1.setText("");
                                numberField2.setText("");
                                name1.setText("");
                                name2.setText("");
                                SharedPreferences scoreSharPref = getSharedPreferences("Score2x2.txt", MODE_PRIVATE);
                                Editor editor = scoreSharPref.edit().clear();
                                editor.apply();
                                arrPlayer1.clear();
                                arrPlayer2.clear();
                            }
                        }).setNegativeButton(getResources().getString(R.string.no), null).show();
            }
        });
        resideMenu.addMenuItem(item1, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item2 = new ResideMenuItem(this, icon[1], titles[1]);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.score2x2"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        resideMenu.addMenuItem(item2, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item3 = new ResideMenuItem(this, icon[2], titles[2]);
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.settings"));
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
                finish();
            }
        });
        resideMenu.addMenuItem(item3, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item4 = new ResideMenuItem(this, icon[3], titles[3]);
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.SHOW_4_ACTIVITY"));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item4, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item5 = new ResideMenuItem(this, icon[4], titles[4]);
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.SHOW_3_ACTIVITY"));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item5, ResideMenu.DIRECTION_LEFT);

        ResideMenuItem item6 = new ResideMenuItem(this, icon[5], titles[5]);
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("app.first.my_deb.SHOW_2_ACTIVITY"));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.exit_to_left);
            }
        });
        resideMenu.addMenuItem(item6, ResideMenu.DIRECTION_LEFT);

        if (StartActivity.flag) {
            StartActivity.flag = false;
            resideMenu.openMenu(0);
        }
    }

    public void onClick(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            assert imm != null;
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (!(numberField1.getText().toString().equals("") && numberField2.getText().toString().equals("")))
            try {
                int prev1 = Integer.valueOf(resultField1.getText().toString());
                int prev2 = Integer.valueOf(resultField2.getText().toString());
                int now1 = 0;
                int now2 = 0;
                if (!numberField1.getText().toString().equals(""))
                    now1 = Integer.valueOf(numberField1.getText().toString());
                else {
                    now1 = Integer.valueOf(numberField1.getHint().toString());
                    numberField1.setText(numberField1.getHint().toString());
                }

                if (!numberField2.getText().toString().equals(""))
                    now2 = Integer.valueOf(numberField2.getText().toString());
                else {
                    now2 = Integer.valueOf(numberField2.getHint().toString());
                    numberField2.setText(numberField2.getHint().toString());
                }
                resultField1.setText(String.valueOf(prev1 + now1));
                resultField2.setText(String.valueOf(prev2 + now2));
                SharedPreferences scoreSharPref = getSharedPreferences("Score2x2.txt", MODE_PRIVATE);
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
                numberPicker.setValue(1);
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
        SharedPreferences sPref = getSharedPreferences("Save2x2.txt", MODE_PRIVATE);
        Editor ed = sPref.edit();
        ed.putString("res1", resultField1.getText().toString());
        ed.putString("res2", resultField2.getText().toString());
        ed.putString("name1", name1.getText().toString());
        ed.putString("name2", name2.getText().toString());
        ed.apply();
    }

    private void loadText(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("Save2x2.txt", MODE_PRIVATE);
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
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.exitTitle))
                    .setMessage(getResources().getString(R.string.exit))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton(getResources().getString(R.string.no), null).show();
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
        new AlertDialog.Builder(Activity2x2.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.new_game))
                .setMessage(getResources().getString(R.string.sure))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultField1.setText("0");
                        resultField2.setText("0");
                        numberField1.setText("");
                        numberField2.setText("");
                        SharedPreferences scoreSharPref = getSharedPreferences("Score2x2.txt", MODE_PRIVATE);
                        Editor editor = scoreSharPref.edit().clear();
                        editor.apply();
                        arrPlayer1.clear();
                        arrPlayer2.clear();
                    }
                }).setNegativeButton(getResources().getString(R.string.no), null).show();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
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
}