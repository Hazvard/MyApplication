package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Capteurs extends AppCompatActivity implements SensorEventListener {

    private Boolean activation;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteurs);

        Log.i("Capteurs", "onCreate: ");

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Switch activateur = findViewById(R.id.switch1);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        activateur.setOnClickListener(view -> {
            activation = ((Switch)activateur).isChecked();
            if(activation){
                // On désactive le sensor
                sensorManager.unregisterListener(this);

            }else{
                // On active le sensor
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL );
            }
        });





    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
            ((TextView)findViewById(R.id.vecteurs)).setText("X : "+ sensorEvent.values[0]+ "\nY : "+sensorEvent.values[1]+"\nZ : "+ sensorEvent.values[2] );
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onPause() {

        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST );
    }
}