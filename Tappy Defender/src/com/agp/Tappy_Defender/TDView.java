package com.agp.Tappy_Defender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
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
    private static final String TAG = "com.agp.Tappy_Defender.TDView";

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

    //HUD display variables
    private float mDistanceRemaining;
    private long mTimeTaken;
    private long mTimeStarted;
    private long mFastestTime;
    private int mScreenX;
    private int mScreenY;

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

        mScreenX = screenX;
        mScreenY = screenY;
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
        for (int index = 0; index < mEnemyShips.length; index++)
        {
            if (Rect.intersects(mPlayerShip.getHitBox(), mEnemyShips[index].getHitBox()))
                mEnemyShips[index].setBitmapXCorner(-200);  //remove it well off the screen for this frame. On the next frame, its update will reposition it.
        }

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
            mCanvas.drawColor(Color.argb(255, 0, 0, 0));  //alpha is white or Opaque/not transparent

            //white specs of dust
            mPaint.setColor(Color.argb(255,255,255,255));

            //draw space dust FIRST so the actors are overlaid
            for (int index = 0; index < mSpaceDusts.length; index++)
                mCanvas.drawPoint(mSpaceDusts[index].getX(), mSpaceDusts[index].getY(), mPaint);

            //ENTERING DEBUG CODE  ----------------

            mPaint.setColor(Color.argb(255, 255, 255, 255));  //set the paint color
            //whitens the hit box to white
            mCanvas.drawRect(mPlayerShip.getHitBox().left, mPlayerShip.getHitBox().top, mPlayerShip.getHitBox().right, mPlayerShip.getHitBox().bottom, mPaint);

            for (int index = 0; index < mEnemyShips.length; index++)
                mCanvas.drawRect(mEnemyShips[index].getHitBox().left, mEnemyShips[index].getHitBox().top, mEnemyShips[index].getHitBox().right, mEnemyShips[index].getHitBox().bottom, mPaint);

            //EXITING DEBUG CODE  ----------------

            //draw the player
            mCanvas.drawBitmap(mPlayerShip.getBitmap(), mPlayerShip.getPlayerPosX(), mPlayerShip.getBitmapYCorner(), mPaint);

            //draw the enemies
            for (int index = 0; index < mEnemyShips.length; index++)
                    mCanvas.drawBitmap(mEnemyShips[index].getBitmap(), mEnemyShips[index].getX(), mEnemyShips[index].getBitmapYCorner(), mPaint);

            //Drawing the HUD
            mPaint.setTextAlign(Paint.Align.LEFT);
            mPaint.setColor(Color.argb(255, 255, 255, 255));  //alpha set to solid, rgb set to white
            mPaint.setTextSize(25);
            //drawText(message, x-coord, y-coord, paint-style)
            mCanvas.drawText("Fastest:" + mFastestTime + "s", 10, 20, mPaint);  //mPaint defines alignment, color, and size
            mCanvas.drawText("Time:" + mTimeTaken + "s", mScreenX/2, 20, mPaint);
            mCanvas.drawText("Distance:" + mDistanceRemaining/1000 + " KM", mScreenX/3, mScreenY-20, mPaint);
            mCanvas.drawText("Shield:" + mPlayerShip.getShieldStrength(), 10, mScreenY-20, mPaint);
            mCanvas.drawText("Speed:" + mPlayerShip.getSpeed()*60 + " MPS", (mScreenX/3)*2, mScreenY-20, mPaint);

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
                60 frames per sec = 60 frames/1 sec = 1 frame every 0.0166 secs or 16.67 ms
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
            //e.printStackTrace();
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
     * SurfaceView allows us to handle onTouchEvents
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