package com.parallelfalchion.gamerwatch.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parallelfalchion.gamerwatch.models.Game;
import com.parallelfalchion.gamerwatch.R;

/**
 * Created by Bryan on 9/24/2016.
 */

public class SingleGameActivity extends AppCompatActivity {

    //TODO need to pass in the selected Game somehow
    Game game;

    RadioGroup platformChoice = (RadioGroup) findViewById(R.id.platformGroup);
    RadioButton selectedPlatform;

    ListView vendors = (ListView) findViewById(R.id.vendorList);

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_game);
    }

    //TODO add a listener to the RadioButtons to handle which platform to show

}
