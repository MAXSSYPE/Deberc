package app.first.my_deb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

public class Score4 extends Activity {
    private LinearLayout linearLayout;

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
        setContentView(R.layout.activity_score);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Slidr.attach(this);
        linearLayout = findViewById(R.id.lin);
        ScrollView scrollView = findViewById(R.id.main);
        if (supp.equals("dark"))
            scrollView.setBackground(getDrawable(R.drawable.field_dark));
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

    @SuppressLint("WrongConstant")
    private void loadText(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("Score4.txt", MODE_APPEND);
        ArrayList<String> pl1 = new Gson().fromJson(sPref.getString("pl1", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        ArrayList<String> pl2 = new Gson().fromJson(sPref.getString("pl2", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        ArrayList<String> pl3 = new Gson().fromJson(sPref.getString("pl3", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        ArrayList<String> pl4 = new Gson().fromJson(sPref.getString("pl4", ""), new TypeToken<ArrayList<String>>() {
        }.getType());

        DataTable dataTable = findViewById(R.id.data_table);
        DataTableHeader header = new DataTableHeader.Builder()
                .item(getString(R.string.gamer1), 1)
                .item(getString(R.string.gamer2), 1)
                .item(getString(R.string.gamer3), 1)
                .item(getString(R.string.gamer4), 1).build();

        ArrayList<DataTableRow> rows = new ArrayList<>();
        if (pl1 != null && pl2 != null && pl3 != null && pl4 != null && !pl1.isEmpty() && !pl2.isEmpty() && !pl3.isEmpty() && !pl4.isEmpty()) {
            for (int i = 0; i < pl1.size(); i++) {
                DataTableRow row = new DataTableRow.Builder()
                        .value(pl1.get(i))
                        .value(pl2.get(i))
                        .value(pl3.get(i))
                        .value(pl4.get(i)).build();
                rows.add(row);
            }
        }
        dataTable.setHeader(header);
        dataTable.setRows(rows);
        dataTable.inflate(context);
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
