package com.parallelfalchion.gamerwatch.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parallelfalchion.gamerwatch.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO This should be R.layout.landing_page.xml
        setContentView(R.layout.single_game);
    }
}
