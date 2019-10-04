package app.first.my_deb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static app.first.my_deb.MainActivity.context;
import static app.first.my_deb.MainActivity.sPref;

public class Score4 extends AppCompatActivity {
    static int s;
    private TextView val1;
    private TextView val2;
    private TextView val3;
    private TextView val4;
    private TextView val5;
    private TextView val6;
    private TextView val7;
    private TextView val8;
    private TextView val9;
    private TextView val10;
    private TextView val11;
    private TextView val12;
    private TextView val13;
    private TextView val14;
    private TextView val15;
    private TextView val16;
    private TextView val17;
    private TextView val18;
    private TextView val19;
    private TextView val20;
    private TextView val21;
    private TextView val22;
    private TextView val23;
    private TextView val24;
    private TextView val25;
    private TextView val26;
    private TextView val27;
    private TextView val28;
    private TextView val29;
    private TextView val30;
    private TextView val31;
    private TextView val32;
    private TextView n1;
    private TextView n2;
    private TextView n3;
    private TextView n4;
    private TextView n5;
    private TextView n6;
    private TextView n7;
    private TextView n8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score4);
        val1 = findViewById(R.id.s11);
        val2 = findViewById(R.id.s12);
        val3 = findViewById(R.id.s13);
        val4 = findViewById(R.id.s14);
        val5 = findViewById(R.id.s21);
        val6 = findViewById(R.id.s22);
        val7 = findViewById(R.id.s23);
        val8 = findViewById(R.id.s24);
        val9 = findViewById(R.id.s31);
        val10 = findViewById(R.id.s32);
        val11 = findViewById(R.id.s33);
        val12 = findViewById(R.id.s34);
        val13 = findViewById(R.id.s41);
        val14 = findViewById(R.id.s42);
        val15 = findViewById(R.id.s43);
        val16 = findViewById(R.id.s44);
        val17 = findViewById(R.id.s51);
        val18 = findViewById(R.id.s52);
        val19 = findViewById(R.id.s53);
        val20 = findViewById(R.id.s54);
        val21 = findViewById(R.id.s61);
        val22 = findViewById(R.id.s62);
        val23 = findViewById(R.id.s63);
        val24 = findViewById(R.id.s64);
        val25 = findViewById(R.id.s71);
        val26 = findViewById(R.id.s72);
        val27 = findViewById(R.id.s73);
        val28 = findViewById(R.id.s74);
        val29 = findViewById(R.id.s81);
        val30 = findViewById(R.id.s82);
        val31 = findViewById(R.id.s83);
        val32 = findViewById(R.id.s84);
        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);
        n6 = findViewById(R.id.n6);
        n7 = findViewById(R.id.n7);
        n8 = findViewById(R.id.n8);
        context = this;
        if (s == 1)
        {
            loadText(context);
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
    @Override
    public void onBackPressed() {
        try {
            String ACTION4 = "app.first.my_deb.SHOW_4_ACTIVITY";
            Intent intent = new Intent(ACTION4);
            startActivity(intent);
            MainActivity.s = 1;
            finish();
        } catch (Exception ignored) {
        }
    }
    @SuppressLint("WrongConstant")
    private void loadText(Context context) {
        sPref = context.getSharedPreferences("Score4.txt", MODE_APPEND);
        val1.setText(sPref.getString("val1", ""));
        val2.setText(sPref.getString("val2", ""));
        val3.setText(sPref.getString("val3", ""));
        val4.setText(sPref.getString("val4", ""));
        val5.setText(sPref.getString("val5", ""));
        val6.setText(sPref.getString("val6", ""));
        val7.setText(sPref.getString("val7", ""));
        val8.setText(sPref.getString("val8", ""));
        val9.setText(sPref.getString("val9", ""));
        val10.setText(sPref.getString("val10", ""));
        val11.setText(sPref.getString("val11", ""));
        val12.setText(sPref.getString("val12", ""));
        val13.setText(sPref.getString("val13", ""));
        val14.setText(sPref.getString("val14", ""));
        val15.setText(sPref.getString("val15", ""));
        val16.setText(sPref.getString("val16", ""));
        val17.setText(sPref.getString("val17", ""));
        val18.setText(sPref.getString("val18", ""));
        val19.setText(sPref.getString("val19", ""));
        val20.setText(sPref.getString("val20", ""));
        val21.setText(sPref.getString("val21", ""));
        val22.setText(sPref.getString("val22", ""));
        val23.setText(sPref.getString("val23", ""));
        val24.setText(sPref.getString("val24", ""));
        val25.setText(sPref.getString("val25", ""));
        val26.setText(sPref.getString("val26", ""));
        val27.setText(sPref.getString("val27", ""));
        val28.setText(sPref.getString("val28", ""));
        val29.setText(sPref.getString("val29", ""));
        val30.setText(sPref.getString("val30", ""));
        val31.setText(sPref.getString("val31", ""));
        val32.setText(sPref.getString("val32", ""));
        n1.setText(sPref.getString("n1", ""));
        n2.setText(sPref.getString("n2", ""));
        n3.setText(sPref.getString("n3", ""));
        n4.setText(sPref.getString("n4", ""));
        n5.setText(sPref.getString("n5", ""));
        n6.setText(sPref.getString("n6", ""));
        n7.setText(sPref.getString("n7", ""));
        n8.setText(sPref.getString("n8", ""));
    }
    @SuppressLint("WrongConstant")
    private void saveText() {
        sPref = context.getSharedPreferences("Score4.txt", MODE_APPEND);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("val1", val1.getText().toString());
        editor.putString("val2", val2.getText().toString());
        editor.putString("val3", val3.getText().toString());
        editor.putString("val4", val4.getText().toString());
        editor.putString("val5", val5.getText().toString());
        editor.putString("val6", val6.getText().toString());
        editor.putString("val7", val7.getText().toString());
        editor.putString("val8", val8.getText().toString());
        editor.putString("val9", val9.getText().toString());
        editor.putString("val10", val10.getText().toString());
        editor.putString("val11", val11.getText().toString());
        editor.putString("val12", val12.getText().toString());
        editor.putString("val13", val13.getText().toString());
        editor.putString("val14", val14.getText().toString());
        editor.putString("val15", val15.getText().toString());
        editor.putString("val16", val16.getText().toString());
        editor.putString("val17", val17.getText().toString());
        editor.putString("val18", val18.getText().toString());
        editor.putString("val19", val19.getText().toString());
        editor.putString("val20", val20.getText().toString());
        editor.putString("val21", val21.getText().toString());
        editor.putString("val22", val22.getText().toString());
        editor.putString("val23", val23.getText().toString());
        editor.putString("val24", val24.getText().toString());
        editor.putString("val25", val25.getText().toString());
        editor.putString("val26", val26.getText().toString());
        editor.putString("val27", val27.getText().toString());
        editor.putString("val28", val28.getText().toString());
        editor.putString("val29", val29.getText().toString());
        editor.putString("val30", val30.getText().toString());
        editor.putString("val31", val31.getText().toString());
        editor.putString("val32", val32.getText().toString());
        editor.putString("n1", n1.getText().toString());
        editor.putString("n2", n2.getText().toString());
        editor.putString("n3", n3.getText().toString());
        editor.putString("n4", n4.getText().toString());
        editor.putString("n5", n5.getText().toString());
        editor.putString("n6", n6.getText().toString());
        editor.putString("n7", n7.getText().toString());
        editor.putString("n8", n8.getText().toString());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText();
    }
}
