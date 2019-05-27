package com.example.geowake;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

public class AlarmScreen extends AppCompatActivity implements SensorEventListener {
    private long[] vibPattern = {1000, 1000};
    private Vibrator vibrator;

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Button stopAlarm;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);
        vibrator = ((Vibrator) getSystemService(VIBRATOR_SERVICE));
        mp = MediaPlayer.create(this, R.raw.alarmsound);

        vibrator.vibrate(VibrationEffect.createWaveform(vibPattern, 0));
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mp.start();
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        stopAlarm = (Button) findViewById(R.id.buttonStopAlarm);
        ((View) stopAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopAlarm.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                stopEvents();
            }
        });


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 10) {
                stopEvents();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    private void stopEvents() {
        vibrator.cancel();
        mSensorManager.unregisterListener(this);
        mp.stop();
        openMapsActivity();

    }

    public void onBackPressed() {

    }


}
