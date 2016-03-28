package com.spiretos.spaceremote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spiretos.spaceremote.game.GameActivity;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button spaceRemoteButton=(Button) findViewById(R.id.main_spaceremote_button);
        spaceRemoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gameIntent=new Intent(MainActivity.this, GameActivity.class);
                gameIntent.putExtra("game",GameActivity.GAME_SPACE);
                startActivity(gameIntent);
            }
        });
    }
}
