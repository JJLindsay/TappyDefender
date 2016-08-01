package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
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
    private PlayerShip mPlayerShip;

    //for drawing
    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    public TDView(Context context)
    {
        super(context);

        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPlayerShip = new PlayerShip(context);
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
        mPlayerShip.update();
    }

    public void draw(){
        if (mSurfaceHolder.getSurface().isValid()){

            //lock the area of memory we will be drawing to
            mCanvas = mSurfaceHolder.lockCanvas();

            //clear the last frame
            mCanvas.drawColor(Color.argb(255,0,0,0));

            //draw the player
            mCanvas.drawBitmap(mPlayerShip.getBitmap(), mPlayerShip.getX(), mPlayerShip.getY(), mPaint);

            //unlock and draw scene
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    public void control(){
        try
        {
            //1,000ms / 60FPS
            mGameThread.sleep(17);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
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
