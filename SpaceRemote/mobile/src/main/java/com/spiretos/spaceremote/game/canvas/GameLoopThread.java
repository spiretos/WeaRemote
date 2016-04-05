package com.spiretos.spaceremote.game.canvas;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by spiretos on 29/3/2016.
 */
public class GameLoopThread extends Thread
{

    private boolean mIsRunning;

    SurfaceHolder mHolder;
    BasicGameEngine mEngine;



    public GameLoopThread(SurfaceHolder holder, BasicGameEngine engine)
    {
        mHolder = holder;
        mEngine = engine;
    }




    public void setRunning(boolean running)
    {
        this.mIsRunning = running;
    }

    @Override
    public void run()
    {
        while (mIsRunning)
        {
            Canvas canvas = mHolder.lockCanvas(null);

            mEngine.update(canvas);

            if (canvas != null)
            {
                synchronized (mHolder)
                {
                    mEngine.Draw(canvas);
                }
                mHolder.unlockCanvasAndPost(canvas);
            }

            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

}


