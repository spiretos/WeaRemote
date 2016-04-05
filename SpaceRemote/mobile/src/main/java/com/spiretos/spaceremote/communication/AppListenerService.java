package com.spiretos.spaceremote.communication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
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

        if (messageEvent.getPath().equals("gyroscope_data_y"))
        {
            float yValue = DataUtils.getFloatFrom(DataUtils.getStringFrom(messageEvent.getData()));
            Log.v("data123", yValue + "");

            /*Intent intent = new Intent();
            intent.setAction(RECEIVED_Y_DATA);
            intent.putExtra("data_y", yValue);
            sendBroadcast(intent);*/
            Intent intent = new Intent(RECEIVED_Y_DATA);
            intent.putExtra("data_y", yValue);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
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
        if (mDataChannel != null)
        {
            mDataChannel.getInputStream(mGoogleApiClient).setResultCallback(new ResultCallback<Channel.GetInputStreamResult>()
            {
                @Override
                public void onResult(@NonNull Channel.GetInputStreamResult getInputStreamResult)
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
                                Log.v("data", yValue + "");

                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Intent intent = new Intent(RECEIVED_Y_DATA);
                                        intent.putExtra("data_y", yValue);
                                        LocalBroadcastManager.getInstance(AppListenerService.this).sendBroadcast(intent);
                                    }
                                }.run();

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
            });
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
