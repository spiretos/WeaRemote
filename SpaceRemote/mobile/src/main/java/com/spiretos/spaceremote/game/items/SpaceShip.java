package com.spiretos.spaceremote.game.items;

/**
 * Created by spiretos on 12/4/2016.
 */
public class SpaceShip
{

    float position;
    float speed;
    float availableWidth;

    private long lastTime;



    public SpaceShip()
    {
        lastTime = System.currentTimeMillis();
        position = -1;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public void setAvailableSpace(float width)
    {
        availableWidth = width;
    }

    public void calculatePosition()
    {
        if (position == -1) //first time
            position = availableWidth / 2f;
        else
        {
            long elapsedTime = System.currentTimeMillis() - lastTime;
            position += elapsedTime * speed / 1000f;

            if (position < 30)
                position = 30;
            else if (position > availableWidth - 20)
                position = availableWidth - 20;

            lastTime = System.currentTimeMillis();
        }
    }

    public float getPosition()
    {
        return position;
    }
}