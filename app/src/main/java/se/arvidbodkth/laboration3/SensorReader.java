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
    private double lowPassFilterAmount = 0.985;
    private double highPassFilterAmount = 0.015;

    private double lowFilterX, lowFilterY, lowFilterZ;
    private double highFilterX, highFilterY, highFilterZ;

    private int sensorFrequency = 0;

    private ArrayList<SensorReading> readings;
    private ArrayList<SensorReading> lastReadings;

    public SensorReader(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        readings = new ArrayList<>();
        lastReadings = new ArrayList<>();
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

            String data = lowFilterX + "\n" + lowFilterY + "\n" + lowFilterZ;
            mainActivity.setTextView2(data);

            if (lowFilterX > 9) mainActivity.showToast("Vänster");
            if (lowFilterX < -9) mainActivity.showToast("Höger");
            if (lowFilterY > 9) mainActivity.showToast("Upprätt");
            if (lowFilterY < -9) mainActivity.showToast("Upp och ner");
            if (lowFilterZ > 9) mainActivity.showToast("Skärm upp");
            if (lowFilterZ < -9) mainActivity.showToast("Skärm ner");

            //Last highpass value
            readings.add(new SensorReading(highFilterX, highFilterY, highFilterZ));

            //Get the changes via highpass filtering.
            highFilterX = highPassFilterAmount * highFilterX + (1- highPassFilterAmount)*x;
            highFilterY = highPassFilterAmount * highFilterY + (1- highPassFilterAmount) * y;
            highFilterZ = highPassFilterAmount * highFilterZ + (1- highPassFilterAmount)*z;

            //New highpass value
            readings.add(new SensorReading(highFilterX, highFilterY, highFilterZ));

            data = highFilterX + "\n" + highFilterY + "\n" + highFilterZ;
            mainActivity.setTextView(data);

//            System.out.println(readings.get(0).compareReading(readings.get(1)));

            if(readings.get(0).compareReading(readings.get(1)) > 2 || readings.get(0).compareReading(readings.get(1)) < -2){
                System.out.println("Skakar");
            }
            readings.clear();
        }
    };
}

