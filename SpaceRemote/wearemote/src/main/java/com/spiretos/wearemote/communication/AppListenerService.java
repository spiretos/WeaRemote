package com.spiretos.wearemote.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.spiretos.wearemote.utils.DataUtils;

import java.io.InputStream;

/**
 * Created by spiretos on 27/3/2016.
 */
public class AppListenerService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    public static final String RECEIVED_Y_DATA = "RECEIVED_Y_DATA";

    private GoogleApiClient mGoogleApiClient;
    private Channel mDataChannel;



    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        super.onMessageReceived(messageEvent);

        //Log.w("m-", "message received");

        if (messageEvent.getPath().equals(Communicator.MESSAGE_START_ACTIVITY))
        {
            Log.w("received", Communicator.MESSAGE_START_ACTIVITY);
            Intent intent = new Intent(Communicator.BROADCAST_SENSOR_ACTIVITY_COMMAND);
            intent.putExtra("command",Communicator.MESSAGE_START_ACTIVITY);
            sendBroadcast(intent);
        }
        if (messageEvent.getPath().equals(Communicator.MESSAGE_FINISH_ACTIVITY))
        {
            Log.w("received", Communicator.MESSAGE_FINISH_ACTIVITY);
            Intent intent = new Intent(Communicator.BROADCAST_SENSOR_ACTIVITY_COMMAND);
            intent.putExtra("command",Communicator.MESSAGE_FINISH_ACTIVITY);
            sendBroadcast(intent);
        }
        else
        {
            try
            {
                float sensorValue = DataUtils.getFloatFrom(DataUtils.getStringFrom(messageEvent.getData()));

                //Log.w("m-", "got '" + messageEvent.getPath() + "'=" + sensorValue);

                Intent intent = new Intent("sensor_data");
                intent.putExtra("type",messageEvent.getPath());
                intent.putExtra("value", sensorValue);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onChannelOpened(Channel channel)
    {
        super.onChannelOpened(channel);

        Log.v("channel", "openned");
        mDataChannel = channel;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        /*if (mDataChannel != null)
        {
            mDataChannel.getInputStream(mGoogleApiClient).setResultCallback(new ResultCallback<Channel.GetInputStreamResult>()
            {
                @Override
                public void onResult(@NonNull Channel.GetInputStreamResult getInputStreamResult)
                {
                    Thread t = new Thread(new TestThread(getInputStreamResult));
                    t.start();
                }
            });
        }*/
    }

    public class TestThread implements Runnable
    {

        private final Channel.GetInputStreamResult getInputStreamResult;

        public TestThread(Channel.GetInputStreamResult getInputStreamResult)
        {
            this.getInputStreamResult = getInputStreamResult;
        }

        @Override
        public void run()
        {
            try
            {
                InputStream inputStream = getInputStreamResult.getInputStream();
                int i;
                char c;
                String s = "";
                while ((i = inputStream.read()) != -1)
                {
                    c = (char) i;

                    if (c == '!')
                    {
                        final float yValue = DataUtils.getFloatFrom(s);

                        Intent intent = new Intent(RECEIVED_Y_DATA);
                        intent.putExtra("data_y", yValue);
                        LocalBroadcastManager.getInstance(AppListenerService.this).sendBroadcast(intent);

                        Thread.sleep(5);
                        s = "";
                    }
                    else
                        s += c;
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
}
