package app.first.my_deb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static app.first.my_deb.MainActivity.context;
import static app.first.my_deb.MainActivity.sPref;

public class Activity4 extends AppCompatActivity {
    private static TextView resultField1;
    private static TextView resultField2;
    private static TextView resultField3;
    private static TextView resultField4;
    private EditText numberField1;
    private EditText numberField2;
    private EditText numberField3;
    private EditText numberField4;
    private static TextView name1;
    private static TextView name2;
    private static TextView name3;
    private static TextView name4;
    private final String score = "app.first.my_deb.score4";
    private final Intent intent2 = new Intent(score);
    private int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
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
        context = this;
        if (MainActivity.getS() == 1) {
            loadText(context);
        }
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        try {
            int prev1 = Integer.valueOf(resultField1.getText().toString());
            int prev2 = Integer.valueOf(resultField2.getText().toString());
            int prev3 = Integer.valueOf(resultField3.getText().toString());
            int prev4 = Integer.valueOf(resultField4.getText().toString());
            int now1 = Integer.valueOf(numberField1.getText().toString());
            int now2 = Integer.valueOf(numberField2.getText().toString());
            int now3 = Integer.valueOf(numberField3.getText().toString());
            int now4 = Integer.valueOf(numberField4.getText().toString());
            resultField1.setText(String.valueOf(prev1 + now1));
            resultField2.setText(String.valueOf(prev2 + now2));
            resultField3.setText(String.valueOf(prev3 + now3));
            resultField4.setText(String.valueOf(prev4 + now4));
            SharedPreferences.Editor editor;
            SharedPreferences scoreSharPref;
            if (count == 1 && Score2x2.s == 0)
            {
                scoreSharPref = getSharedPreferences("Score4.txt", MODE_APPEND);
                editor = scoreSharPref.edit().clear();
            }
            else
            {
                scoreSharPref = getSharedPreferences("Score4.txt", MODE_APPEND);
                editor = scoreSharPref.edit();
            }
            Score4.s = 1;
            if (count % 8 == 1) {
                editor.putString("val1", numberField1.getText().toString());
                editor.putString("val2", numberField2.getText().toString());
                editor.putString("val3", numberField3.getText().toString());
                editor.putString("val4", numberField4.getText().toString());
                editor.putString("n1", String.valueOf(count));
                count++;
            } else if (count % 8 == 2) {
                editor.putString("val5", numberField1.getText().toString());
                editor.putString("val6", numberField2.getText().toString());
                editor.putString("val7", numberField3.getText().toString());
                editor.putString("val8", numberField4.getText().toString());
                editor.putString("n2", String.valueOf(count));
                count++;
            } else if (count % 8 == 3) {
                editor.putString("val9", numberField1.getText().toString());
                editor.putString("val10", numberField2.getText().toString());
                editor.putString("val11", numberField3.getText().toString());
                editor.putString("val12", numberField4.getText().toString());
                editor.putString("n3", String.valueOf(count));
                count++;
            } else if (count % 8 == 4) {
                editor.putString("val13", numberField1.getText().toString());
                editor.putString("val14", numberField2.getText().toString());
                editor.putString("val15", numberField3.getText().toString());
                editor.putString("val16", numberField4.getText().toString());
                editor.putString("n4", String.valueOf(count));
                count++;
            } else if (count % 8 == 5) {
                editor.putString("val17", numberField1.getText().toString());
                editor.putString("val18", numberField2.getText().toString());
                editor.putString("val19", numberField3.getText().toString());
                editor.putString("val20", numberField4.getText().toString());
                editor.putString("n5", String.valueOf(count));
                count++;
            } else if (count % 8 == 6) {
                editor.putString("val21", numberField1.getText().toString());
                editor.putString("val22", numberField2.getText().toString());
                editor.putString("val23", numberField3.getText().toString());
                editor.putString("val24", numberField4.getText().toString());
                editor.putString("n6", String.valueOf(count));
                count++;
            } else if (count % 8 == 7) {
                editor.putString("val25", numberField1.getText().toString());
                editor.putString("val26", numberField2.getText().toString());
                editor.putString("val27", numberField3.getText().toString());
                editor.putString("val28", numberField4.getText().toString());
                editor.putString("n7", String.valueOf(count));
                count++;
            } else if (count % 8 == 0) {
                editor.putString("val29", numberField1.getText().toString());
                editor.putString("val30", numberField2.getText().toString());
                editor.putString("val31", numberField3.getText().toString());
                editor.putString("val32", numberField4.getText().toString());
                editor.putString("n8", String.valueOf(count));
                count++;
            }
            editor.apply();
            numberField1.setText("");
            numberField2.setText("");
            numberField3.setText("");
            numberField4.setText("");
        } catch (Exception ignored) {

        }
    }

    public void onSuppClick(View view) {
        startActivityForResult(intent2, 1);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        String main = "app.first.my_deb.main";
        Intent intent1 = new Intent(main);
        startActivity(intent1);
        finish();
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

    private void saveText() {
        sPref = getSharedPreferences("Save4.txt", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("res1", resultField1.getText().toString());
        ed.putString("res2", resultField2.getText().toString());
        ed.putString("res3", resultField3.getText().toString());
        ed.putString("res4", resultField4.getText().toString());
        ed.putString("name1", name1.getText().toString());
        ed.putString("name2", name2.getText().toString());
        ed.putString("name3", name3.getText().toString());
        ed.putString("name4", name4.getText().toString());
        ed.putInt("count", count);
        ed.apply();
    }

    private void loadText(Context context) {
        sPref = context.getSharedPreferences("Save4.txt", MODE_PRIVATE);
        name1.setText(sPref.getString("name1", ""));
        name2.setText(sPref.getString("name2", ""));
        name3.setText(sPref.getString("name3", ""));
        name4.setText(sPref.getString("name4", ""));
        resultField1.setText(sPref.getString("res1", ""));
        resultField2.setText(sPref.getString("res2", ""));
        resultField3.setText(sPref.getString("res3", ""));
        resultField4.setText(sPref.getString("res4", ""));
        count = sPref.getInt("count", 0);
    }
}
