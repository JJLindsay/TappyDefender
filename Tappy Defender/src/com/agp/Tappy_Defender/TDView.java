package com.agp.Tappy_Defender;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 */
public class TDView extends SurfaceView implements Runnable
{

    //a volatile variable can be viewed (and altered) across threads. Very dangerous if not careful!
    volatile boolean playing;
    Thread mGameThread = null;

    public TDView(Context context)
    {
        super(context);
    }

    public TDView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TDView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void run()
    {
        while (playing){
            update();  //updates all the game's data and objects
            draw();  //draw all game data one a frame once they've been updated
            control();  //controls the frequency of this cycle
        }
    }

    public void update(){

    }
    public void draw(){

    }

    public void control(){

    }

    //clean up our thread if the player quits or is interrupted by a phone call
    public void pause(){
        playing = false;
        try{
            mGameThread.join();
        }
        catch (InterruptedException e)
        {

        }
    }

    //make a new thread and start it
    public void resume() {
        playing = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }
}
