package com.parallelfalchion.gamerwatch.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.parallelfalchion.gamerwatch.R;

/**
 * Created by Bryan on 9/24/2016.
 */

public class WishlistActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);


        populateWishlist();
    }

    private void populateWishlist() {
        //TODO
    }
}
