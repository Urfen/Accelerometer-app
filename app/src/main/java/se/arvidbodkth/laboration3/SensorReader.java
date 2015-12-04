package se.arvidbodkth.laboration3;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Arvid on 2015-12-04.
 *
 */
public class SensorReader {

    private SensorManager sensorManager;
    private MainActivity mainActivity;

    private double x, y, z, maxX = -1000.0F, maxY = -1000.0F, maxZ = -1000.0F,
            minX = 1000.0F, minY = 1000.0F, minZ = 1000.0F;
    private double filterX, filterY, filterZ;

    private double filterAmount = 0.5;

    private int sensorFrequency = 0;

    private ArrayList<SensorReading> readings;

    public SensorReader(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        readings = new ArrayList<>();
    }


    public void startListening(){
        sensorManager = (SensorManager) mainActivity.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometer,
                sensorFrequency);
    }

    public void stopListening() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }
    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        // private double calibration = SensorManager.STANDARD_GRAVITY;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            // NB! Need to store max/min when display switches orientation.
            if (x > maxX)
                maxX = x;
            if (y > maxY)
                maxY = y;
            if (z > maxZ)
                maxZ = z;
            if (x < minX)
                minX = x;
            if (y < minY)
                minY = y;
            if (z < minZ)
                minZ = z;

            // Store time stamp
            String data = x + "\n" + y + "\n" + z;
            mainActivity.setTextView(data);

            /*filterX = filterAmount * filterX + (1-filterAmount)*x;
            filterY = filterAmount * filterY + (1-filterAmount)*y;
            filterZ = filterAmount * filterZ + (1-filterAmount)*z;*/


            readings.add(new SensorReading(x, y, z));

            if(readings.size() > 100){

                for (SensorReading s: readings) {

                }

                readings.clear();
            }



                //data = filterX + "\n" + filterY + "\n" + filterZ;
            mainActivity.setTextView2(data);

        }
    };
}

