package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


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
    private int mBitmapXCorner, mBitmapYCorner;  //x and y are of the top left point of the bitmap
    private int mSpeed = 0;  //how fast the ship will travel
    private boolean mBoosting;
    private int maxY; //screen boundaries
    private int minY; //screen boundaries

    private static final int GRAVITY = -12;  //a random number chosen for gravity -- may need adjusting
    private static final int MIN_SPEED = 1;  //the min speed
    private static final int MAX_SPEED = 20; //a random number chosen for max speed -- may need adjusting

    //A hit box for collision detection
    private Rect mHitBox;

    private int mShieldStrength;

    public PlayerShip(Context context, int screenX, int screenY)
    {
        mBitmapXCorner = 50;
        mBitmapYCorner = 50;
        mSpeed = 1;
        mShieldStrength = 2;
        //loads the graphic
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        mBoosting = false;
        maxY = screenY - mBitmap.getHeight(); //this is the screen bottom
        minY = 0; //this is the screen top

        mHitBox = new Rect(mBitmapXCorner, mBitmapYCorner, mBitmap.getWidth(), mBitmap.getHeight());
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
        mBitmapYCorner -= mSpeed + GRAVITY;

        //keep ship on screen
        if (mBitmapYCorner < minY)
            mBitmapYCorner = minY;
        if(mBitmapYCorner > maxY)
            mBitmapYCorner = maxY;

        //refresh placement of hit box location
        mHitBox.left = mBitmapXCorner;
        mHitBox.top = mBitmapYCorner;
        mHitBox.right = mBitmapXCorner + mBitmap.getWidth();
        mHitBox.bottom = mBitmapYCorner + mBitmap.getHeight();
    }

    public Bitmap getBitmap()
    {
        return mBitmap;
    }

    public int getPlayerPosX()
    {
        return mBitmapXCorner;
    }

    public int getBitmapYCorner()
    {
        return mBitmapYCorner;
    }

    public int getSpeed()
    {
        return mSpeed;
    }

    public void setBoosting(boolean boosting)
    {
        mBoosting = boosting;
    }

    public Rect getHitBox()
    {
        return mHitBox;
    }

    public int getShieldStrength()
    {
        return mShieldStrength;
    }
}