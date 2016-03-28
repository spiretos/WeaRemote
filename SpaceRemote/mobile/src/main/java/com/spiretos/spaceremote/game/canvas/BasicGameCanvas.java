package com.spiretos.spaceremote.game.canvas;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by spiretos on 29/3/2016.
 */
public class BasicGameCanvas extends SurfaceView implements SurfaceHolder.Callback
{

    GameLoopThread mGameLoop;
    SurfaceHolder mSurfaceHolder;


    public BasicGameCanvas(Context context)
    {
        super(context);

        initialize();
    }

    public BasicGameCanvas(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initialize();
    }

    public BasicGameCanvas(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BasicGameCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        initialize();
    }

    private void initialize()
    {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        //mGameLoop = new GameLoopThread(mSurfaceHolder);

        //setFocusable(true);
    }




    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mGameLoop.setRunning(true);
        mGameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                mGameLoop.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                // try again shutting down the thread
            }
        }
    }

}
