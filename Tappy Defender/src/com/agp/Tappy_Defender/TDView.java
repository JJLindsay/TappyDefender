package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * This is a dynamically generating view (as opposed to xml views). Its the game view after the home screen
 * SurfaceView provides a dedicated drawing surface embedded inside of a view
 *
 */
public class TDView extends SurfaceView implements Runnable
{
    //a volatile variable can be viewed (and altered) across threads. Very dangerous if not careful!
    volatile boolean playing;

    Thread mGameThread = null;

    private PlayerShip mPlayerShip;
    private EnemyShip[] mEnemyShips = new EnemyShip[3];
    private SpaceDust[] mSpaceDusts = new SpaceDust[40];

    //for drawing
    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    private static final boolean STOP_BOOSTING = false;
    private static final boolean START_BOOSTING = true;

    public TDView(Context context, int screenX, int screenY)
    {
        super(context);

        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPlayerShip = new PlayerShip(context, screenX, screenY);

        for (int index = 0; index < mEnemyShips.length; index++)
            mEnemyShips[index] = new EnemyShip(context, screenX, screenY);

        for (int index = 0; index < mSpaceDusts.length; index++)
            mSpaceDusts[index] = new SpaceDust(screenX, screenY);

    }

    @Override
    public void run()
    {
        while (playing)
        {
            update();  //updates all the game's data and objects
            draw();  //draw all game data one a frame once they've been updated
            control();  //controls the frequency of this cycle
        }
    }

    public void update()
    {
        mPlayerShip.update();
        for (int index = 0; index < mEnemyShips.length; index++)
            mEnemyShips[index].update(mPlayerShip.getSpeed());

        for (int index = 0; index < mSpaceDusts.length; index++)
            mSpaceDusts[index].update(mPlayerShip.getSpeed());
    }

    public void draw()
    {
        if (mSurfaceHolder.getSurface().isValid())
        {
            //lock the area of memory we will be drawing to
            mCanvas = mSurfaceHolder.lockCanvas();

            //clear the last frame. argb = alpha red green blue
            mCanvas.drawColor(Color.argb(255, 0, 0, 0));


            //white specs of dust
            mPaint.setColor(Color.argb(255,255,255,255));

            //draw space dust FIRST so the actors are overlaid
            for (int index = 0; index < mSpaceDusts.length; index++)
                mCanvas.drawPoint(mSpaceDusts[index].getX(), mSpaceDusts[index].getY(), mPaint);

            //draw the player
            mCanvas.drawBitmap(mPlayerShip.getBitmap(), mPlayerShip.getX(), mPlayerShip.getY(), mPaint);

            //draw the enemies
            for (int index = 0; index < mEnemyShips.length; index++)
                    mCanvas.drawBitmap(mEnemyShips[index].getBitmap(), mEnemyShips[index].getX(), mEnemyShips[index].getY(), mPaint);


            //unlock and draw scene
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * Control how soon the game updates and draws the sprite(s)
     */
    public void control()
    {
        try
        {
            /*  Android time is measured in milliseconds
                60 frames per sec = 1sec/60 frames = 1 frame every 0.0166 secs or 16.67 ms
                Stop updating and drawing for 17ms to maintain 60FPS.  */
            mGameThread.sleep(17);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //clean up our thread if the player quits or is interrupted by a phone call
    public void pause()
    {
        playing = false;
        try
        {
            mGameThread.join();
        } catch (InterruptedException e)
        {

        }
    }

    /**
     * make a new thread and start it
     */
    public void resume()
    {
        playing = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    /**
     * SurfaceView allows to handle onTouchEvents
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        //There are many different events in MotionEvent but only 2 are of interest here
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case (MotionEvent.ACTION_UP):
                //stop boosting player
                mPlayerShip.setBoosting(STOP_BOOSTING);
                break;
            case (MotionEvent.ACTION_DOWN):
                //boost player
                mPlayerShip.setBoosting(START_BOOSTING);
                break;
        }

        return true;
    }
}