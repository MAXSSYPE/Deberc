package app.first.my_deb;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class App extends Application {

    private static AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(
                this,
                initializationStatus -> {
                });
        appOpenManager = new AppOpenManager(this);
    }

}
