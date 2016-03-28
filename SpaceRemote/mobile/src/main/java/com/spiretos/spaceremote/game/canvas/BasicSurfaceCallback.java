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
public class BasicSurfaceCallback implements SurfaceHolder.Callback
{

    GameLoopThread mGameLoop;


    public BasicSurfaceCallback(GameLoopThread gameloop)
    {
        mGameLoop=gameloop;
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
