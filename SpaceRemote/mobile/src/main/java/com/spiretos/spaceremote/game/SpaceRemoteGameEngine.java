package com.spiretos.spaceremote.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.spiretos.spaceremote.game.canvas.BasicGameEngine;
import com.spiretos.spaceremote.game.items.Obstacle;

import java.util.ArrayList;

/**
 * Created by spiretos on 29/3/2016.
 */
public class SpaceRemoteGameEngine implements BasicGameEngine
{

    private float shipPosition;

    Paint backPaint;
    Paint shipPaint;

    ArrayList<Obstacle> mObstacles;

    int spawnObstacleInterval = 800;
    long lastObstacleTime = System.currentTimeMillis();


    Obstacle obstacle;
    float obstacleDistance;




    public SpaceRemoteGameEngine()
    {
        backPaint = new Paint();
        backPaint.setColor(Color.BLACK);

        shipPaint = new Paint();
        shipPaint.setColor(Color.YELLOW);

        mObstacles = new ArrayList<>();
    }



    @Override
    public void update(Canvas canvas)
    {
        long currentTime = System.currentTimeMillis();

        for (int i = mObstacles.size() - 1; i >= 0; i--)
        {
            obstacle = mObstacles.get(i);
            if (!obstacle.isActive())
                mObstacles.remove(i);
        }

        if (currentTime - lastObstacleTime > spawnObstacleInterval)
        {
            obstacle = new Obstacle(canvas.getWidth());
            mObstacles.add(obstacle);
            lastObstacleTime = currentTime;
        }
    }

    @Override
    public void Draw(Canvas canvas)
    {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backPaint);

        for (int i = 0; i < mObstacles.size(); i++)
        {
            obstacle = mObstacles.get(i);
            if (obstacle.isActive())
            {
                canvas.drawCircle(obstacle.getPosition(), obstacle.getDistance(), obstacle.getSize(), shipPaint);
                if (obstacleDistance > canvas.getWidth() + 100)
                    obstacle.setActive(false);
            }
        }

        float x = canvas.getWidth() / 2f + shipPosition * 1000f;
        canvas.drawCircle(x, canvas.getHeight() - 50f, 10f, shipPaint);
    }

    public void setShipPosition(float y)
    {
        this.shipPosition = y;
    }

}
