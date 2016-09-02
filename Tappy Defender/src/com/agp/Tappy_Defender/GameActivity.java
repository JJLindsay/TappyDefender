package com.agp.Tappy_Defender;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 *
 * This class exists to be a blank activity which we can use in conjunction
 * with a SurfaceView to dynamically create a view instead of using xml.
 */
public class GameActivity extends Activity
{
    private TDView mGameView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Get the device Display
        Display display = getWindowManager().getDefaultDisplay();
        //Point can hold two integers
        Point size = new Point();
        display.getSize(size);  //set the x and y to Point
        //passes the context of the app plus the device display size
        mGameView = new TDView(this, size.x, size.y);
        //Make mGameView the view for this activity
        setContentView(mGameView);
    }

    //When the activity is onPause, pause TDView
    @Override
    protected void onPause()
    {
        super.onPause();
        mGameView.pause();
    }

    //When the activity onResumes, resume TDView
    @Override
    protected void onResume()
    {
        super.onResume();
        mGameView.resume();
    }
}
