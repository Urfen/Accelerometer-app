package se.arvidbodkth.laboration3;


import java.util.ArrayList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Arvid on 2015-12-04.
 *
 */
public class SensorReader {

    private SensorManager sensorManager;
    private MainActivity mainActivity;

    private double x, y, z;
    private double lowPassFilterAmount;
    private double highPassFilterAmount;

    private double lowFilterX, lowFilterY, lowFilterZ;
    private double highFilterX, highFilterY, highFilterZ;

    private int sensorFrequency = 0;
    private int skakning = 0;

    private ArrayList<SensorReading> readings;

    private double isShown = 0;

    public SensorReader(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        readings = new ArrayList<>();
        sensorFrequency = 0;
        lowPassFilterAmount = 0.995;
        highPassFilterAmount = 0.015;
        x = 0;
        y = 0;
        z = 0;
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

            //Get the constants via lowpassfiltering.
            lowFilterX = lowPassFilterAmount * lowFilterX + (1- lowPassFilterAmount)*x;
            lowFilterY = lowPassFilterAmount * lowFilterY + (1- lowPassFilterAmount)*y;
            lowFilterZ = lowPassFilterAmount * lowFilterZ + (1- lowPassFilterAmount)*z;


            if (lowFilterX > 8) mainActivity.showToast("Vänster");
            if (lowFilterX < -8) mainActivity.showToast("Höger");
            if (lowFilterY > 8) mainActivity.showToast("Upprätt");
            if (lowFilterY < -8) mainActivity.showToast("Upp och ner");
            if (lowFilterZ > 8) mainActivity.showToast("Skärm upp");
            if (lowFilterZ < -8) mainActivity.showToast("Skärm ner");

            for (int i = 0; i < 10; i++) {
                if(lowFilterX>i && lowFilterX<i+1 && isShown != i){

                    System.out.println(i);
                    isShown = i;
                }
            }

            //Last highpass value
            readings.add(new SensorReading(highFilterX, highFilterY, highFilterZ));

            //Get the changes via highpass filtering.
            highFilterX = highPassFilterAmount * highFilterX + (1- highPassFilterAmount)*x;
            highFilterY = highPassFilterAmount * highFilterY + (1- highPassFilterAmount) * y;
            highFilterZ = highPassFilterAmount * highFilterZ + (1- highPassFilterAmount)*z;

            //New highpass value
            readings.add(new SensorReading(highFilterX, highFilterY, highFilterZ));

            if(readings.get(0).compareReading(readings.get(1)) > 0.3 ||
                    readings.get(0).compareReading(readings.get(1)) < -0.3){
                skakning++;
                if(skakning > 200){
                    System.out.println("Skakar");
                    skakning = 0;
                }
            }
            else if(skakning > 0)skakning--;

            readings.clear();
        }
    };
}

