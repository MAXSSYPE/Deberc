package app.first.my_deb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.ScrollView;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

import static app.first.my_deb.StartActivity.THEME_SELECTED;
import static app.first.my_deb.StartActivity.mSharedPreferences;

public class Score extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTheme();
        Slidr.attach(this);
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
        SharedPreferences sPref = context.getSharedPreferences("Score.txt", MODE_PRIVATE);
        ArrayList<String> pl1 = new Gson().fromJson(sPref.getString("pl1", ""), new TypeToken<ArrayList<String>>() {
        }.getType());
        ArrayList<String> pl2 = new Gson().fromJson(sPref.getString("pl2", ""), new TypeToken<ArrayList<String>>() {
        }.getType());

        DataTable dataTable = findViewById(R.id.data_table);
        DataTableHeader header = new DataTableHeader.Builder()
                .item(getString(R.string.gamer1), 1)
                .item(getString(R.string.gamer2), 1).build();

        ArrayList<DataTableRow> rows = new ArrayList<>();
        if (pl1 != null && pl2 != null && !pl1.isEmpty() && !pl2.isEmpty()) {
            for (int i = 0; i < pl1.size(); i++) {
                DataTableRow row = new DataTableRow.Builder()
                        .value(pl1.get(i))
                        .value(pl2.get(i)).build();
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

    private void setTheme() {
        ScrollView scrollView = findViewById(R.id.main);
        if (mSharedPreferences.getInt(THEME_SELECTED, 0) == R.style.AppThemeDark) {
            scrollView.setBackground(getDrawable(R.drawable.field_dark));
            DataTable dataTable = findViewById(R.id.data_table);
            dataTable.setHeaderBackgroundColor(R.color.colorGradientStartDark);
            dataTable.setRowBackgroundColor(R.color.colorGradientStartDark);
        }
    }
}