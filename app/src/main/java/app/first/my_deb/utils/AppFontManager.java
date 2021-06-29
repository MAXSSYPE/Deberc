package app.first.my_deb.utils;

import androidx.appcompat.app.AppCompatActivity;

import app.first.my_deb.R;

public class AppFontManager {

    private AppCompatActivity appCompatActivity;

    public static final String ROBOTO = "font/roboto.ttf";
    public static final String DANCING = "font/dancing_script.ttf";
    public static final String FREDOKA = "font/fredoka.ttf";

    public AppFontManager(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public void setFont(String fontName){

        switch (fontName){

            case ROBOTO:{
                appCompatActivity.getTheme().applyStyle(R.style.Roboto, true);
                break;
            }

            case DANCING:{
                appCompatActivity.getTheme().applyStyle(R.style.DancingFont, true);
                break;
            }

            case FREDOKA:{
                appCompatActivity.getTheme().applyStyle(R.style.Fredoka, true);
                break;
            }
        }
    }
}