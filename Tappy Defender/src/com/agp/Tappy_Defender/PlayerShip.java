package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 */
public class PlayerShip
{
    private Bitmap mBitmap;
    private int x, y;
    private int mSpeed = 0;  //how fast the ship will travel
    private boolean mBoosting;
    private int maxY; //screen boundaries
    private int minY; //screen boundaries

    private static final int GRAVITY = -12;  //a random number chosen for gravity -- may need adjusting
    private static final int MIN_SPEED = 1;  //the min speed
    private static final int MAX_SPEED = 20; //a random number chosen for max speed -- may need adjusting

    public PlayerShip(Context context, int screenX, int screenY)
    {
        x = 50;
        y = 50;
        mSpeed = 1;
        //loads the graphic
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        mBoosting = false;
        maxY = screenY - mBitmap.getHeight(); //this is the screen bottom
        minY = 0; //this is the screen top

    }

    public void update()
    {
        //Are we boosting
        if (mBoosting){
            mSpeed += 2;
        }
        else{
            mSpeed -= 5;
        }

        //constrain top speed
        if (mSpeed > MAX_SPEED){
            mSpeed = MAX_SPEED;
        }

        //never stop completely
        if (mSpeed < MIN_SPEED){
            mSpeed = MIN_SPEED;
        }

        //move ship vertically
        y -= mSpeed + GRAVITY;

        //keep ship on screen
        if (y < minY)
            y = minY;
        if(y > maxY)
            y = maxY;
    }

    public Bitmap getBitmap()
    {
        return mBitmap;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getSpeed()
    {
        return mSpeed;
    }

    public void setBoosting(boolean boosting)
    {
        mBoosting = boosting;
    }
}


