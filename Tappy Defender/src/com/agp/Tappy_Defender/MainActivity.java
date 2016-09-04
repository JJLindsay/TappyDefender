package com.agp.Tappy_Defender;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * The game begins here.
 * This class inflates the start screen.
 */
public class MainActivity extends Activity implements View.OnClickListener
{

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Access and set persistent data
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        //get a reference to a file called Hi_Scores
        preferences = getSharedPreferences("Hi_Scores", MODE_PRIVATE);

        //Set the text to the fastest recorded time or to 1,000,000
        final TextView textFastestTime = (TextView) findViewById(R.id.textHighScore);
        long fastestTime = preferences.getLong("fastest_time", 1000000);
        textFastestTime.setText("fastest_time:" + fastestTime);

        final Button playButton = (Button) findViewById(R.id.playButton);

        playButton.setOnClickListener(this);
    }

    //There is only 1 button so this is acceptable.
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //shutdown this activity
        finish();
    }

    /**
     * Should the user press the back button on their device
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return false;
    }
}