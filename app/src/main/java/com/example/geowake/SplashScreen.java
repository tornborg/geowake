package com.example.geowake;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class SplashScreen extends MainActivity {
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                SplashScreen.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        SplashScreen.this.finish();
        super.onBackPressed();
    }}