package com.example.followyourshifts;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.followyourshifts.Objects.Shift;
import com.example.followyourshifts.Objects.Workplace;

import java.util.ArrayList;

public class SignalGenerator {
    private static SignalGenerator instance;
    private Context context;
    private static Vibrator vibrator;
    private static MediaPlayer backgroundMediaPlayer;
    private static MediaPlayer eventsMediaPlayer;
   // private static DataManager dataManager;

    private SignalGenerator(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGenerator(context);
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            //dataManager = new DataManager();
            //backgroundMediaPlayer = MediaPlayer.create(context, R.raw.birds_background);
            //eventsMediaPlayer = MediaPlayer.create(context, R.raw.crash);
        }
    }

//    public static ArrayList<Workplace>  getWorkplaces(){
//        return dataManager.getWorkPlacesAndShifts();
//    }
//    public static ArrayList<Shift>  getShifts(){
//        return dataManager.getShifts();
//    }

    public static SignalGenerator getInstance() {
        return instance;
    }

    public void toast(String text, int length) {
        Toast.makeText(context, text, length).show();
    }

    public void playSound(int soundId) {
        eventsMediaPlayer.release();
        eventsMediaPlayer = MediaPlayer.create(context, soundId);
        eventsMediaPlayer.start();
    }

    public void playBackgroundSound(int soundId) {
        backgroundMediaPlayer = MediaPlayer.create(context, soundId);
        backgroundMediaPlayer.setLooping(true);
        backgroundMediaPlayer.start();
    }

    public void pauseBackgroundSound(int soundId) {
        backgroundMediaPlayer.pause();
    }

    public void vibrate(long ms) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(ms);
        }
    }


}
