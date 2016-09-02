package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;


/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 */
public class EnemyShip
{
    private Bitmap mBitmap;
    private int mBitmapXCorner, mBitmapYCorner;
    private int mSpeed = -1;

    //detect enemy leaving screen
    private int maxX;
    private int minX;

    //spawn enemy within screen
    private int maxY;
    private int minY;

    //A hit box for collision detection
    private Rect mHitBox;

    public EnemyShip(Context context, int screenX, int screenY)
    {
        //loads the graphic
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        mSpeed = generator.nextInt(6) + 10;

        mBitmapXCorner = screenX;  //place enemy at the right edge of the screen
        mBitmapYCorner = ((generator.nextInt(maxY) - mBitmap.getHeight()) < 0) ? 0 : (generator.nextInt(maxY) - mBitmap.getHeight());  //place enemy at a random Y position

        mHitBox = new Rect(mBitmapXCorner, mBitmapYCorner, mBitmap.getWidth(), mBitmap.getHeight());
    }

    public void update(int playerSpeed)
    {
        //move enemy left
        mBitmapXCorner -= playerSpeed;
        mBitmapXCorner -= mSpeed;

        //respawn when off screen
        if (mBitmapXCorner < minX - mBitmap.getWidth())
        {
            Random generator = new Random();
            mSpeed = generator.nextInt(10) + 10;
            mBitmapXCorner = maxX;
            mBitmapYCorner = generator.nextInt(maxY) - mBitmap.getHeight();
        }

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

    public int getX()
    {
        return mBitmapXCorner;
    }

    public int getBitmapYCorner()
    {
        return mBitmapYCorner;
    }

    public Rect getHitBox()
    {
        return mHitBox;
    }

    public void setBitmapXCorner(int bitmapXCorner)
    {
        mBitmapXCorner = bitmapXCorner;
    }
}
