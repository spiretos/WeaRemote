package com.spiretos.spaceremote.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.spiretos.spaceremote.R;
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

    Bitmap shipImage, asteroidImage;
    Rect shipRect, asteroidRect;




    public SpaceRemoteGameEngine(Context context)
    {
        backPaint = new Paint();
        backPaint.setColor(Color.BLACK);

        shipPaint = new Paint();
        shipPaint.setColor(Color.YELLOW);

        mObstacles = new ArrayList<>();

        shipImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        shipRect = new Rect(0, 0, shipImage.getWidth(), shipImage.getHeight());
        asteroidImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid);
        asteroidRect = new Rect(0, 0, asteroidImage.getWidth(), asteroidImage.getHeight());
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
                obstacleDistance = obstacle.getDistance();

                //canvas.drawCircle(obstacle.getPosition(), obstacle.getDistance(), obstacle.getSize(), shipPaint);
                canvas.drawBitmap(asteroidImage, asteroidRect, obstacle.getRect(obstacleDistance), backPaint);
                if (obstacleDistance > canvas.getHeight() + 100)
                    obstacle.setActive(false);
            }
        }

        float x = canvas.getWidth() / 2f + shipPosition * 1000f;
        //canvas.drawCircle(x, canvas.getHeight() - 50f, 10f, shipPaint);
        float shipSize = 40f;
        canvas.drawBitmap(shipImage, asteroidRect,
                new RectF((float) x - shipSize / 2f, canvas.getHeight() - 50f - shipSize / 2f,
                        (float) x + shipSize / 2f, canvas.getHeight() - 50f + shipSize / 2f)
                , backPaint);
    }

    public void setShipPosition(float y)
    {
        this.shipPosition = y;
    }

}
