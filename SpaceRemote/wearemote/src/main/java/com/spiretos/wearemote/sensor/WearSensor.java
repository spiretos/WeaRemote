package com.spiretos.wearemote.sensor;

import android.hardware.Sensor;

/**
 * Created by spiretos on 11/4/2016.
 */
public class WearSensor
{

    private String name;
    private Sensor sensor;
    private int delay;
    private int sensorType;


    public WearSensor(String sensorName, Sensor sensor, int sensorDelay)
    {
        name = sensorName;
        this.sensor = sensor;
        delay = sensorDelay;

        sensorType = sensor.getType();
    }


    public String getName()
    {
        return name;
    }

    public Sensor getSensor()
    {
        return sensor;
    }

    public int getSensorDelay()
    {
        return delay;
    }

    public int getSensorType()
    {
        return sensorType;
    }

}
