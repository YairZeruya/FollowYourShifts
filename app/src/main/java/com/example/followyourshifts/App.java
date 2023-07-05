package com.example.followyourshifts;

import android.app.Application;

import com.example.followyourshifts.Utilities.SignalGenerator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalGenerator.init(this);
    }


}
