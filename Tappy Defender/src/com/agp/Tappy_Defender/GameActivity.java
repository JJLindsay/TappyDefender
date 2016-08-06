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

        //Get and display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        //load resolution into point object. Point holds two integer coordinates
        Point size = new Point();
        display.getSize(size);  //sets x and y of Point size
        //creates an instance fo TDView and passes in context of the app
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
