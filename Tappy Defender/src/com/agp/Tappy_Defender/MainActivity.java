package com.agp.Tappy_Defender;

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
 */
public class MainActivity extends android.app.Activity implements View.OnClickListener
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

    //The button is just there for visual purposes but the user can press anywhere to begin.
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        //shutdown this activity
        finish();
    }
}