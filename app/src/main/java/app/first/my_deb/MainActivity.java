package app.first.my_deb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    static int s = 1;
    private int spec = 0;
    private static final String ACTION2 = "app.first.my_deb.SHOW_2_ACTIVITY";
    private static final String ACTION3 = "app.first.my_deb.SHOW_3_ACTIVITY";
    private static final String ACTION2x2 = "app.first.my_deb.SHOW_2x2_ACTIVITY";
    private static final String ACTION4 = "app.first.my_deb.SHOW_4_ACTIVITY";
    static SharedPreferences sPref;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radG1 = findViewById(R.id.radG1);
        radG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg, int id) {
                switch (id) {
                    case R.id.n2:
                        spec = 1;
                        break;
                    case R.id.n3:
                        spec = 2;
                        break;
                    case R.id.n2x2:
                        spec = 3;
                        break;
                    case R.id.n4:
                        spec = 4;
                        break;
                }
            }
        });
    }

    public void onClick(View view) {
        if (spec == 0) {
            Toast toast = Toast.makeText(this, "Выберите количество игроков", Toast.LENGTH_SHORT);
            toast.show();
        } else if (spec == 1) {
            Intent intent1 = new Intent(ACTION2);
            s = 0;
            Score.s = 0;
            startActivity(intent1);
            finish();
        } else if (spec == 2) {
            Intent intent1 = new Intent(ACTION3);
            s = 0;
            Score3.s = 0;
            startActivity(intent1);
            finish();
        } else if (spec == 3) {
            Intent intent1 = new Intent(ACTION2x2);
            s = 0;
            Score2x2.s = 0;
            startActivity(intent1);
            finish();
        } else if (spec == 4) {
            Intent intent1 = new Intent(ACTION4);
            s = 0;
            Score4.s = 0;
            startActivity(intent1);
            finish();
        }
    }

    public void onCountinue2Click(View view) {
        try {
            Intent intent = new Intent(ACTION2);
            startActivity(intent);
            s = 1;
            finish();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Нет сохранённых игр на 2", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onCountinue2x2Click(View view) {
        try {
            Intent intent = new Intent(ACTION2x2);
            startActivity(intent);
            s = 1;
            finish();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Нет сохранённых игр 2x2", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onCountinue3Click(View view) {
        try {
            Intent intent = new Intent(ACTION3);
            startActivity(intent);
            s = 1;
            finish();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Нет сохранённых игр на 3", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onCountinue4Click(View view) {
        try {
            Intent intent = new Intent(ACTION4);
            startActivity(intent);
            s = 1;
            finish();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Нет сохранённых игр на 4", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

    public static int getS() {
        return s;
    }
}
