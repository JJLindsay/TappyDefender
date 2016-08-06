package com.agp.Tappy_Defender;

import java.util.Random;


/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 */
public class SpaceDust
{
    private int x, y;
    private int mSpeed;

    //Detect dust leaving screen
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public SpaceDust(int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        //set a speed 0 - 9
        Random generator = new Random();
        mSpeed = generator.nextInt(10);

        //set the starting coordinates
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed){
        //speed up when the player does.
        x-=playerSpeed;
        x-=mSpeed;

        //respawn space dust
        if (x<0){
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            mSpeed = generator.nextInt(15);
        }
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