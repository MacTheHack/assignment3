package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor mLightSensor;
    private SensorManager mSensorManager;
    private boolean mSensorExists, mLocalScreen = false;
    private Switch windowOfSystemSwitch;
    private Spinner mSpinner;
    private double mSensorBrightness;
    private ContentResolver mContentResolver;
    private Window mWindow;
    private DataHolder mDataHolder;
    private HashMap<Integer, int[]> mLuxThresholds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateSensorManager();
        checkIfSensorExists();
        registerSensor();
        initComponents();
        mDataHolder = DataHolder.getInstance();
        populateSpinner();
        populateHashmap();


    }

    private void populateHashmap() {
        mLuxThresholds = mDataHolder.getLuxThresholds();
    }

    private void populateSpinner() {
        String[] values = mDataHolder.getSpinnerArray();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }


    private void initComponents() {
        windowOfSystemSwitch = findViewById(R.id.screen_switch);
        windowOfSystemSwitch.setOnCheckedChangeListener(new SwitchListener());
        mSpinner = findViewById(R.id.spinner_levels);
        mSpinner.setOnItemSelectedListener((new SpinnerListener()));
        mContentResolver = getContentResolver();
        mWindow = getWindow();
    }

    /**
     * If the sensor we are looking for exists on the device
     * the method will register this Activity as the sensors listener.
     */
    private void registerSensor() {
        if (mSensorExists)
            mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * This method checks if the sensor we are looking for
     * exists on the device or not.
     */
    private void checkIfSensorExists() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            mSensorExists = true;
        } else {
            mSensorExists = false;
        }

    }

    /**
     * This method activates the sensor manager to allow the
     * program to access the sensors on the device.
     */
    private void activateSensorManager() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }


    /**
     * When the app goes in the foreground this method will
     * register the the activity to listen for sensor events.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorExists)
            mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * When the app goes in the background the activity will
     * unregister itself as a listener to avoid getting readings
     * that will be of no use, since the user is not on the app.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorExists)
            mSensorManager.unregisterListener(this);
    }

    /**
     * Method that is called whenever the sensor we are observing
     * gets a a hit on the information we are looking for.
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        mSensorBrightness = event.values[0];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Very small class that simply checks if the user sets the switch to
     * ON or OFF.
     */
    private class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        /**
         * Whenever the switch is turned to ON. isChecked is turned to true
         *
         * @param buttonView
         * @param isChecked
         */
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mLocalScreen = isChecked;
            if (mLocalScreen)
                Toast.makeText(MainActivity.this, "Local screen set", Toast.LENGTH_SHORT).show();

            if (!mLocalScreen)
                Toast.makeText(MainActivity.this, "System set", Toast.LENGTH_SHORT).show();
        }
    }


    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    Toast.makeText(MainActivity.this, "Level 1 selected", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    Toast.makeText(MainActivity.this, "Level 2 selected", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    Toast.makeText(MainActivity.this, "Level 3 selected", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(MainActivity.this, "Level 4 selected", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(MainActivity.this, "Level 5 selected", Toast.LENGTH_SHORT).show();
                    break;
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}



