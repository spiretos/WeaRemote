package com.spiretos.spaceremote;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import com.spiretos.wearemote.communication.Communicator;
import com.spiretos.wearemote.utils.DataUtils;

public class SpaceRemoteActivity extends WearableActivity implements SensorEventListener
{

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView mSensorText;

    Communicator mCommunicator;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v("asd","Asd");
        setContentView(R.layout.activity_space_remote);
        setAmbientEnabled();

        mSensorText = (TextView) findViewById(R.id.main_sensortext);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        mCommunicator = new Communicator(this);
        mCommunicator.connect();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails)
    {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient()
    {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient()
    {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay()
    {
        if (isAmbient())
        {

        }
        else
        {

        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        if (mSensorManager != null)
        {
            Log.v("wear", "sensor Registered");
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onStop()
    {
        if (mSensorManager != null)
        {
            Log.v("wear", "sensor UN-Registered");
            mSensorManager.unregisterListener(this);
        }

        super.onStop();
    }

    @Override
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
                mCommunicator.writeData("gyroscope_data_y1", DataUtils.getByteArray(String.valueOf(sensorEvent.values[1])+"!"));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}
