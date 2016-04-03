package com.spiretos.spaceremote.game.items;

import java.util.Random;

/**
 * Created by spiretos on 3/4/2016.
 */
public class Obstacle
{

    int margin;
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
        size = new Random().nextFloat() * 30f + 20;
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

}
