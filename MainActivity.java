package com.example.gr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textView;
    private static final float ALPHA = 0.8f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        textView = findViewById(R.id.text_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Listenerの登録
        Sensor accel = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float sensorY, sensorZ, low_senserY, low_senserZ;
        double theta;
        String view_theta;
        int int_theta;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            sensorY = event.values[1];
            sensorZ = event.values[2];
            low_senserY = sensorY * ALPHA + event.values[1] * (1f -ALPHA);
            low_senserZ = sensorZ * ALPHA + event.values[2] * (1f -ALPHA);

            theta = Math.atan(low_senserY/low_senserZ) * 180 / Math.PI;
            int_theta = (int)Math.abs(theta);
            if(int_theta < 10){
                view_theta = "0" + String.valueOf(int_theta);
            } else {
                view_theta = String.valueOf(int_theta);
            }
            String strTmp = view_theta + "度";

            textView.setText(strTmp);

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

