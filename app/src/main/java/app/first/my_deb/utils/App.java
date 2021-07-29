package app.first.my_deb.utils;

import android.app.Application;

import com.jaredrummler.cyanea.Cyanea;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*MobileAds.initialize(
                this,
                initializationStatus -> {
                });
        AppOpenManager appOpenManager = new AppOpenManager(this);*/
        Cyanea.init(this, getResources());
        Cyanea.setLoggingEnabled(true);
    }
}
