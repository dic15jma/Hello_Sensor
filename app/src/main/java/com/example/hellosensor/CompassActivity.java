package com.example.hellosensor;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.hardware.SensorManager.getOrientation;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor sensorGravity;
    private ImageView image;
    private Sensor compass;
    private float[] gravity = new float[3];
    private float[] rotation = new float[9];
    private float currentDegree = 0f;
    // orientation (azimuth, pitch, roll)
    private float[] orientation = new float[3];
    // smoothed values
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compass = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        image = (ImageView) findViewById(R.id.imageViewCompass);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        // Capture the layout's TextView and set the string as its text

    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, compass);
        mSensorManager.unregisterListener(this, sensorGravity);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    //Modified from sensor Manager, tutsplus tutorial and buiild your first app
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        float[] magnet = new float[3];
        if (mySensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnet[0] = sensorEvent.values[0];
            magnet[1] = sensorEvent.values[1];
            magnet[2] = sensorEvent.values[2];
        }
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // we need to use a low pass filter to make data smoothed
            gravity[0] = sensorEvent.values[0];
            gravity[1] = sensorEvent.values[1];
            gravity[2] = sensorEvent.values[2];
        }
        SensorManager.getRotationMatrix(rotation, null, gravity, magnet);
        SensorManager.getOrientation(rotation, orientation);
        // east degrees of true North
        double  bearing = orientation[0];
        // convert from radians to degrees
        bearing = Math.toDegrees(bearing);
        // bearing must be in 0-360
        if (bearing < 0) {
            bearing += 360;
        }

        TextView textView = findViewById(R.id.textView7);
        textView.setText(String.format("%.2f", bearing) + " grader");

        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -(float) bearing,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        // how long the animation will take place
        ra.setDuration(210);
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        // Start the animation
        image.startAnimation(ra);
        currentDegree = -(float) bearing;

    }
}

