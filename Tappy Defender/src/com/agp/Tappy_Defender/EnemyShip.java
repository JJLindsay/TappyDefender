package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    private int x, y;
    private int mSpeed = -1;

    //detect enemy leaving screen
    private int maxX;
    private int minX;

    //spawn enemy within screen
    private int maxY;
    private int minY;

    public EnemyShip(Context context, int screenX, int screenY)
    {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        mSpeed = generator.nextInt(6) + 10;

        x = screenX;
        y = generator.nextInt(maxY) - mBitmap.getHeight();
    }

    public void update(int playerSpeed)
    {
        //move enemy left
        x -= playerSpeed;
        x -= mSpeed;

        //respawn when off screen
        if (x < minX - mBitmap.getWidth())
        {
            Random generator = new Random();
            mSpeed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - mBitmap.getHeight();
        }
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
}
