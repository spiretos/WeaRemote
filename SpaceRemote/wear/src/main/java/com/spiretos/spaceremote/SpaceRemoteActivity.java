package com.spiretos.spaceremote;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.spiretos.wearemote.communication.Communicator;
import com.spiretos.wearemote.sensor.SensorActivity;
import com.spiretos.wearemote.sensor.WearSensor;

public class SpaceRemoteActivity extends SensorActivity
{

    private ImageView mShipImage;

    float tempY, tempValue, lastSendValue;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_space_remote);
        setAmbientEnabled();

        mShipImage = (ImageView) findViewById(R.id.ship_image);

        addSensor("accelerometer", Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);

        setCommunicationType(Communicator.TYPE_MESSAGE_API);
        //setCommunicationType(Communicator.TYPE_CHANNEL_API);
    }


    @Override
    protected void onGotSensorData(WearSensor sensor, SensorEvent event)
    {
        if (sensor.getName().equals("accelerometer"))
        {
            tempY = event.values[1];
            tempValue = 0;

            if (tempY < -6)
                tempValue = -3f;
            else if (tempY < -4)
                tempValue = -2f;
            else if (tempY < -2)
                tempValue = -1f;
            else if (tempY > 6)
                tempValue = 3f;
            else if (tempY > 4)
                tempValue = 2f;
            else if (tempY > 2)
                tempValue = 1f;
            else
                tempValue = 0;

            if (tempValue != lastSendValue)
            {
                lastSendValue = tempValue;
                sendSensorData("accelerometer_Y", lastSendValue);
                updateImage((int) lastSendValue);
            }
        }
    }

    private void updateImage(int value)
    {
        if (value == 0)
            mShipImage.setImageResource(R.drawable.ship0);
        else if (value == 1)
            mShipImage.setImageResource(R.drawable.ship2r);
        else if (value == -1)
            mShipImage.setImageResource(R.drawable.ship2l);
        else if (value == 2)
            mShipImage.setImageResource(R.drawable.ship2r);
        else if (value == -2)
            mShipImage.setImageResource(R.drawable.ship2l);
        else if (value == 3)
            mShipImage.setImageResource(R.drawable.ship3r);
        else if (value == -3)
            mShipImage.setImageResource(R.drawable.ship3l);
        else if (value == 4)
            mShipImage.setImageResource(R.drawable.ship4r);
        else if (value == -4)
            mShipImage.setImageResource(R.drawable.ship4l);
    }

}