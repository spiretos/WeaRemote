package com.spiretos.wearemote.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spiretos.wearemote.communication.AppListenerService;
import com.spiretos.wearemote.communication.Communicator;

/**
 * Created by spiretos on 8/4/2016.
 */
public abstract class ReceiverActivity extends AppCompatActivity
{

    Communicator mCommunicator;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCommunicator = new Communicator(this);
        mCommunicator.setCommunicationListener(new Communicator.CommunicationListener()
        {
            @Override
            public void onConnected()
            {
                OnConnectedWithWear();
            }
        });
        mCommunicator.connect();
    }


    protected void OnConnectedWithWear()
    {
        mCommunicator.sendMessage(Communicator.MESSAGE_START_ACTIVITY);
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("sensor_data"));
    }

    @Override
    protected void onPause()
    {
        if (mMessageReceiver != null)
        {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        }

        super.onPause();
    }


    protected abstract void OnReceivedRemoteValue(String type, float value);

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String type=intent.getStringExtra("type");
            float yValue = intent.getFloatExtra("value", 0);

            //Log.w("m-", "*** GOT '" + type + "'=" + yValue);

            //onYchanged(yValue);
            OnReceivedRemoteValue(type, yValue);
        }
    };


    protected Communicator getCommunicator()
    {
        return mCommunicator;
    }
}