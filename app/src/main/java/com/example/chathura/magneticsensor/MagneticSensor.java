package com.example.chathura.magneticsensor;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.TypeVariable;
import java.util.concurrent.Semaphore;

public class MagneticSensor extends AppCompatActivity implements SensorEventListener {
    public TextView tX, tY, tZ;
    public ProgressBar pX, pY, pZ;
    public SensorManager sensorManager;
    public Sensor magneticSensor;
    int maxValue;
    public int pValueX;
    public int pValueY;
    public int pValueZ;
    int red = Color.RED;
    int blue = Color.BLUE;
    int green = Color.GREEN;
    public Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic_sensor);

        tX = (TextView) findViewById(R.id.tvX);
        tY = (TextView) findViewById(R.id.tvY);
        tZ = (TextView) findViewById(R.id.tvZ);
        pX = (ProgressBar) findViewById(R.id.pX);
        pY = (ProgressBar) findViewById(R.id.pY);
        pZ = (ProgressBar) findViewById(R.id.pZ);
        exit = (Button) findViewById(R.id.btnExit);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        maxValue = Math.round(magneticSensor.getMaximumRange());


        if (magneticSensor == null) {
            Toast.makeText(this, "The device has no magnetic sensor !", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MagneticSensor.this.finish();
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tX.setText(String.valueOf(event.values[0]));
        pValueX = Math.round(event.values[0]);
        setProgressValue(pValueX , pX);
        setProgressColor(pX , red);

        tY.setText(String.valueOf(event.values[1]));
        pValueY = Math.round(event.values[1]);
        setProgressValue(pValueY , pY);
        setProgressColor(pY , blue);

        tZ.setText(String.valueOf(event.values[2]));
        pValueZ = Math.round(event.values[2]);
        setProgressValue(pValueZ , pZ);
        setProgressColor(pZ , green);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void setProgressValue(final int progress , final ProgressBar p) {

        p.setMax(maxValue);
        p.setProgress(progress);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10 , p);
            }
        });
        thread.start();
    }

    public void setProgressColor(ProgressBar p , int color){
        p.getProgressDrawable().setColorFilter(color , android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}


