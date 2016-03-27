package com.spiretos.spaceremote.game;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.spiretos.spaceremote.R;
import com.spiretos.spaceremote.communication.AppListenerService;
import com.spiretos.spaceremote.communication.GameDataReceiver;
import com.spiretos.wearemote.communication.Communicator;

public class GameActivity extends AppCompatActivity implements GameDataReceiver.GameDataListener
{

    public static final String START_ACTIVITY_PATH = "/start/MainActivity";

    TextView mDataText;

    Communicator mCommunicator;
    //ActivityMessageHandler mHandler;
    GameDataReceiver mReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Log.v("app", "create");

        mDataText = (TextView) findViewById(R.id.game_datatext);

        mCommunicator = new Communicator(this);
        mCommunicator.setCommunicationListener(new Communicator.CommunicationListener()
        {
            @Override
            public void onConnected()
            {
                mCommunicator.sendMessage(START_ACTIVITY_PATH);
            }
        });
        mCommunicator.connect();
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        mReceiver = new GameDataReceiver();
        mReceiver.setGameDataListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppListenerService.RECEIVED_Y_DATA);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onStop()
    {
        if (mReceiver != null)
        {
            mReceiver.removeGameDataListener(this);
            unregisterReceiver(mReceiver);
        }

        super.onStop();
    }


    @Override
    public void onYchanged(float yValue)
    {
        Log.v("data2", yValue + "");

        if (mDataText!=null)
            mDataText.setText(String.valueOf(yValue));
    }

}
