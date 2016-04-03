package com.spiretos.spaceremote.game;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.spiretos.spaceremote.R;
import com.spiretos.spaceremote.communication.AppListenerService;
import com.spiretos.spaceremote.communication.GameDataReceiver;
import com.spiretos.spaceremote.game.canvas.BasicSurfaceCallback;
import com.spiretos.spaceremote.game.canvas.GameLoopThread;
import com.spiretos.wearemote.communication.Communicator;

public class GameActivity extends AppCompatActivity implements GameDataReceiver.GameDataListener
{

    public static final String START_ACTIVITY_PATH = "/start/MainActivity";

    public static final String GAME_SPACE = "game_space";
    public static final String GAME_GHOSTS = "game_ghosts";


    TextView mDataText;

    Communicator mCommunicator;
    GameDataReceiver mReceiver;
    SurfaceView mSurface;
    SpaceRemoteGameEngine mGameEngine;
    GameLoopThread mGameLoop;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Log.v("app", "create");
        setupGame();

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

    private void setupGame()
    {
        mSurface = (SurfaceView) findViewById(R.id.game_surface);

        SurfaceHolder mSurfaceHolder = mSurface.getHolder();

        String game = getIntent().getStringExtra("game");
        if (game.equals(GAME_SPACE))
        {
            mGameEngine = new SpaceRemoteGameEngine();
            mGameLoop = new GameLoopThread(mSurfaceHolder, mGameEngine);
            mSurfaceHolder.addCallback(new BasicSurfaceCallback(mGameLoop));
        }
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
    protected void onPause()
    {
        if (mReceiver != null)
        {
            mReceiver.removeGameDataListener(this);
            unregisterReceiver(mReceiver);
        }

        if (mGameLoop != null)
            mGameLoop.setRunning(false);

        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }


    @Override
    public void onYchanged(float yValue)
    {
        //Log.v("data2", yValue + "");

        if (mDataText != null)
            mDataText.setText(String.valueOf(yValue));

        if (mGameEngine != null)
            mGameEngine.setShipPosition(yValue);
    }

}
