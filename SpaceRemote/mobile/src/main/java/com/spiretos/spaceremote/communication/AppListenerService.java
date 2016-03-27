package com.spiretos.spaceremote.communication;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.spiretos.wearemote.utils.DataUtils;

/**
 * Created by spiretos on 27/3/2016.
 */
public class AppListenerService extends WearableListenerService
{

    public static final String RECEIVED_Y_DATA = "RECEIVED_Y_DATA";

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        super.onMessageReceived(messageEvent);

        if (messageEvent.getPath().equals("gyroscope_data_y"))
        {
            float yValue = DataUtils.getFloatFrom(DataUtils.getStringFrom(messageEvent.getData()));
            //Log.v("data", yValue + "");

            Intent intent = new Intent();
            intent.setAction(RECEIVED_Y_DATA);
            intent.putExtra("data_y", yValue);
            sendBroadcast(intent);
        }
    }

}
