package com.agp.Tappy_Defender;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 */

/**
 * This class exists to be a blank page we can draw on.
 */
public class GameActivity extends Activity
{
    private TDView mGameView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //creates an instance fo TDView and passes in context of the app
        mGameView = new TDView(this);
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
    protected void onResume(){
        super.onResume();
        mGameView.resume();
    }
}
