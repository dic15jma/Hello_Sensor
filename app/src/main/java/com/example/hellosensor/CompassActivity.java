package com.example.hellosensor;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor sensorGravity, sensorRotation;
    boolean haveSensor = false, haveSensor2 = false;
    private ImageView image;
    private Sensor compass;
    private int mAzimuth;
    private float[] gravity = new float[3];
    private float[] rotation = new float[9];
    private float currentDegree = 0f;
    // orientation (azimuth, pitch, roll)
    private float[] orientation = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    // smoothed values
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compass = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        image = findViewById(R.id.imageViewCompass);

        // Get the Intent that started this activity and extract the string
        start();
        // Capture the layout's TextView and set the string as its text

    }
    protected void onResume() {
        super.onResume();
        start();
    }

    protected void onPause() {
        super.onPause();
        stop();
    }

    public void stop() {
        if (haveSensor) {
            mSensorManager.unregisterListener(this, sensorRotation);
        }
        else {
            mSensorManager.unregisterListener(this, sensorGravity);
            mSensorManager.unregisterListener(this, compass);
        }
    }

    public void start() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)) {
                noSensorsAlert();
            }
            else {
                sensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                compass = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                haveSensor = mSensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_UI);
                haveSensor2 = mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_UI);
            }
        }
        else{
            sensorRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = mSensorManager.registerListener(this, sensorRotation, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void noSensorsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Compass.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }



    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    //Modified from sensor Manager, tutsplus tutorial and buiild your first app
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotation, sensorEvent.values);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rotation, orientation)[0]) + 360) % 360;
        }

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, gravity, 0, sensorEvent.values.length);
            mLastAccelerometerSet = true;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, mLastMagnetometer, 0, sensorEvent.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rotation, null, gravity, mLastMagnetometer);
            SensorManager.getOrientation(rotation, orientation);
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rotation, orientation)[0]) + 360) % 360;
        }

        TextView textView = findViewById(R.id.textView7);
        mAzimuth = Math.round(mAzimuth);
        textView.setText(mAzimuth + " grader");

        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -(float) mAzimuth,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        // how long the animation will take place
        ra.setDuration(210);
        // set the animation after the end of the reservation status
        ra.setFillAfter(true);
        // Start the animation
        image.startAnimation(ra);
        currentDegree = -(float) mAzimuth;

    }
}

