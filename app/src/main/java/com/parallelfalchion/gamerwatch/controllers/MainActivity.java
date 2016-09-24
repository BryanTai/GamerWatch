package com.parallelfalchion.gamerwatch.controllers;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.parallelfalchion.gamerwatch.R;

public class MainActivity extends AppCompatActivity {

    ListView featuredGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        populateFeaturedGames();
    }

    private void populateFeaturedGames() {
        featuredGames = (ListView) findViewById(R.id.featuredList);

    }
}
