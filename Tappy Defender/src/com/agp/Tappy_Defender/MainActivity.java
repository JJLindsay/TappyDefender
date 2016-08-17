package com.agp.Tappy_Defender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * To comment out a line = ctrl + (keypad /)
 * To block comment = ctrl +  shift + (keypad /)
 * To refactor = shift f6
 * To remove unused imports = ctrl + alt + o
 * To format code = ctrl + alt + L
 * To run class = ctrl +  shift + F10
 *
 * The game begins here to display the home screen.
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
}