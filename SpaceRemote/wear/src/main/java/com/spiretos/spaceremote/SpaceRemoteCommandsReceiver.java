package com.spiretos.spaceremote;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.spiretos.wearemote.communication.Communicator;
import com.spiretos.wearemote.communication.WearCommandsReceiver;

/**
 * Created by spiretos on 12/4/2016.
 */
public class SpaceRemoteCommandsReceiver extends WearCommandsReceiver
{

    @Override
    protected void executeCommand(Context context, Intent intent)
    {
        String message=intent.getStringExtra("command");

        if (message.equals(Communicator.MESSAGE_START_ACTIVITY))
        {
            startActivity(context);
        }
        /*else if (message.equals(Communicator.MESSAGE_FINISH_ACTIVITY))
        {
            //local broadcast to finish activity?
        }*/
    }

    private void startActivity(Context context)
    {
        Intent intent = new Intent(context, SpaceRemoteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 75, 50, 75};
        vibrator.vibrate(vibrationPattern, -1);
    }

}
