package com.spiretos.spaceremote.game.items;

import android.graphics.RectF;

import java.util.Random;

/**
 * Created by spiretos on 3/4/2016.
 */
public class Obstacle
{

    int margin = 50;
    int position;

    boolean active;

    long creationTime;
    float speed = 300;

    float size;


    public Obstacle(int spaceWidth)
    {
        creationTime = System.currentTimeMillis();

        active = true;
        position = new Random().nextInt(spaceWidth - 2 * margin) + margin;
        size = new Random().nextFloat() * 50f + 50f;
    }


    public int getPosition()
    {
        return position;
    }


    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public float getDistance()
    {
        long elapsedTime = System.currentTimeMillis() - creationTime;
        return elapsedTime * speed / 1000f;
    }

    public float getSize()
    {
        return size;
    }

    public RectF getRect(float obstacleDistance)
    {
        return new RectF((float) position - size / 2f, obstacleDistance - size / 2f, (float) position + size / 2f, obstacleDistance + size / 2f);
    }
}
