package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.qtechnetworks.ptplatform.R;

import pub.devrel.easypermissions.EasyPermissions;

public class StepsActivity extends AppCompatActivity {

    TextView stepCounter;
    SensorManager sensorManager;
    Sensor sensor, sensorDetector;
    boolean isCounterSensorPresent, isDetectorSensorPresent;
    int stepCount = 0, stepDetector = 0;

    double previousMagnitude = 0;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        EasyPermissions.requestPermissions(this, "Please accept permission", 233, Manifest.permission.ACTIVITY_RECOGNITION);

        stepCounter = findViewById(R.id.step_counter);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null){
                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];

                    double magnitude = Math.sqrt(x*x + y*y + z*z);
                    double magnitudeDelta = magnitude - previousMagnitude;
                    previousMagnitude = magnitude;

                    if (magnitudeDelta > 6){
                        stepCount++;
                        stepCounter.setText(String.valueOf(stepCount));
                    }
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

//        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

//        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
//            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//            isCounterSensorPresent = true;
//        } else {
//            Toast.makeText(this, "Counter Sensor is not present", Toast.LENGTH_SHORT).show();
//            isCounterSensorPresent = false;
//        }
//
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
//            sensorDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//            isDetectorSensorPresent = true;
//        } else {
//            Toast.makeText(this, "Detector Sensor is not present", Toast.LENGTH_SHORT).show();
//            isDetectorSensorPresent = false;
//
//        }

    }
/*
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == sensor) {
            stepCount = (int) (sensorEvent.values[0]);
            stepCounter.setText(String.valueOf(stepCount));
        } else if (sensorEvent.sensor == sensorDetector){
            stepDetector = (int) (stepDetector+sensorEvent.values[0]);
            stepCounter.setText(String.valueOf(stepDetector));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

 */

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

//        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
//            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
            sensorManager.unregisterListener(sensorEventListener, sensor);

//        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
//            sensorManager.unregisterListener(this, sensor);

    }


}