package com.spiretos.spaceremote.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spiretos.spaceremote.game.canvas.BasicGameEngine;

/**
 * Created by spiretos on 29/3/2016.
 */
public class SpaceRemoteGameEngine implements BasicGameEngine
{

    private float y;

    Paint backPaint;
    Paint shipPaint;



    public SpaceRemoteGameEngine()
    {
        backPaint = new Paint();
        backPaint.setColor(Color.BLACK);

        shipPaint = new Paint();
        shipPaint.setColor(Color.YELLOW);
    }



    @Override
    public void update()
    {

    }

    @Override
    public void Draw(Canvas canvas)
    {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backPaint);

        float x = canvas.getWidth() / 2f + y * 1000f;
        canvas.drawCircle(x, canvas.getHeight() - 50f, 10f, shipPaint);
    }

    public void setY(float y)
    {
        this.y = y;
    }

}
