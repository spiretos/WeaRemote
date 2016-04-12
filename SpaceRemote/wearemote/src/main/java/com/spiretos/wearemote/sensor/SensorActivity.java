package com.spiretos.wearemote.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import com.spiretos.wearemote.communication.Communicator;
import com.spiretos.wearemote.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spiretos on 9/4/2016.
 */
public abstract class SensorActivity extends WearableActivity implements SensorEventListener
{

    private SensorManager mSensorManager;
    private List<WearSensor> mSensors;

    Communicator mCommunicator;
    private int mCommunicationType = Communicator.TYPE_MESSAGE_API;


    WearSensor tempSensor;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCommunicator = new Communicator(this);
        mCommunicator.connect();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensors = new ArrayList<>();
    }



    protected void addSensor(String sensorName, int sensorType)
    {
        addSensor(sensorName, sensorType, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void addSensor(String sensorName, int sensorType, int sensorDelay)
    {
        if (mSensorManager != null)
        {
            Sensor sensor = mSensorManager.getDefaultSensor(sensorType);

            WearSensor newSensor = new WearSensor(sensorName, sensor, sensorDelay);

            mSensors.add(newSensor);
        }
    }


    protected void setCommunicationType(int communicationType)
    {
        mCommunicationType = communicationType;
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if (mSensorManager != null)
        {
            for (WearSensor sensor : mSensors)
            {
                mSensorManager.registerListener(this, sensor.getSensor(), sensor.getSensorDelay());
            }
        }
    }

    @Override
    protected void onPause()
    {
        if (mSensorManager != null)
            mSensorManager.unregisterListener(this);

        super.onPause();
    }




    @Override
    public void onSensorChanged(SensorEvent event)
    {
        tempSensor = getSensor(event);
        onGotSensorData(tempSensor, event);
    }

    protected abstract void onGotSensorData(WearSensor sensor, SensorEvent event);

    private WearSensor getSensor(SensorEvent event)
    {
        for (WearSensor sensor : mSensors)
            if (sensor.getSensor().getType() == event.sensor.getType())
                return sensor;
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }


    protected void sendSensorData(String tag, float value)
    {
        if (mCommunicator != null)
        {
            if (mCommunicationType == Communicator.TYPE_MESSAGE_API)
            {
                mCommunicator.sendMessage(tag, DataUtils.getByteArray(String.valueOf(value)));
            }
            else if (mCommunicationType == Communicator.TYPE_CHANNEL_API && mCommunicator.isChannelCreated())
            {
                mCommunicator.writeData(tag, DataUtils.getByteArray(String.valueOf(value) + "!"));
            }
        }
    }

}