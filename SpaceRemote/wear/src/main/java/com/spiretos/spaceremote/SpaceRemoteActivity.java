package com.spiretos.spaceremote;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.spiretos.wearemote.communication.Communicator;
import com.spiretos.wearemote.sensor.SensorActivity;
import com.spiretos.wearemote.sensor.WearSensor;

public class SpaceRemoteActivity extends SensorActivity
{

    private TextView mSensorText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_space_remote);
        setAmbientEnabled();

        mSensorText = (TextView) findViewById(R.id.main_sensortext);
        mSensorText.setText("asd");

        addSensor("accelerometer", Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);

        setCommunicationType(Communicator.TYPE_MESSAGE_API);
        //setCommunicationType(Communicator.TYPE_CHANNEL_API);
    }



    /*@Override
    public void onSensorChanged(final SensorEvent sensorEvent)
    {
        if (mSensorText != null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("x:" + sensorEvent.values[0] + "\n");
            sb.append("y:" + sensorEvent.values[1] + "\n");
            sb.append("z:" + sensorEvent.values[2]);

            mSensorText.setText(sb.toString());

            if (mCommunicator != null && mCommunicator.isChannelCreated())
            {
                //mCommunicator.sendMessage("gyroscope_data_y", DataUtils.getByteArray(String.valueOf(sensorEvent.values[1])));
                mCommunicator.writeData("gyroscope_data_y1", DataUtils.getByteArray(String.valueOf(sensorEvent.values[1]) + "!"));
            }
        }
    }*/

    @Override
    protected void onNewSensorData(WearSensor sensor, SensorEvent event)
    {
        if (sensor.getName().equals("accelerometer"))
        {
            sendSensorData("accelerometer_Y", event.values[1]);
        }
    }

}