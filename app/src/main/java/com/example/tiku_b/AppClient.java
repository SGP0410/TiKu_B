package com.example.tiku_b;

import android.app.Application;
import android.content.SharedPreferences;

public class AppClient extends Application {
    private static SharedPreferences sharedPreferences = null;
    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("deploy" , MODE_PRIVATE);
        super.onCreate();
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
}
