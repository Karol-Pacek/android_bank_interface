package com.example.bankapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BlikTimer {

    public BlikTimer(Context con, TextView blikCode, TextView timeLeft, ProgressBar progressBar) {
        this.con = con;
        this.blikCode = blikCode;
        this.timeLeft = timeLeft;
        this.progressBar = progressBar;
    }

    private OnBlikRequestDetected callback;
    Context con;
    TextView blikCode;
    TextView timeLeft;
    ProgressBar progressBar;
    Timer timer;
    TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    /*@Override
    protected void onResume() {
        super.onResume();

        startTimer();
    }*/

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 1000ms the TimerTask will run every 1000ms
        timer.schedule(timerTask, 1000, 1000);
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        new VolleyController().checkBlik(con,blikCode,timeLeft,progressBar,callback);
                    }
                });
            }
        };
    }

    public void setOnBlikRequestDetected(OnBlikRequestDetected callback) {
        this.callback = callback;
    }
}
