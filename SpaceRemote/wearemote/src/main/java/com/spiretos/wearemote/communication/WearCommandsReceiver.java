package com.spiretos.wearemote.communication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by spiretos on 12/4/2016.
 */
public abstract class WearCommandsReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        executeCommand(context, intent);
    }

    protected abstract void executeCommand(Context context, Intent intent);

}