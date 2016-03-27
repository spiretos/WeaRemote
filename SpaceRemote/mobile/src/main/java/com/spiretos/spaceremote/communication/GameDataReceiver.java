package com.spiretos.spaceremote.communication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spiretos on 28/3/2016.
 */
public class GameDataReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            float yValue= intent.getFloatExtra("data_y", 0);
            //Log.v("data1", yValue + "");

            sendYchanged(yValue);
        }



        // EVENTS

        public void setGameDataListener(GameDataListener listener)
        {
            addListener(listener);
        }

        public void removeGameDataListener(GameDataListener listener)
        {
            removeListener(listener);
        }

        private void sendYchanged(float yValue)
        {
            for (GameDataListener l : mListeners)
            {
                l.onYchanged(yValue);
            }
        }

        public interface GameDataListener
        {
            public void onYchanged(float yValue);
        }

        List<GameDataListener> mListeners = new ArrayList<GameDataListener>();

        private void addListener(GameDataListener toAdd)
        {
            mListeners.add(toAdd);
        }

        public void removeListener(GameDataListener toRemove)
        {
            mListeners.remove(toRemove);
        }

}
