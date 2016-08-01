package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by J.Lindsay on 01.08.2016.
 */
public class PlayerShip
{
    private Bitmap mBitmap;
    private int x, y;
    private int mSpeed = 0;  //how fast the ship will travel

    public PlayerShip(Context context){
        x = 50;
        y = 50;
        mSpeed = 1;
        //loads the graphic
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
    }

    public void update(){
        x++;
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
}


