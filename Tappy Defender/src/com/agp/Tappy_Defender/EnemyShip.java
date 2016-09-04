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
    private int mBitmapXCoordinate, mBitmapYCoordinate;
    private int mSpeed = -1;

    //detect enemy leaving screen
    private int maxX;
    private int minX;

    //spawn enemy within screen
    private int maxY;
    private int minY;

    //A hit box for collision detection
    private Rect mHitBox;

    Random generator;
    int randomYCoordinate;

    public EnemyShip(Context context, int screenX, int screenY)
    {
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        generator= new Random();


        //load a random enemy
        int whichBitmap = generator.nextInt(3); //0,1,2
        switch (whichBitmap){
            case 0:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
                break;
            case 1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy2);
                break;
            case 2:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3);
                break;
        }

        scaleBitmapEnemy();

        //There is no particular reason for this range.
        mSpeed = generator.nextInt(6) + 10;  //10, 11, 12, 13, 14, 15

        mBitmapXCoordinate = screenX;  //place enemy at the right edge of the screen
        randomYCoordinate = generator.nextInt(maxY);
        mBitmapYCoordinate = ((randomYCoordinate - mBitmap.getHeight()) < minY) ? minY : (randomYCoordinate - mBitmap.getHeight());  //place enemy at a random Y position

        mHitBox = new Rect(mBitmapXCoordinate, mBitmapYCoordinate, mBitmap.getWidth(), mBitmap.getHeight());
    }

    public void update(int playerSpeed)
    {
        //move enemy left
        mBitmapXCoordinate -= playerSpeed;
        mBitmapXCoordinate -= mSpeed;

        //respawn when off screen
        if (mBitmapXCoordinate < minX - mBitmap.getWidth())
        {
            mSpeed = generator.nextInt(10) + 10;  //10 ... 19
            mBitmapXCoordinate = maxX;
            randomYCoordinate = generator.nextInt(maxY);
            mBitmapYCoordinate = ((randomYCoordinate - mBitmap.getHeight()) < minY) ? minY : (randomYCoordinate - mBitmap.getHeight());
        }

        //refresh placement of hit box location
        mHitBox.left = mBitmapXCoordinate;
        mHitBox.top = mBitmapYCoordinate;
        mHitBox.right = mBitmapXCoordinate + mBitmap.getWidth();
        mHitBox.bottom = mBitmapYCoordinate + mBitmap.getHeight();
    }


    public void scaleBitmapEnemy(){
        if (maxX < 1000){
            mBitmap= Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth()/3, mBitmap.getHeight()/3, false);
        }
        else if (maxX < 1200){
            mBitmap= Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth()/2, mBitmap.getHeight()/2, false);
        }
    }

    public Bitmap getBitmap()
    {
        return mBitmap;
    }

    public int getX()
    {
        return mBitmapXCoordinate;
    }

    public int getBitmapYCoordinate()
    {
        return mBitmapYCoordinate;
    }

    public Rect getHitBox()
    {
        return mHitBox;
    }

    public void setBitmapXCoordinate(int bitmapXCoordinate)
    {
        mBitmapXCoordinate = bitmapXCoordinate;
    }
}
