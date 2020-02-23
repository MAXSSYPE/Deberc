package app.first.my_deb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    private int count = 0;
    private final Handler handler = new Handler();
    static boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        onEverySecond.run();
    }

    private final Runnable onEverySecond = new Runnable() {
        public void run() {

            count++;
            int limit = 3;
            if (count == limit) {
                Intent intent = new Intent("app.first.my_deb.SHOW_2x2_ACTIVITY");
                startActivity(intent);
                overridePendingTransition(R.anim.appear, R.anim.disappear);
                finish();
            } else {
                handler.postDelayed(onEverySecond, 1000);
            }
        }
    };
}
