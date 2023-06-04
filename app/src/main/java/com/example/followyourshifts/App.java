package com.example.followyourshifts;

import android.app.Application;

import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.Utilities.SignalGenerator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalGenerator.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        // Call the method to update the database on app finish
        DataManager.updateDatabaseOnAppFinish();
    }
}
