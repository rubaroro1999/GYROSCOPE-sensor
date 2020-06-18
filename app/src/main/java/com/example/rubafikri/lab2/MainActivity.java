package com.example.rubafikri.lab2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor s;
    HorizontalScrollView hh;
    AudioManager audioManager;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        sensorManager   = ( SensorManager ) getSystemService(this.SENSOR_SERVICE);
         audioManager = (AudioManager)getApplicationContext().getSystemService(getApplicationContext().AUDIO_SERVICE);

        if(sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE) != null){
            s = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }else{
            Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
           // hh.smoothScrollTo(hh.getScrollX()+((int) event.values[1]*150),0);
            if(event.values[1] > 2){
                audioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
                tv.setText("Normal");

            }else if(event.values[1] < -2){
                audioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                tv.setText("Seilent");

            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,s,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
