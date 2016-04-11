package com.spiretos.spaceremote.game;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.spiretos.spaceremote.R;
import com.spiretos.spaceremote.communication.GameDataReceiver;
import com.spiretos.spaceremote.game.canvas.BasicSurfaceCallback;
import com.spiretos.spaceremote.game.canvas.GameLoopThread;
import com.spiretos.wearemote.receiver.ReceiverActivity;

public class GameActivity extends ReceiverActivity implements GameDataReceiver.GameDataListener
{

    public static final String START_ACTIVITY_PATH = "/start/MainActivity";

    public static final String GAME_SPACE = "game_space";
    public static final String GAME_GHOSTS = "game_ghosts";


    TextView mDataText;

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
    }


    private void setupGame()
    {
        mSurface = (SurfaceView) findViewById(R.id.game_surface);

        SurfaceHolder mSurfaceHolder = mSurface.getHolder();

        String game = getIntent().getStringExtra("game");
        if (game.equals(GAME_SPACE))
        {
            mGameEngine = new SpaceRemoteGameEngine(this);
            mGameLoop = new GameLoopThread(mSurfaceHolder, mGameEngine);
            mSurfaceHolder.addCallback(new BasicSurfaceCallback(mGameLoop));
        }
    }





    @Override
    public void onYchanged(float yValue)
    {
        //Log.v("data2", yValue + "");

        /*if (mDataText != null)
            mDataText.setText(String.valueOf(yValue));*/

        if (mGameEngine != null)
            mGameEngine.setShipPosition(yValue);
    }

    @Override
    protected void OnReceivedRemoteValue(String type, float value)
    {
        if (mGameEngine != null)
            mGameEngine.setShipPosition(value);
    }



}
