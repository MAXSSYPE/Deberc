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

public class Activity3 extends AppCompatActivity {
    private static TextView resultField1;
    private static TextView resultField2;
    private static TextView resultField3;
    private EditText numberField1;
    private EditText numberField2;
    private EditText numberField3;
    private static TextView name1;
    private static TextView name2;
    private static TextView name3;
    private final String score = "app.first.my_deb.score3";
    private final Intent intent2 = new Intent(score);
    private int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        MobileAds.initialize(this, "ca-app-pub-5807744662830254~2797692226");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        resultField1 = findViewById(R.id.resultField1);
        resultField2 = findViewById(R.id.resultField2);
        resultField3 = findViewById(R.id.resultField3);
        numberField1 = findViewById(R.id.numberField1);
        numberField2 = findViewById(R.id.numberField2);
        numberField3 = findViewById(R.id.numberField3);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        context = this;
        if (MainActivity.getS() == 1) {
            loadText(context);
        }
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view)
    {
        try{
            int prev1 = Integer.valueOf(resultField1.getText().toString());
            int prev2 = Integer.valueOf(resultField2.getText().toString());
            int prev3 = Integer.valueOf(resultField3.getText().toString());
            int now1 = Integer.valueOf(numberField1.getText().toString());
            int now2 = Integer.valueOf(numberField2.getText().toString());
            int now3 = Integer.valueOf(numberField3.getText().toString());
            resultField1.setText(String.valueOf(prev1 + now1));
            resultField2.setText(String.valueOf(prev2 + now2));
            resultField3.setText(String.valueOf(prev3 + now3));
            SharedPreferences.Editor editor;
            SharedPreferences scoreSharPref;
            if (count == 1 && Score2x2.s == 0)
            {
                scoreSharPref = getSharedPreferences("Score3.txt", MODE_APPEND);
                editor = scoreSharPref.edit().clear();
            }
            else
            {
                scoreSharPref = getSharedPreferences("Score3.txt", MODE_APPEND);
                editor = scoreSharPref.edit();
            }
            Score3.s = 1;
            if (count % 8 == 1) {
                editor.putString("val1", numberField1.getText().toString());
                editor.putString("val2", numberField2.getText().toString());
                editor.putString("val3", numberField3.getText().toString());
                editor.putString("n1", String.valueOf(count));
                count++;
            } else if (count % 8 == 2) {
                editor.putString("val4", numberField1.getText().toString());
                editor.putString("val5", numberField2.getText().toString());
                editor.putString("val6", numberField3.getText().toString());
                editor.putString("n2", String.valueOf(count));
                count++;
            } else if (count % 8 == 3) {
                editor.putString("val7", numberField1.getText().toString());
                editor.putString("val8", numberField2.getText().toString());
                editor.putString("val9", numberField3.getText().toString());
                editor.putString("n3", String.valueOf(count));
                count++;
            } else if (count % 8 == 4) {
                editor.putString("val10", numberField1.getText().toString());
                editor.putString("val11", numberField2.getText().toString());
                editor.putString("val12", numberField3.getText().toString());
                editor.putString("n4", String.valueOf(count));
                count++;
            } else if (count % 8 == 5) {
                editor.putString("val13", numberField1.getText().toString());
                editor.putString("val14", numberField2.getText().toString());
                editor.putString("val15", numberField3.getText().toString());
                editor.putString("n5", String.valueOf(count));
                count++;
            } else if (count % 8 == 6) {
                editor.putString("val16", numberField1.getText().toString());
                editor.putString("val17", numberField2.getText().toString());
                editor.putString("val18", numberField3.getText().toString());
                editor.putString("n6", String.valueOf(count));
                count++;
            } else if (count % 8 == 7) {
                editor.putString("val19", numberField1.getText().toString());
                editor.putString("val20", numberField2.getText().toString());
                editor.putString("val21", numberField3.getText().toString());
                editor.putString("n7", String.valueOf(count));
                count++;
            } else if (count % 8 == 0) {
                editor.putString("val22", numberField1.getText().toString());
                editor.putString("val23", numberField2.getText().toString());
                editor.putString("val24", numberField3.getText().toString());
                editor.putString("n8", String.valueOf(count));
                count++;
            }
            editor.apply();
            numberField1.setText("");
            numberField2.setText("");
            numberField3.setText("");
        }
        catch (Exception ignored)
        {

        }
    }
    public void onSuppClick(View view)
    {
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
    protected void onStop() {
        super.onStop();
        saveText();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void saveText() {
        sPref = getSharedPreferences("Save3.txt", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("res1", resultField1.getText().toString());
        ed.putString("res2", resultField2.getText().toString());
        ed.putString("res3", resultField3.getText().toString());
        ed.putString("name1", name1.getText().toString());
        ed.putString("name2", name2.getText().toString());
        ed.putString("name3", name3.getText().toString());
        ed.putInt("count", count);
        ed.apply();
    }

    private void loadText(Context context) {
        sPref = context.getSharedPreferences("Save3.txt", MODE_PRIVATE);
        name1.setText(sPref.getString("name1", ""));
        name2.setText(sPref.getString("name2", ""));
        name3.setText(sPref.getString("name3", ""));
        resultField1.setText(sPref.getString("res1", ""));
        resultField2.setText(sPref.getString("res2", ""));
        resultField3.setText(sPref.getString("res3", ""));
        count = sPref.getInt("count", 0);
    }
}
